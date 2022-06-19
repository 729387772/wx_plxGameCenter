package priv.gao.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;
import priv.gao.common.helper.RetMsgUtils;
import priv.gao.user.bean.User;
import priv.gao.user.bean.UserRelation;
import priv.gao.user.dao.InteractDao;

import java.util.Date;
import java.util.List;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/25 10:42
 */
@Service
public class InteractServiceImpl implements InteractService {

    @Autowired
    InteractDao interactDao;

    @Override
    public void applyToBeFriends(UserRelation userRelation) {
        userRelation.setStatus(UserRelation.STATUS_SQ);
        try {
            interactDao.insertUserRelation(userRelation);
        }catch (DuplicateKeyException e){
            throw new MyException("申请已提交，请勿重复发送！", Constant.RETMSG_WARNING);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agreeToBeFriends(UserRelation userRelation) {
        //1.添加对方为好友
        userRelation.setCreateTime(new Date());
        userRelation.setStatus(UserRelation.STATUS_ZC);
        interactDao.insertUserRelation(userRelation);

        //2.同意加为好友
        String userCode = userRelation.getUserCode();
        userRelation.setUserCode(userRelation.getFriendCode());
        userRelation.setFriendCode(userCode);
        interactDao.updateUserRelation(userRelation);

        //3.刷新对方好友列表

    }

    @Override
    public void unfriend(UserRelation userRelation) {
        //1.双向删除
        interactDao.deleteUserRelation(userRelation);
        //2.刷新对方好友列表

    }

    @Override
    public void dragToBlacklist(UserRelation userRelation) {
        userRelation.setBlacklistTime(new Date());
        userRelation.setStatus(UserRelation.STATUS_LH);
        interactDao.updateUserRelation(userRelation);
    }

    @Override
    public String sendMessagesToAll() {
        return null;
    }

    @Override
    public String sendMessagesToFriend() {
        return null;
    }

    @Override
    public String sendMessagesToFriends() {
        return null;
    }

    @Override
    public String sendMessagesToStranger() {
        return null;
    }

    @Override
    public void receiveMsgByUserCode(String userCode){

    }

    @Override
    public List<UserRelation> queryFriendList(String userCode) {
        List<UserRelation> userRelationList = interactDao.queryUserRelationByUserCode(userCode);
        return userRelationList;
    }

    @Override
    public List<User> queryApplyList(String userCode) {
        List<User> applyList = interactDao.queryApplyListByUserCode(userCode);
        return applyList;
    }
}
