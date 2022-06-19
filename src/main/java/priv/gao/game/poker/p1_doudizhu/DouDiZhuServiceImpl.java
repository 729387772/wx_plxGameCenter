package priv.gao.game.poker.p1_doudizhu;

import org.springframework.stereotype.Service;
import priv.gao.game.poker.Poker;
import priv.gao.game.poker.PokerPlayer;
import priv.gao.game.poker.PokerService;

import java.util.*;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 14:04
 */
@Service
public class DouDiZhuServiceImpl extends PokerService {

    @Override
    public LinkedList<Poker> createPoker() {
        return null;
    }

    @Override
    public void initRules(String rules) {

    }

    @Override
    public void initPoker(LinkedList<Poker> pokers) {

    }

    @Override
    public void buildPlayersRel(LinkedHashMap<String, PokerPlayer> playerMap) {

    }

    @Override
    public void deal(LinkedHashMap<String, PokerPlayer> playerMap, LinkedList<Poker> pokers) {

    }

    @Override
    public void sort(ArrayList<PokerPlayer> pokerPlayers) {

    }

    @Override
    public void play() {

    }
}
