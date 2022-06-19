package priv.gao.user.bean;

import java.util.Date;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 玩家关系
 * @date 2021/8/11 11:31
 */
public class UserRelation {

    public static final String STATUS_SQ = "00";//申请
    public static final String STATUS_ZC = "01";//正常
    public static final String STATUS_LH = "02";//拉黑

    /*
        主键id
     */
    private String id;

    /*
        玩家编号
     */
    private String userCode;

    /*
        朋友编号
     */
    private String friendCode;

    /*
        创建时间
     */
    private Date createTime;

    /*
        拉黑时间
     */
    private Date blacklistTime;

    /*
        状态[00申请01正常02拉黑]
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBlacklistTime() {
        return blacklistTime;
    }

    public void setBlacklistTime(Date blacklistTime) {
        this.blacklistTime = blacklistTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
