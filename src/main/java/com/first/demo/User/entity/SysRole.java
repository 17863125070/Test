package com.first.demo.User.entity;

import lombok.Data;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:29
 * @param:
 * @return:
 */
@Data
public class SysRole {
    //角色ID
    private Integer roleId;
    //角色名称
    private String roleName;
    //备注
    private String remark;
    //创建用户ID CREATE_USER_ID
    private String createUserId;
    //创建时间
    private String createTime;
}
