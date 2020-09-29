package com.first.demo.User.entity;

import lombok.Data;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:31
 * @param:
 * @return:
 */
@Data
public class SysUserRole {
    //用户ID
    private String userId;
    //对应的用户角色ID
    private String roleId;

}
