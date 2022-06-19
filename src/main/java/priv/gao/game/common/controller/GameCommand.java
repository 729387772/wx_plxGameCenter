package priv.gao.game.common.controller;

import priv.gao.game.common.service.GameCommandService;
import priv.gao.room.bean.Room;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/18 14:57
 */
public class GameCommand {

    private GameCommandService gameCommandService;

    public GameCommand(GameCommandService gameCommandService) {
        this.gameCommandService = gameCommandService;
    }

    public void initGame(Room room){ gameCommandService.initGame(room); }

    public void startNextGame(Room room){
        gameCommandService.startNextGame(room);
    }
}
