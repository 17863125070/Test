package com.first.demo.User.service;

import com.first.demo.User.entity.SysRole;

import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:23
 * @param:
 * @return:
 */
public interface SysRoleService {

    /**
     * 功能描述:查询所有权限
     *
     * @author : kanghongjian
     * @date : 2020/3/30  16:10
     */

    List<SysRole> selectAllRole();

    /**
     * 功能描述:根据roleId查询UserId
     *
     * @author : kanghongjian
     * @date   : 2020/4/1  16:26
     */
    List<String>  selectUserIdByroleId(String roleId);
    /**
     * 功能描述:添加权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  saveRole(SysRole sysRole);
    /**
     * 功能描述:修改权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  updateRole(SysRole sysRole);
    /**
     * 功能描述:删除权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  deleteRole(String roleId);
    /**
     * 功能描述:
     * 〈根据用户ID查询用户角色，用于shiro授权的方法〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 8:45
     */
    List<String> findRolesByUserId(Integer userId);

    List<SysRole> listRoles();
}
