package com.first.demo.User.controller;

import com.first.demo.User.dto.SysUserRoleDto;
import com.first.demo.User.entity.SysRole;
import com.first.demo.User.entity.User;
import com.first.demo.User.service.SysRoleService;
import com.first.demo.User.service.userService;
import com.first.demo.common.anno.SysLog;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.BootstrapTableResult;
import com.first.demo.util.Query;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:用户信息相关接口
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/27 11:14
 * @Version 1.0
 */
@Controller
@RequestMapping("/sysuser")
public class UserController {

    @Resource
    private userService userService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 功能描述:
     * 〈查询用户信息〉
     * @param
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2019/11/27 13:21
     */
    //shiro权限只有sys:user:info才能访问
    //@RequiresPermissions("sys:user:info")
    @RequestMapping("/user/info")
    @ResponseBody
    public AjaxRusult userInfo() {
        //通过shiro拿到用户信息,shiro可以管理session信息和用户的信息
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        return com.first.demo.util.AjaxRusult.ok().put("user", principal);
    }

    /**
     * 功能描述:
     * 〈用户管理模块展示用户〉
     * @param query 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/3 14:01
     */
    @RequestMapping("/userlist")
    @ResponseBody
    public BootstrapTableResult userList(Query query) {
        PageHelper.offsetPage(query.getOffset(), query.getLimit());//参数一：（当前页）页码，参数二：每页显示条数
        List<SysUserRoleDto> sysUserRoleDtos = userService.listUsers(query);
        PageInfo<SysUserRoleDto> info = new PageInfo<SysUserRoleDto>(sysUserRoleDtos);
        List<SysUserRoleDto> rows = info.getList();
        long total = info.getTotal();
        BootstrapTableResult bootstrapTableResult = new BootstrapTableResult(total, rows);
        return bootstrapTableResult;
    }

    @RequestMapping("/finduserbyid/{userId}")
    @ResponseBody
    public AjaxRusult findUserByUserID(@PathVariable("userId") String userId) {
        SysUserRoleDto userByUserId = userService.findUserByUserId(userId);
        return AjaxRusult.ok().put("user", userByUserId);
    }

    /**
     * 功能描述:
     * 〈查询所有角色〉
     * @param
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/5 9:40
     */
    @RequestMapping("/listroles")
    @ResponseBody
    public AjaxRusult listRoles() {
        List<SysRole> sysRoles = sysRoleService.listRoles();
        return AjaxRusult.ok().put("listRoles", sysRoles);
    }


    @RequestMapping("/saveuser")
    @ResponseBody
    @RequiresPermissions("sys:user:save")
    @SysLog(value = "新增用户")
    public AjaxRusult saveUser(@RequestBody SysUserRoleDto user) {
        String roleId = user.getRoleId();
        String userName = user.getUserName();
        String passWord = user.getPassWord();
        String cnName = user.getCnName();
        Boolean istrue = (roleId != null) && (userName != "" && userName != null) && (passWord != "" && passWord != null) && (cnName != "" && cnName != null);
        if (istrue) {

            return userService.saveUserRole(user);
        }
        return AjaxRusult.error("有空值请检查");
    }

    @RequestMapping("/updateuser")
    @ResponseBody
    //@RequiresRoles("管理员")
    @SysLog(value = "更新用户")
    public AjaxRusult updateUser(@RequestBody SysUserRoleDto user) {
        String roleId = user.getRoleId();
        String userName = user.getUserName();
        String cnName = user.getCnName();
        Boolean istrue = (roleId != "" && roleId != null) && (userName != "" && userName != null) && (cnName != "" && cnName != null);
        if (istrue) {
            return userService.updateUserRole(user);
        }
        return AjaxRusult.error("有空值请检查");
    }

    /**
     * 功能描述:
     * 〈删除用户和用户角色〉
     * @param ids 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/12 13:44
     */
    @RequestMapping("/deluserrole")
    @ResponseBody
    @SysLog("删除用户和用户角色")
    public AjaxRusult delUserRole(@RequestBody List<String> ids) {
        return userService.delUserRole(ids);
    }

    /**
     * 功能描述:
     * 〈重置密码〉
     * @param sysUser 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/12 14:15
     */
    @RequestMapping("/resetpassword")
    @ResponseBody
    @SysLog("重置密码")
    public AjaxRusult resetPassword(@RequestBody List<User> sysUser) {
        return userService.resetPassword(sysUser);
    }

    /**
     * 功能描述:
     * 〈修改当前登录用户的密码〉
     * @param password 1
     * @param newPassword 2
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 13:28
     */
    @RequestMapping("/updatepassword")
    @ResponseBody
    public AjaxRusult updatePassword(@RequestBody @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
        return userService.updatePassword(password, newPassword);
    }

}
