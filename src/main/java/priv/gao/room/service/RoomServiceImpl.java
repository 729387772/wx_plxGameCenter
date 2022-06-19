package priv.gao.room.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import priv.gao.common.bean.MyException;
import priv.gao.common.handler.websocket.WebSocketHandler;
import priv.gao.common.helper.Constant;
import priv.gao.common.helper.ArrayUtils;
import priv.gao.game.common.AllGameInfo;
import priv.gao.game.common.bean.GameInfo;
import priv.gao.game.common.bean.Player;
import priv.gao.game.common.dao.GameInfoDao;
import priv.gao.room.bean.Room;
import priv.gao.room.bean.RoomRequest;
import priv.gao.room.dao.RoomDao;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/13 17:43
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private GameInfoDao gameInfoDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private WebSocketHandler webSocketHandler;
    /**
     * 创建房间
     * @return
     */
    @Override
    public Room create(RoomRequest roomReq){
        String userCode = roomReq.getUserCode();
        String roomPassword = roomReq.getRoomPassword();
        int gameType = roomReq.getGameType();
        String rules = roomReq.getRules();

        //1.创建房间
        Room room = this.createRoomInfo(userCode,roomPassword,gameType,rules);
        //2.存储房间信息mysql
        gameInfoDao.saveGameInfo(room.getGameInfo());
        room.setGameInfoId(room.getGameInfo().getId());
        roomDao.saveRoomInfo(room);
        //3.存储房间信息redis
        String roomKey = Room.getRoomKey(room.getRoomCode());
        redisTemplate.opsForValue().set(roomKey,JSONObject.toJSONString(room));//,30L,TimeUnit.MINUTES
        log.debug("玩家"+room.getRoomMasterCode()+"创建了"+room.getRoomCode()+"号房间");
        return room;
    }

    private Room createRoomInfo(String userCode, String roomPassword, int gameType, String rules) {
        AllGameInfo gameInfo = AllGameInfo.matchTheGame(gameType);
        Room room = new Room();
        room.setRoomName(gameInfo.getGameName());
        room.setPassword(roomPassword);
        room.setRoomMasterCode(userCode);
        room.setMaxUserNumber(gameInfo.getMaxPlayerNumber());
        room.setFullUserStart(gameInfo.isFullUserStart());
        room.setStatus(Room.STATUS_WKS);
        room.setGameInfo(new GameInfo(gameType,gameInfo.getGameName(),rules));
        room.getGameInfo().getPlayerMap().put(userCode, new Player(userCode));
        return room;
    }

    /**
     * 加入房间
     * @return
     */
    @Override
    public Room join(RoomRequest roomReq) {
        String userCode = roomReq.getUserCode();
        int roomCode = roomReq.getRoomCode();
        String roomPassword = roomReq.getRoomPassword();

        //1.将当前玩家写入房间玩家信息redis
        Room room = this.getRoomInfo(roomCode);
        LinkedHashMap<String,Player> playerMap = room.getGameInfo().getPlayerMap();
        boolean isExist = playerMap.keySet().contains(userCode);
        if(!isExist){
            if(!StringUtils.isEmpty(room.getPassword()) && !room.getPassword().equals(roomPassword)){
                throw new MyException("房间密码不正确！", Constant.RETMSG_WARNING);
            }
            //判断当前房间人数
            if(playerMap.size() >= room.getMaxUserNumber()){
                throw new MyException("房间人数已满！", Constant.RETMSG_WARNING);
            }
            log.debug("玩家"+userCode+"加入"+roomCode+"房间");
            playerMap.put(userCode, new Player(userCode));
            this.updateRoomInfo4R(Room.getRoomKey(roomCode),room);
        }
        //2.通知其他玩家  todo
//        log.debug("通知：玩家"+userCode+"加入"+roomCode+"房间");
        return room;
    }

    /**
     * 离开房间
     * @return
     */
    @Override
    public void exit(RoomRequest roomReq) {
        String userCode = roomReq.getUserCode();
        int roomCode = roomReq.getRoomCode();

        Room room = this.getRoomInfo(roomCode);
        //1.将当前玩家移出房间玩家信息redis
        String roomKey = Room.getRoomKey(roomCode);
        LinkedHashMap<String,Player> playerMap = room.getGameInfo().getPlayerMap();
        if(playerMap.keySet().contains(userCode) && !room.getStatus().equals(Room.STATUS_YXZ)){
            log.debug("玩家"+userCode+"离开"+roomCode+"房间");
            playerMap.remove(userCode);
            this.updateRoomInfo4R(roomKey,room);
        }

        //2.判断当前房间人数，无人时解散房间
        if(playerMap.size() <= 0){
            log.debug(roomCode+"房间解散");
            redisTemplate.delete(roomKey);
            roomDao.deleteRoomByRoomCode(roomCode);
        }else if(userCode.equals(room.getRoomMasterCode()) && !room.getStatus().equals(Room.STATUS_YXZ)){//房主退出
            String newMasterCode = playerMap.keySet().iterator().next();
            room.setRoomMasterCode(newMasterCode);
            this.updateRoomInfo(room);
            this.updateRoomInfo4R(roomKey,room);
            log.debug(roomCode+"房间更换房主为"+newMasterCode);
        }

        //3.通知其他玩家  todo
//        log.debug("通知：玩家"+userCode+"离开"+roomCode+"房间");
    }

    /**
     * 匹配
     * @param roomReq
     * @return
     */
    @Override
    public void match(RoomRequest roomReq) {
        String[] userCodes = roomReq.getUserCodes();
        ArrayUtils.compareArr(userCodes);
        int gameType = roomReq.getGameType();

        // 1 将当前房间玩家添加到匹配队列
        String matchKey = Room.getMatchKey(gameType);
        String jsonUserCodes = JSONObject.toJSONString(userCodes);
        redisTemplate.opsForZSet().add(matchKey,jsonUserCodes,userCodes.length);

        // 2 判断当前队列是否可以匹配成功
        Thread matchThread = new Thread(new MatchThread(redisTemplate,webSocketHandler,gameType,matchKey));
        matchThread.start();
    }
    /**
     * 取消匹配
     * @return
     */
    @Override
    public void unmatch(RoomRequest roomReq) {
        String[] userCodes = roomReq.getUserCodes();
        ArrayUtils.compareArr(userCodes);
        String jsonUserCodes = JSONObject.toJSONString(userCodes);
        int gameType = roomReq.getGameType();
        String matchKey = Room.getMatchKey(gameType);
        redisTemplate.opsForZSet().remove(matchKey,jsonUserCodes);

        //打印一下
        System.out.print("取消匹配后");
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(matchKey, 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        while(iterator.hasNext()){
            ZSetOperations.TypedTuple<String> next = iterator.next();
            System.out.print(next.getValue() + " ");
        }
        System.out.println();
    }

    /**
     * 获取房间信息
     * @param roomCode
     * @return
     */
    @Override
    public Room getRoomInfo(int roomCode){
        String roomKey = Room.getRoomKey(roomCode);
        String jsonRoom = redisTemplate.opsForValue().get(roomKey);

        Room room;
        if(jsonRoom == null){
            room = roomDao.queryRoomInfo(roomCode);
        }else{
            room = JSONObject.parseObject(jsonRoom, Room.class);
        }
        if(room == null){
            throw new MyException("房间信息不存在",Constant.RETMSG_WARNING);
        }
        return room;
    }

    /**
     * 向mysql中保存房间信息
     * @param room
     * @return
     */
    @Override
    public void updateRoomInfo(Room room){
        roomDao.updateRoomInfo(room);
    }

    /**
     * 向redis中保存房间信息
     * @param roomKey
     * @param room
     * @return
     */
    @Override
    public void updateRoomInfo4R(String roomKey, Room room){
        redisTemplate.boundValueOps(roomKey).set(JSONObject.toJSONString(room),30L,TimeUnit.MINUTES);
    }

    /**
     * 玩家准备
     * @return
     */
    @Override
    public void prepare(RoomRequest roomReq) {
        String userCode = roomReq.getUserCode();
        int roomCode = roomReq.getRoomCode();
        boolean prepareFlag = roomReq.getFlag();
        //通知其他玩家  todo

    }

}
