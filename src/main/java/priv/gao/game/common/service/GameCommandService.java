package priv.gao.game.common.service;

import priv.gao.room.bean.Room;


/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/23 11:15
 */
public interface GameCommandService {

    void initGame(Room room);

    void startNextGame(Room room);

}
