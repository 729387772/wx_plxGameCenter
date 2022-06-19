package priv.gao.game.common.bean;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 18:26
 */
public class Player {
    public String playerCode;

    public Player() {
    }

    public Player(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }
}
