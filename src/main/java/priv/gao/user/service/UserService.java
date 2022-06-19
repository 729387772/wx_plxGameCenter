package priv.gao.user.service;

import priv.gao.user.bean.User;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/16 17:35
 */
public interface UserService {

    //用户注册
    void regist(User user);

    //用户登录
    User login(User user);

    //游客登录
    User login4Visitor(User user);
}
