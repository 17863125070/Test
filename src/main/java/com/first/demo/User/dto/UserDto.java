package com.first.demo.User.dto;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:23
 * @param:
 * @return:
 */
public class UserDto {

    /**
     * 对应登录页面得到账号
     */
    private String username;

    /**
     * 对应登录页面密码
     */
    private String password;

    /**
     * 对应登录页面验证码
     */
    private String captcha;
    /**
     * 对应登录页面记住我
     */
    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
