package com.first.demo.User.service;

import com.first.demo.User.dto.SysUserRoleDto;
import com.first.demo.User.entity.User;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.Query;

import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wangshichao
 * @Date: 2020/5/28 13:55
 * @Version 1.0
 */

public interface userService {
    List<User> listSysuser();
    /**
     * 功能描述:
     * 〈根据userName查询用户信息〉
     *
     * @param username 1
     * @return : com.msunhealth.usermanagement.entity.SysUser
     * @author : songhuanhao
     * @date : 2019/11/25 15:18
     */
    User selectUserByUserName(String username);
    /**
     * 功能描述:
     * 〈用户管理模块用户角色展示〉
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.dto.SysUserRoleDto>
     * @author : songhuanhao
     * @date : 2020/3/3 10:46
     */
    List<SysUserRoleDto> listUsers(Query query);

    /**
     * 功能描述:
     * 〈根据userId查询用户〉
     *
     * @param userId 1
     * @return : com.msunhealth.usermanagement.dto.SysUserRoleDto
     * @author : songhuanhao
     * @date : 2020/3/5 14:03
     */
    SysUserRoleDto findUserByUserId(String userId);

    /**
     * 功能描述:
     * 〈添加新用户、角色service〉
     *
     * @param sysUserRoleDto 1
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/5 14:03
     */
    AjaxRusult saveUserRole(SysUserRoleDto sysUserRoleDto);

    /**
     * 功能描述:
     * 〈修改用户、角色service〉
     *
     * @param sysUserRoleDto 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/6 15:14
     */
    AjaxRusult updateUserRole(SysUserRoleDto sysUserRoleDto);

    /**
     * 功能描述:
     * 〈批量删除用户和用户角色〉
     *
     * @param ids 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 9:24
     */
    AjaxRusult delUserRole(List<String> ids);

    /**
     * 功能描述:
     * 〈批量重置用户密码〉
     *
     * @param sysUser 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 9:25
     */
    AjaxRusult resetPassword(List<User> sysUser);

    /**
     * 功能描述:
     * 〈修改当前登录用户的密码〉
     *
     * @param  password,newPassword
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 13:21
     */
    AjaxRusult updatePassword(String password,String newPassword);

}
