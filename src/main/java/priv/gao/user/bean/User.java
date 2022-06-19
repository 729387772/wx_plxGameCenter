package priv.gao.user.bean;

import java.util.Date;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 用户
 * @date 2021/8/11 11:28
 */
public class User {
    public static final String STATUS_ZC = "00";
    public static final String STATUS_SD = "01";
    public static final String STATUS_TY = "02";

    /*
        主键id 自增
     */
    private int id;

    /*
        用户编号
     */
    private String userCode;

    /*
        用户昵称
     */
    private String userName;

    /*
        密码
     */
    private String password;

    /*
        qq号
     */
    private String qq;

    /*
        微信号
     */
    private String wechat;

    /*
        身份证
     */
    private String idCard;

    /*
        手机号
     */
    private Integer telephone;

    /*
        创建时间
     */
    private Date createTime;

    /*
        状态[00正常01锁定02停用]
     */
    private String status;

    /*
        是否游客
     */
    private boolean isVisitor;

    /*
        用户经验
     */
    private Long exp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getTelephone() { return telephone; }

    public void setTelephone(Integer telephone) { this.telephone = telephone; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(boolean visitor) {
        isVisitor = visitor;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }
}
