package priv.gao.user.dao;

import org.springframework.stereotype.Repository;
import priv.gao.user.bean.User;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/11 14:10
 */
@Repository
public interface UserDao {

    int regist(User user);

    User login(User user);
}
