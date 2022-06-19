package priv.gao.user.service;

import priv.gao.user.bean.User;
import priv.gao.user.bean.UserRelation;

import java.util.List;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/25 10:42
 */

public interface InteractService {

    //申请好友
    void applyToBeFriends(UserRelation userRelation);

    //同意添加好友
    void agreeToBeFriends(UserRelation userRelation);

    //删除好友
    void unfriend(UserRelation userRelation);

    //拉黑好友
    void dragToBlacklist(UserRelation userRelation);

    //发消息给所有人
    String sendMessagesToAll();

    //发消息给好友
    String sendMessagesToFriend();

    //群发好友
    String sendMessagesToFriends();

    //发消息给陌生人
    String sendMessagesToStranger();

    //接收消息
    void receiveMsgByUserCode(String userCode);

    List<UserRelation> queryFriendList(String userCode);

    List<User> queryApplyList(String userCode);
}
