package priv.gao.game.poker.p2_baohuang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import priv.gao.game.common.bean.GameInfo;
import priv.gao.game.poker.*;
import priv.gao.room.bean.Room;

import java.util.*;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 14:04
 */
@Service
public class BaoHuangServiceImpl extends PokerService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static int size = 4;//4副扑克
    public static int userNum = 5;//玩家数量5
    public static boolean threeFlag = true;//憋三
    public static boolean fourFlag = false;//防止记牌，将4随机去掉

    /**
     * 1 构造扑克
     * @return
     */
    @Override
    public LinkedList<Poker> createPoker() {
        return Poker.createPoker(size);
    }

    /**
     * 2 初始化游戏规则
     */
    @Override
    public void initRules(String rules) {
        log.debug(rules);
    }

    /**
     * 3 初始化扑克，闷3和皇帝牌
     * @param allPokers
     */
    @Override
    public void initPoker(LinkedList<Poker> allPokers) {
        int threeNum = 4 * size;
        int DWNum = 0;
        for (int i = 0; i < allPokers.size(); i++) {
            if(threeFlag && threeNum > userNum && "3".equals(allPokers.get(i).getNum())){
                allPokers.remove(allPokers.get(i));
                threeNum--;
            }
            if(Poker.NUM_OTH[1].equals(allPokers.get(i).getNum()) && DWNum++ == 0){
                allPokers.get(i).setType(Poker.HD);
            }
        }
    }

    /**
     * 构建玩家上下家关系
     * @param playerMap
     */
    @Override
    public void buildPlayersRel(LinkedHashMap<String,PokerPlayer> playerMap) {
        PokerPlayer[] pokerPlayers = new PokerPlayer[userNum];
        Iterator<Map.Entry<String, PokerPlayer>> iterator = playerMap.entrySet().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            pokerPlayers[i] = iterator.next().getValue();
            if(i > 0){
                pokerPlayers[i].setPrePlayer(pokerPlayers[i-1]);
                pokerPlayers[i-1].setNextPlayer(pokerPlayers[i]);
            }
        }
        pokerPlayers[0].setPrePlayer(pokerPlayers[userNum-1]);
        pokerPlayers[userNum - 1].setNextPlayer(pokerPlayers[0]);
    }

    /**
     * 4 发牌
     * @param playerMap
     * @param allPokers
     */
    @Override
    public void deal(LinkedHashMap<String,PokerPlayer> playerMap, LinkedList<Poker> allPokers) {
        PokerPlayer pokerPlayer = playerMap.entrySet().iterator().next().getValue();
        for (int i = 0; i < allPokers.size(); i++) {
            if(fourFlag){
                if(!"4".equals(allPokers.get(i).getNum())){
                    pokerPlayer.getPokers().add(allPokers.get(i));
                }
            }else{
                pokerPlayer.getPokers().add(allPokers.get(i));
            }
            pokerPlayer = pokerPlayer.getNextPlayer();
        }
    }

    /**
     * 将每个玩家的牌从大到小排序
     */
    @Override
    public void sort(ArrayList<PokerPlayer> pokerPlayers) {
        for (int i = 0; i < userNum; i++) {
            PokerPlayer pokerPlayer = pokerPlayers.get(i);
            System.out.print(pokerPlayer.getPlayerCode()+":");
            this.sort(pokerPlayer.getPokers());
        }
    }

    /**
     * 将玩家的牌从大到小排序
     */
    public void sort(LinkedList<Poker> pokers) {
        //1.先按照标准顺序排序
        Collections.sort(pokers);

        //2.处理特殊牌
        LinkedList<Poker> zpokers = new LinkedList<>();
        for (int i = 0; i < pokers.size(); i++) {
            Poker next = pokers.get(i);
            //记录炸弹
            if(i < pokers.size() - 3 && next.equals(pokers.get(i+3))){
                zpokers.addAll(pokers.subList(i,i+4));
                pokers.removeAll(zpokers);
            }
            //保子牌第一位，皇帝牌第二位
            if(next.getType() == Poker.HD || next.getType() == Poker.BZ){
                pokers.remove(next);
                pokers.addFirst(next);
            }
        }
        pokers.addAll(0,zpokers);

        for (int i = 0; i < pokers.size(); i++) {
            Poker next = pokers.get(i);
            System.out.print(next + ",");
        }
        System.out.println("共计：" + pokers.size());
    }

    /**
     * 5.2 让位
     * @param pokerReq
     * @param gameInfo
     */
    public void abdicate(PokerRequest pokerReq, GameInfo gameInfo) {
        String userCode = pokerReq.getUserCode();//操作玩家编号
        LinkedHashMap<String, PokerPlayer> playerMap = gameInfo.getPlayerMap();//当前房间玩家信息
        PokerPlayer operator = playerMap.get(userCode);//获取操作玩家信息
        LinkedList<Poker> pokers = operator.getPokers();
        Poker hdPoker = pokers.getFirst();
        pokers.remove(hdPoker);//将皇帝牌移除

        LinkedList publicPokerPool = gameInfo.getPublicPokerPool();
        if(gameInfo.getPublicPokerPool().size() != 0){//除第一个玩家，后续都添2
            int t = pokers.size() - 1;
            while(t-- >= 0){
                if(pokers.get(t).getRank() >= 13 && pokers.get(t).getType() != 1){
                    publicPokerPool.add(pokers.get(t));//将2添加到公共牌池，没2往上添
                    pokers.remove(pokers.get(t));
                    break;
                }
            }
        }
        publicPokerPool.add(hdPoker);//将皇帝牌添加到公共牌池
        //发消息给其他玩家 todo
        PokerPlayer prePlayer = operator.getPrePlayer();
    }

    /**
     * 登基
     * @param pokerReq
     * @param gameInfo
     */
    public void beEnthroned(PokerRequest pokerReq, GameInfo gameInfo) {
        String userCode = pokerReq.getUserCode();//操作玩家编号
        LinkedHashMap<String, PokerPlayer> playerMap = gameInfo.getPlayerMap();//当前房间玩家信息
        PokerPlayer operator = playerMap.get(userCode);//获取操作玩家信息

        boolean protectItself = true;//皇帝是否是独保
        //1.处理皇帝的牌
        operator.setRole(PokerPlayerRole.HUANGDI);
        LinkedList<Poker> publicPokerPool = gameInfo.getPublicPokerPool();//公共牌池
        operator.getPokers().addAll(publicPokerPool);
        publicPokerPool.clear();//清空公共牌池

        //2.处理保子的牌
        Poker[] playPokers = pokerReq.getPlayPokers();//叫保的牌
        PokerPlayer nextPlayer = operator.getNextPlayer();
        while(PokerPlayerRole.HUANGDI != nextPlayer.getRole()){
            LinkedList<Poker> pokers = nextPlayer.getPokers();
            if(pokers.contains(playPokers[0])){
                protectItself = false;
                nextPlayer.setRole(PokerPlayerRole.SHIWEI);
                pokers.get(pokers.indexOf(playPokers[0])).setType(2);
                System.out.print(nextPlayer.getPlayerCode()+":");
                this.sort(nextPlayer.getPokers());
            }else{
                nextPlayer.setRole(PokerPlayerRole.FANZEI);
            }
            nextPlayer = nextPlayer.getNextPlayer();
        }

        //3.独保的情况
        if(protectItself){
            LinkedList<Poker> pokers = operator.getPokers();
            pokers.get(pokers.indexOf(playPokers[0])).setType(2);
        }

        //4.排序
        System.out.print(operator.getPlayerCode()+":");
        this.sort(operator.getPokers());
    }


    @Override
    public void play() {

    }
}
