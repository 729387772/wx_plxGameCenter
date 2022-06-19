package priv.gao.game.poker;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/20 15:59
 */
public class PokerRequest {
    /*
       操作玩家编号
    */
    private String userCode;

    /*
        房间编号
     */
    private int roomCode;

    /*
        出的牌
     */
    private Poker[] playPokers;

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

    public Poker[] getPlayPokers() {
        return playPokers;
    }

    public void setPlayPokers(Poker[] playPokers) {
        this.playPokers = playPokers;
    }

}
