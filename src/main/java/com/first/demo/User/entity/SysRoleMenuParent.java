package com.first.demo.User.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单实体类
 */
@Data
public class SysRoleMenuParent implements Serializable {

    /**
     *菜单名称
    */
    private String title;
    /**
     * 菜单Id
     */
    private  Integer id;
    /**
     * 树形菜单是否展开
     */
    private Boolean spread;

    private List<SysRoleMenuChildren> children;



}