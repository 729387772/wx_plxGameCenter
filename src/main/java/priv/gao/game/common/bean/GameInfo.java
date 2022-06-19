package priv.gao.game.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import priv.gao.game.poker.Poker;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 游戏信息
 * @date 2021/8/26 15:13
 */
public class GameInfo<T> {
    /*
        主键
    */
    private int id;

    /*
       游戏类型
    */
    private int gameType;

    /*
       游戏名称
    */
    private String gameName;

    /*
       游戏规则
    */
    private String rules;

//    /*
//       操作记录 todo
//    */
//    private LinkedList<String> operateLog;

    /*
       房间内玩家
    */
    private LinkedHashMap<String, T> playerMap = new LinkedHashMap<>();

    @JSONField(serialize = false)
    private LinkedList<Poker> publicPokerPool;


    public GameInfo() {
    }

    public GameInfo(int gameType, String gameName) {
        this.gameType = gameType;
        this.gameName = gameName;
    }

    public GameInfo(int gameType, String gameName, String rules) {
        this.gameType = gameType;
        this.gameName = gameName;
        this.rules = rules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

//    public LinkedList<String> getOperateLog() {
//        return operateLog;
//    }
//
//    public void setOperateLog(LinkedList<String> operateLog) {
//        this.operateLog = operateLog;
//    }

    public LinkedHashMap<String, T> getPlayerMap() {
        return playerMap;
    }

    public LinkedList<Poker> getPublicPokerPool() {
        if(publicPokerPool == null){
            publicPokerPool = new LinkedList<>();
        }
        return publicPokerPool;
    }
}
