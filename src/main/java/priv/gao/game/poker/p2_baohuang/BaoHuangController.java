package priv.gao.game.poker.p2_baohuang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.game.poker.PokerPlayer;
import priv.gao.game.poker.PokerRequest;
import priv.gao.game.poker.PokerService;
import priv.gao.room.bean.Room;
import priv.gao.room.service.RoomService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/20 14:43
 */
@Controller
@RequestMapping(value = "/bh2")
public class BaoHuangController {

    @Autowired
    RoomService roomServiceImpl;

    @Autowired
    BaoHuangServiceImpl baoHuangService;

    /**
     * 登基
     * @param pokerReq:roomCode 房间编号
     * @param pokerReq:userCode 玩家编号
     * @param pokerReq:playPokers 出牌
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/beEnthroned")
    public String beEnthroned(@RequestBody PokerRequest pokerReq){
        Room room = this.getRoomInfo(pokerReq.getRoomCode());
        baoHuangService.beEnthroned(pokerReq, room.getGameInfo());
        return RetMsgUtils.success();
    }

    /**
     * 让位
     * @param pokerReq:roomCode 房间编号
     * @param pokerReq:userCode 玩家编号
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/abdicate")
    public String abdicate(PokerRequest pokerReq){
        Room room = this.getRoomInfo(pokerReq.getRoomCode());
        baoHuangService.abdicate(pokerReq, room.getGameInfo());
        return RetMsgUtils.success();
    }

    /**
     * 随机登基（都叫不起来）
     * @param pokerReq:roomCode 房间编号
     * @param pokerReq:userCode 玩家编号
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/beEnthronedExt")
    public String beEnthronedExt(PokerRequest pokerReq){
        Room room = this.getRoomInfo(pokerReq.getRoomCode());
        baoHuangService.abdicate(pokerReq, room.getGameInfo());
        return RetMsgUtils.success();
    }

    @ResponseBody
    @PostMapping(value = "/play")
    public String play(PokerRequest pokerReq){
        Room room = this.getRoomInfo(pokerReq.getRoomCode());
//        baoHuangService.play();
//        roomServiceImpl.updateRoomInfo4R(roomKey,room);
        return RetMsgUtils.success(room.getGameInfo().getPlayerMap());
    }

    private Room getRoomInfo(int roomCode){
        Room room = roomServiceImpl.getRoomInfo(roomCode);
        baoHuangService.exPlayerClass(room.getGameInfo().getPlayerMap());
        baoHuangService.buildPlayersRel(room.getGameInfo().getPlayerMap());
        return room;
    }
}
