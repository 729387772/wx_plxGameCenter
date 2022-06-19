package priv.gao.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.game.common.controller.GameCommand;
import priv.gao.game.common.controller.GameServiceFactory;
import priv.gao.room.bean.Room;
import priv.gao.room.bean.RoomRequest;
import priv.gao.room.service.RoomService;


/**
 * @author gaoqiang
 * @version 1.0
 * @description 房间
 * @date 2021/8/13 17:43
 */
@Controller
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomService roomServiceImpl;

    @Autowired
    GameServiceFactory gameServiceFactory;

    /**
     * 创建房间
     * @param roomReq:userCode 玩家编号
     * @param roomReq:roomPassword 房间密码
     * @param roomReq:gameType 游戏类型
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/create")
    public String create(RoomRequest roomReq){
        Room room = roomServiceImpl.create(roomReq);
        return RetMsgUtils.success(room);
    }

    /**
     * 加入房间
     * @param roomReq:userCode 玩家编号
     * @param roomReq:roomCode 房间编号
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/join")
    public String join(RoomRequest roomReq){
        Room room = roomServiceImpl.join(roomReq);
        return RetMsgUtils.success(room);
    }

    /**
     * 离开房间
     * @param roomReq:userCode 玩家编号
     * @param roomReq:roomCode 房间编号
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/exit")
    public String exit(RoomRequest roomReq){
        roomServiceImpl.exit(roomReq);
        return RetMsgUtils.success();
    }

    /**
     * 匹配
     * @param roomReq:userCodes 玩家编号集合
     * @param roomReq:gameType 游戏类型
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/match")
    public String match(@RequestBody RoomRequest roomReq){
        roomServiceImpl.match(roomReq);
        return RetMsgUtils.success();
    }

    /**
     * 取消匹配
     * @param roomReq:userCode 玩家编号
     * @param roomReq:gameType 游戏类型
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/unmatch")
    public String unmatch(@RequestBody RoomRequest roomReq){
        roomServiceImpl.unmatch(roomReq);
        return RetMsgUtils.success();
    }

    /**
     * 游戏准备
     * @param roomReq:userCode 玩家编号
     * @param roomReq:roomCode 房间编号
     * @param roomReq:flag true准备/false取消准备
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/prepare")
    public String prepare(RoomRequest roomReq){
        roomServiceImpl.prepare(roomReq);
        return RetMsgUtils.success();
    }


    /**
     * 游戏开始
     * @param roomReq:roomCode 房间编号
     * @param roomReq:rules 游戏规则
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/start")
    public String start(RoomRequest roomReq){
        int roomCode = roomReq.getRoomCode();
        String rules = roomReq.getRules();
        Room room = roomServiceImpl.getRoomInfo(roomCode);

        if(room.isFullUserStart() && room.getMaxUserNumber() != room.getGameInfo().getPlayerMap().size()){
            //房间人数不足 开始匹配
            roomServiceImpl.match(roomReq);
            return RetMsgUtils.success();
        }else{
            //1.初始化游戏
            GameCommand gameCommand = new GameCommand(gameServiceFactory.getGameService(room.getGameInfo().getGameType()));
            gameCommand.initGame(room);
            //2.保存游戏信息
            String roomKey = Room.getRoomKey(roomCode);
            room.getGameInfo().setRules(rules);
            room.setStatus(Room.STATUS_YXZ);
            roomServiceImpl.updateRoomInfo(room);
            roomServiceImpl.updateRoomInfo4R(roomKey,room);
            return RetMsgUtils.success(room.getGameInfo());
        }
    }

    /**
     * 开始下一局
     * @param roomReq:roomCode 房间编号
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/startNext")
    public String startNext(RoomRequest roomReq){
        int roomCode = roomReq.getRoomCode();

        //1.开始下一局
        Room room = roomServiceImpl.getRoomInfo(roomCode);
        GameCommand gameCommand = new GameCommand(gameServiceFactory.getGameService(room.getGameInfo().getGameType()));
        gameCommand.startNextGame(room);
        //2.保存游戏信息
        String roomKey = Room.getRoomKey(roomCode);
        roomServiceImpl.updateRoomInfo4R(roomKey,room);
        return RetMsgUtils.success(room.getGameInfo());
    }
}
