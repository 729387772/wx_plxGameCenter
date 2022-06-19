package priv.gao.room.bean;

import com.alibaba.fastjson.annotation.JSONField;
import priv.gao.game.common.bean.GameInfo;
import priv.gao.game.common.bean.Player;

import java.util.LinkedHashMap;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 房间
 * @date 2021/8/11 11:50
 */
public class Room<T> {
    private static final String ROOM_KEY = "ROOM";
    private static final String MATCH_KEY = "MATCH";
    public static final String STATUS_WKS = "00"; //未开始
    public static final String STATUS_YXZ = "01"; //游戏中

    /*
        主键id 自增
     */
    @JSONField(serialize = false)
    private int id;

    /*
        房间编号 序列
     */
    private int roomCode;

    /*
        房间名称
     */
    private String roomName;

    /*
        密码
     */
    private String password;

    /*
        房主
     */
    private String roomMasterCode;

    /*
        房间总人数
     */
    private int maxUserNumber;

    /*
       是否人满开始
    */
    private boolean fullUserStart;

    /*
        状态
     */
    private String status;

    /*
        游戏信息id
     */
    private int gameInfoId;

    /*
        游戏信息
     */
    private GameInfo gameInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoomMasterCode() { return roomMasterCode; }

    public void setRoomMasterCode(String roomMasterCode) {
        this.roomMasterCode = roomMasterCode;
    }

    public int getMaxUserNumber() {
        return maxUserNumber;
    }

    public void setMaxUserNumber(int maxUserNumber) {
        this.maxUserNumber = maxUserNumber;
    }

    public boolean isFullUserStart() {
        return fullUserStart;
    }

    public void setFullUserStart(boolean fullUserStart) {
        this.fullUserStart = fullUserStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public int getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(int gameInfoId) {
        this.gameInfoId = gameInfoId;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public static String getRoomKey(int roomCode){
        return ROOM_KEY + "_" + roomCode + ": String";
    }

    public static String getMatchKey(int gameType){
        return MATCH_KEY + "_" + gameType + ": ZSet";
    }
}
