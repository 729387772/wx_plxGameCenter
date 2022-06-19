package priv.gao.user.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import priv.gao.common.bean.MyException;
import priv.gao.common.helper.Constant;
import priv.gao.common.helper.SysIdCreator;
import priv.gao.user.bean.User;
import priv.gao.user.dao.UserDao;

import java.util.Date;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/8/11 14:05
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDao userDao;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public void regist(User user) {
        //1.补充基本信息
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword()) + Constant.SOLT));
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_ZC);
        //2.保存用户信息到数据库
        try {
            userDao.regist(user);
        }catch (DuplicateKeyException e){
            throw new MyException("用户名已存在！",Constant.RETMSG_WARNING);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword()) + Constant.SOLT));
        User retUser = userDao.login(user);
        retUser.setExp(3456L);

        if(retUser == null){
            throw new MyException("用户名或密码不正确！",Constant.RETMSG_WARNING);
        }else{
            retUser.setPassword(null);
            return retUser;
        }
    }

    /**
     * 游客登录
     * @return
     */
    @Override
    public User login4Visitor(User user) {
        if(!StringUtils.isEmpty(user.getUserCode())){
            user.setPassword(user.getUserCode());
            return this.login(user);
        }
        User visitorUser = new User();
        visitorUser.setUserCode(SysIdCreator.getUUID(16));
        visitorUser.setUserName("游客"+visitorUser.getUserCode().substring(0,8));
        visitorUser.setPassword(visitorUser.getUserCode());
        visitorUser.setIsVisitor(true);
        this.regist(visitorUser);
        visitorUser.setPassword(null);
        return visitorUser;
    }
}
