package com.first.demo.User.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单实体类
 */
@Data
public class SysMenu implements Serializable {
    /**
     * 菜单Id
     */
    private Integer menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权
     */
    private String perms;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;


    //添加一个额外的字段
    private String parentName;
    //防止被物理删除的逻辑字段，f为未删除，t为删除

    private String isDeleted;

}