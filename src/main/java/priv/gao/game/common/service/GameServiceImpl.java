package priv.gao.game.common.service;

import org.springframework.stereotype.Service;
import priv.gao.game.common.AllGameInfo;
import priv.gao.game.common.bean.GameInfo;

import java.util.ArrayList;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/26 14:37
 */
@Service
public class GameServiceImpl implements GameService {

    @Override
    public ArrayList<GameInfo> getAllGameList() {
        return AllGameInfo.getAllGameList();
    }

}
