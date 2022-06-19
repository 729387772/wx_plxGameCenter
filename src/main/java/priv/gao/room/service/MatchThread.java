package priv.gao.room.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import priv.gao.common.bean.MyException;
import priv.gao.common.handler.websocket.WebSocketHandler;
import priv.gao.common.helper.Constant;
import priv.gao.game.common.AllGameInfo;

import java.util.*;
import java.util.stream.Collectors;

public class MatchThread implements Runnable {

    private RedisTemplate<String,String> redisTemplate;

    private WebSocketHandler webSocketHandler;

    private int gameType;

    private String matchKey;


    MatchThread(RedisTemplate<String,String> redisTemplate,WebSocketHandler webSocketHandler,int gameType,String matchKey){
        this.redisTemplate = redisTemplate;
        this.webSocketHandler = webSocketHandler;
        this.gameType = gameType;
        this.matchKey = matchKey;
    }
    @Override
    public void run() {
        synchronized (MatchThread.class){
            System.out.print("匹配前");
            // 将redis中的匹配队列转换成ArrayList:allMatchPlayerList
            ArrayList<ZSetOperations.TypedTuple<String>> allMatchPlayerList = new ArrayList();
            Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(matchKey, 0, -1);
            Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
            while(iterator.hasNext()){
                ZSetOperations.TypedTuple<String> next = iterator.next();
                allMatchPlayerList.add(next);
                System.out.print(next.getValue() + " ");
            }
            System.out.println();
            // 将队列中的等待人数进行组合
            AllGameInfo gameInfo = AllGameInfo.matchTheGame(gameType);
            int maxPlayerNumber = gameInfo.getMaxPlayerNumber();
            LinkedList<ZSetOperations.TypedTuple<String>> makeUpTempList = new LinkedList<>();
            makeUp(matchKey,allMatchPlayerList,maxPlayerNumber,makeUpTempList,0);
        }
    }

    /**
     * 玩家匹配组合
     * @param matchKey
     * @param allMatchPlayerList
     * @param maxPlayerNumber
     * @param makeUpTempList
     * @param curIdx
     */
    private void makeUp(String matchKey, ArrayList<ZSetOperations.TypedTuple<String>> allMatchPlayerList,int maxPlayerNumber,LinkedList<ZSetOperations.TypedTuple<String>> makeUpTempList,int curIdx){
        for (int i = curIdx; i < allMatchPlayerList.size(); i++) {
            Double matchPlayerNumber = makeUpTempList.stream().collect(Collectors.summingDouble(x -> ((Double) x.getScore())));
            if(!makeUpTempList.contains(allMatchPlayerList.get(i))){
                makeUpTempList.add(allMatchPlayerList.get(i));
                matchPlayerNumber += allMatchPlayerList.get(i).getScore();
                if(matchPlayerNumber == maxPlayerNumber){
                    ArrayList<String> matchedUserList = new ArrayList();
                    //从makeUpTempList拿出所有匹配成功的用户数据
                    Iterator<ZSetOperations.TypedTuple<String>> iterator = makeUpTempList.iterator();
                    while(iterator.hasNext()){
                        ZSetOperations.TypedTuple<String> next = iterator.next();
                        Collections.addAll(matchedUserList,((JSONArray) JSONObject.parse(next.getValue())).toArray(new String[0]));
                    }
                    try {
                        //校验用户是否取消了匹配
                        checkUserCancelledMatch(makeUpTempList);
                        //给前端返回匹配成功
                        webSocketHandler.sendMessageToSomeOne("匹配成功",matchedUserList);
                    }catch (MyException e){
                        //如果匹配成功的用户下线了，将该用户踢出
                        String[] exceptionUserCodes = e.getExceptionNo();
                        for (String exceptionUserCode: exceptionUserCodes) {
                            iterator = makeUpTempList.iterator();
                            while(iterator.hasNext()) {
                                ZSetOperations.TypedTuple<String> next = iterator.next();
                                List<String> matchingUserCodeList = ((JSONArray) JSONObject.parse(next.getValue())).toJavaList(String.class);
                                if(matchingUserCodeList.contains(exceptionUserCode)){
                                    allMatchPlayerList.remove(next);
                                    redisTemplate.opsForZSet().remove(matchKey,next.getValue());
                                }
                            }
                        }
                        makeUpTempList.clear();
                        //重新匹配一次
                        makeUp(matchKey,allMatchPlayerList,maxPlayerNumber,makeUpTempList,0);
                        return;
                    }
                    //从总的匹配列表中删除匹配成功的数据
                    iterator = makeUpTempList.iterator();
                    while(iterator.hasNext()){
                        ZSetOperations.TypedTuple<String> next = iterator.next();
                        System.out.println(next.getValue());
                        allMatchPlayerList.remove(next);
                        redisTemplate.opsForZSet().remove(matchKey,next.getValue());
                    }
                    //清空临时匹配数组
                    makeUpTempList.clear();
                    return;
                }else if(matchPlayerNumber > maxPlayerNumber){
                    makeUpTempList.removeLast();
                    makeUpTempList.removeLast();
                    return;
                }
                makeUp(matchKey,allMatchPlayerList,maxPlayerNumber,makeUpTempList,curIdx+1);
                if(makeUpTempList.size() != 0 && makeUpTempList.getFirst().equals(allMatchPlayerList.get(i))){
                    makeUpTempList.clear();
                }
            }
        }
    }

    /**
     * 检查用户是否取消匹配
     * @param makeUpTempList
     */
    private void checkUserCancelledMatch(LinkedList<ZSetOperations.TypedTuple<String>> makeUpTempList) {
        ArrayList<String> cancelledList = new ArrayList();
        Iterator<ZSetOperations.TypedTuple<String>> iterator = makeUpTempList.iterator();
        while(iterator.hasNext()){
            ZSetOperations.TypedTuple<String> next = iterator.next();
            Double score = redisTemplate.opsForZSet().score(matchKey, next.getValue());
            if(null == score){//已经不在匹配队列了
                Collections.addAll(cancelledList,((JSONArray) JSONObject.parse(next.getValue())).toArray(new String[0]));
            }
        }
        if(cancelledList.size() != 0){
            throw new MyException("用户已取消匹配", Constant.RETMSG_FAIL, cancelledList);
        }
    }

}
