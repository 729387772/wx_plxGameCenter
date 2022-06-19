package priv.gao.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.user.bean.User;
import priv.gao.user.service.InteractService;
import priv.gao.user.service.UserService;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 登录
 * @date 2021/8/11 11:35
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    InteractService interactServiceImpl;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/regist")
    public String regist(User user){
        userServiceImpl.regist(user);
        return RetMsgUtils.success();
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public String login(User user){
        User retUser = userServiceImpl.login(user);
        interactServiceImpl.receiveMsgByUserCode(user.getUserCode());
        return RetMsgUtils.success(retUser);
    }

    /**
     * 游客登录
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login4Visitor")
    public String login4Visitor(@RequestBody User user){
        User retUser = userServiceImpl.login4Visitor(user);
        return RetMsgUtils.success(retUser);
    }
}
