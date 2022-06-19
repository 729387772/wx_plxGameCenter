package priv.gao.game.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.game.common.bean.GameInfo;
import priv.gao.game.common.service.GameService;

import java.util.ArrayList;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/26 14:28
 */
@Controller
@RequestMapping(value = "/game")
public class GameController {
    @Autowired
    GameService gameServiceImpl;

    /**
     * 获取游戏列表
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public String getAllGameList(){
        ArrayList<GameInfo> gameList = gameServiceImpl.getAllGameList();
        return RetMsgUtils.success(gameList);
    }
}
