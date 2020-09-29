package com.first.demo.util;

import com.first.demo.User.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/6/5 8:32
 * @param:
 * @return:
 */
public class GetUserInfoUtils {

    public static String getuserId() {
        //通过shiro拿到用户信息,shiro可以管理session信息和用户的信息
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        //获取登陆用户的userId
        String userId = principal.getUserId().toString();
        return userId;
    }
}
