package priv.gao.game.common;

import priv.gao.game.common.bean.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/17 10:27
 */
public enum AllGameInfo {

    DOUDIZHU(1,"欢乐斗地主",3,true,"priv.gao.game.poker.p1_doudizhu.DouDiZhuServiceImpl","douDiZhuServiceImpl"),

    BAOHUANG(2,"保皇",5,true,"priv.gao.game.poker.p2_baohuang.BaoHuangServiceImpl","baoHuangServiceImpl"),

    GOUJI(3,"够级",6,true,"priv.gao.game.poker.p3_gouji.GouJiServiceImpl","gouJiServiceImpl"),

    FORGIORY(4,"为了荣耀",2,true,"priv.gao.game.puz.z1_forGiory.ForGioryServiceImpl","ForGioryServiceImpl");
    /*
       游戏类型
    */
    private int gameType;

    /*
       游戏名称
    */
    private String gameName;

    /*
       最大玩家数
    */
    private int maxPlayerNumber;

    /*
       是否人满开始
    */
    private boolean fullUserStart;

    private String reference;

    private String referenceName;

    AllGameInfo(int gameType, String gameName, int maxPlayerNumber,boolean fullUserStart, String reference,String referenceName) {
        this.gameType = gameType;
        this.gameName = gameName;
        this.maxPlayerNumber = maxPlayerNumber;
        this.fullUserStart = fullUserStart;
        this.reference = reference;
        this.referenceName = referenceName;
    }

    public int getGameType() {
        return gameType;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public boolean isFullUserStart() {
        return fullUserStart;
    }

    public void setFullUserStart(boolean fullUserStart) {
        this.fullUserStart = fullUserStart;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public static AllGameInfo matchTheGame(int gameType){
        return Arrays.stream(AllGameInfo.values())
                .filter(gameInfoEnum -> gameInfoEnum.getGameType() == gameType)
                .findFirst().orElse(null);
    }

    public static ArrayList<GameInfo> getAllGameList(){
        ArrayList<GameInfo> allGameInfos = new ArrayList();
        Arrays.stream(AllGameInfo.values()).forEach(gameInfo -> {
            allGameInfos.add(new GameInfo(gameInfo.getGameType(),gameInfo.getGameName()));
        });
        return allGameInfos;
    }
}
