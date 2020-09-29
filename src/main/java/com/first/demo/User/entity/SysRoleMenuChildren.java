package com.first.demo.User.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单实体类
 */
@Data
public class SysRoleMenuChildren implements Serializable {

    /**
     *菜单名称
    */
    private String id;
    /**
     * 菜单Id
     */
    private String title;


}