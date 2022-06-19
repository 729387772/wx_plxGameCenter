package priv.gao.user.dao;

import org.springframework.stereotype.Repository;
import priv.gao.user.bean.User;
import priv.gao.user.bean.UserRelation;

import java.util.List;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/25 10:43
 */
@Repository
public interface InteractDao {

    void insertUserRelation(UserRelation userRelation);

    void updateUserRelation(UserRelation userRelation);

    void deleteUserRelation(UserRelation userRelation);

    List<UserRelation> queryUserRelationByUserCode(String userCode);

    List<User> queryApplyListByUserCode(String userCode);
}
