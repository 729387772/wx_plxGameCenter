package priv.gao.game.common.dao;

import org.springframework.stereotype.Repository;
import priv.gao.game.common.bean.GameInfo;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/9/3 16:51
 */
@Repository
public interface GameInfoDao {
    void saveGameInfo(GameInfo gameInfo);
}
