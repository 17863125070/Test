package com.first.demo.User.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wangshichao
 * @Date: 2020/5/28 10:20
 * @Version 1.0
 */
@Data
public class User implements Serializable {
    /*ID*/
    public Integer userId;
    /*用户名*/
    public String userName;
    /*中文名*/
    public String cnName;
    /*密码*/
    public String password;
    /*邮箱*/
    public String email;
    /*电话*/
    public String mobile;
    /*状态 0：禁用 1：正常*/
    public String status;
    /*创建者ID*/
    public String createUserId;
    /*创建时间*/
    public String createTime;
    /*更新时间*/
    public String updateTime;
}
