package priv.gao.game.poker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;
import priv.gao.game.common.service.GameCommandService;
import priv.gao.room.bean.Room;

import java.util.*;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 14:04
 */
public abstract class PokerService implements GameCommandService {
    /**
     * 构造扑克
     * @return
     */
    public abstract LinkedList<Poker> createPoker();

    /**
     * 设置游戏规则xxx-xxxxx-xxx
     * @param rules
     */
    public abstract void initRules(String rules);

    /**
     * 初始化扑克
     * @param allPokers
     */
    public abstract void initPoker(LinkedList<Poker> allPokers);

    /**
     * 洗牌
     * @param allPokers
     */
    public void shuffle(LinkedList<Poker> allPokers) {
        Collections.shuffle(allPokers);
    }

    /**
     * 构建玩家上下家关系
     * @param playerMap
     */
    public abstract void buildPlayersRel(LinkedHashMap<String,PokerPlayer> playerMap);

    /**
     * 发牌
     * @param playerMap
     * @param allPokers
     */
    public abstract void deal(LinkedHashMap<String,PokerPlayer> playerMap, LinkedList<Poker> allPokers);

    /**
     * 排序
     * @param pokerPlayers
     */
    public abstract void sort(ArrayList<PokerPlayer> pokerPlayers);

    /**
     * 将playerMap中players的类型转成PokerPlayer JSONObject ==> JavaObject
     * @param playerMap
     */
    public final void exPlayerClass(LinkedHashMap playerMap){
        Iterator<Map.Entry<String, JSONObject>> iterator = playerMap.entrySet().iterator();
        while (iterator.hasNext()){
            JSONObject nextVal = iterator.next().getValue();
            String jsonString = JSON.toJSONString(nextVal);
            PokerPlayer pokerPlayer = JSON.parseObject(jsonString,PokerPlayer.class);
            playerMap.put(pokerPlayer.getPlayerCode(),pokerPlayer);
        }
    }

    /**
     * 清空玩家手牌
     * @param playerMap
     */
    private void clearPlayerPokers(LinkedHashMap playerMap) {
        Iterator<Map.Entry<String, PokerPlayer>> iterator = playerMap.entrySet().iterator();
        while (iterator.hasNext()){
            PokerPlayer pokerPlayer = iterator.next().getValue();
            pokerPlayer.setPokers(null);
        }
    }

    /**
     * 出牌
     *
     */
    public abstract void play();

    /**
     * 初始化扑克游戏的模板
     * @param room
     */
    @Override
    public final void initGame(Room room){
        LinkedHashMap playerMap = room.getGameInfo().getPlayerMap();
        String rules = room.getGameInfo().getRules();

        this.exPlayerClass(playerMap);
        this.clearPlayerPokers(playerMap);
        LinkedList<Poker> allPokers = this.createPoker();
        this.initRules(rules);
        this.initPoker(allPokers);
        this.shuffle(allPokers);
        this.buildPlayersRel(playerMap);
        this.deal(playerMap,allPokers);
        this.sort(new ArrayList<>(playerMap.values()));
    }


    /**
     * 开始下一局游戏的模板
     * @param room
     */
    @Override
    public final void startNextGame(Room room){


    };

}
