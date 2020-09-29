package com.first.demo.User.dto;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:20
 * @param:
 * @return:
 */
public  class  SysUserRoleDto {
    //用户ID
    private Integer userId;
    //用户账号
    private String userName;
    //用户姓名
    private String cnName;
    //联系电话
    private String mobile;
    //创建日期
    private String createTime;
    //状态
    private String status;
    //角色名称
    private String roleName;
    //角色ID
    private String roleId;
    //密码
    private String passWord;
    //创建人ID
    private String createUserId;
    //更新时间
    private String updateTime;



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String creatTime) {
        this.createTime = creatTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
