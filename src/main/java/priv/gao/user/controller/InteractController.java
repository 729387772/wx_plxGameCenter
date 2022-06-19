package priv.gao.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.user.bean.User;
import priv.gao.user.bean.UserRelation;
import priv.gao.user.service.InteractService;

import java.util.List;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 互动（添加好友、聊天、发送邀请）
 * @date 2021/8/11 11:35
 */
@Controller
@RequestMapping(value = "/interact")
public class InteractController {

    @Autowired
    InteractService interactServiceImpl;

    /**
     * 申请好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/apply")
    public String applyToBeFriends(UserRelation userRelation){
        interactServiceImpl.applyToBeFriends(userRelation);
        return RetMsgUtils.success();
    }

    /**
     * 同意添加好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/agree")
    public String agreeToBeFriends(UserRelation userRelation){
        interactServiceImpl.agreeToBeFriends(userRelation);
        return RetMsgUtils.success();
    }

    /**
     * 删除好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/unfriend")
    public String unfriend(UserRelation userRelation){
        interactServiceImpl.unfriend(userRelation);
        return RetMsgUtils.success();
    }

    /**
     * 拉黑好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/blacklist")
    public String dragToBlacklist(UserRelation userRelation){
        interactServiceImpl.dragToBlacklist(userRelation);
        return RetMsgUtils.success();
    }

    /**
     * 发消息给所有人
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/MsgToAll")
    public String sendMessagesToAll(){
        return interactServiceImpl.sendMessagesToAll();
    }

    /**
     * 发消息给好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/MsgToFri")
    public String sendMessagesToFriend(){
        return interactServiceImpl.sendMessagesToFriend();
    }

    /**
     * 群发好友
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/MsgToFris")
    public String sendMessagesToFriends(){
        return interactServiceImpl.sendMessagesToFriends();
    }

    /**
     * 发消息给陌生人
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/MsgToStranger")
    public String sendMessagesToStranger(){
        return interactServiceImpl.sendMessagesToStranger();
    }

    /**
     * 刷新好友列表
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/friendList")
    public String queryFriendList(String userCode){
        List<UserRelation> userRelationList = interactServiceImpl.queryFriendList(userCode);
        return RetMsgUtils.success(userRelationList);
    }

    /**
     * 查询申请列表
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/applyList")
    public String queryApplyList(String userCode){
        List<User> userRelationList = interactServiceImpl.queryApplyList(userCode);
        return RetMsgUtils.success(userRelationList);
    }
}
