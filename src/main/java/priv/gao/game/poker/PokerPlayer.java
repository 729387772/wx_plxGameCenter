package priv.gao.game.poker;

import com.alibaba.fastjson.annotation.JSONField;
import priv.gao.game.common.bean.Player;

import java.util.LinkedList;

public class PokerPlayer extends Player {

    @JSONField(serialize = false)
    private PokerPlayer prePlayer;

    @JSONField(serialize = false)
    private PokerPlayer nextPlayer;

    private LinkedList<Poker> pokers;

    private PokerPlayerRole role;

    public PokerPlayer() {
    }

    public PokerPlayer(String playerCode) {
        super(playerCode);
    }

    public PokerPlayer getPrePlayer() {
        return prePlayer;
    }

    public void setPrePlayer(PokerPlayer prePlayer) {
        this.prePlayer = prePlayer;
    }

    public PokerPlayer getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(PokerPlayer nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public LinkedList<Poker> getPokers() {
        if(pokers == null){
            pokers = new LinkedList<>();
        }
        return pokers;
    }

    public void setPokers(LinkedList<Poker> pokers) {
        this.pokers = pokers;
    }

    public PokerPlayerRole getRole() {
        return role;
    }

    public void setRole(PokerPlayerRole role) {
        this.role = role;
    }
}
