package priv.gao.room.bean;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 房间接口的请求参数
 * @date 2021/8/18 11:28
 */
public class RoomRequest {
    /*
        操作玩家编号
     */
    private String userCode;

    /*
        房间编号
     */
    private int roomCode;

    /*
        房间密码
     */
    private String roomPassword;

    /*
        房间游戏类型
     */
    private int gameType;

    /*
        玩家准备标志
     */
    private boolean flag;

    /*
        规则编号
     */
    private String rules;

    /*
        房间内玩家编号
     */
    private String[] userCodes;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String[] getUserCodes() {
        return userCodes;
    }

    public void setUserCodes(String[] userCodes) {
        this.userCodes = userCodes;
    }
}
