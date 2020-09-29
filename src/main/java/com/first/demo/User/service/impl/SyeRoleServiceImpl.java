package com.first.demo.User.service.impl;

import com.first.demo.User.entity.SysRole;
import com.first.demo.User.entity.User;
import com.first.demo.User.mapper.SysRoleMapper;
import com.first.demo.User.service.SysRoleService;
import com.first.demo.util.FormatDateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/27 9:45
 * @Version 1.0
 */
@Service
public class SyeRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 功能描述:查询所有角色
     *
     * @param : []
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysRole>
     * @author : kanghongjian
     * @date : 2020/3/30  16:12
     */
    @Override
    public List<SysRole> selectAllRole() {

        List<SysRole> sysRoles = sysRoleMapper.selectAllRole();

        return sysRoles;
    }

    /**
     * 功能描述:根据roleId查询UserId
     *
     * @param : [roleId]
     * @return : java.util.List
     * @author : kanghongjian
     * @date : 2020/4/1  16:28
     */
    @Override
    public List<String> selectUserIdByroleId(String roleId) {

        List<String> selectUserIdByroleId = sysRoleMapper.selectUserIdByroleId(roleId);

        if (selectUserIdByroleId.isEmpty() || selectUserIdByroleId.size() == 0) {
            selectUserIdByroleId.add("-1");
        }

        return selectUserIdByroleId;
    }

    /**
     * 功能描述:新增权限
     *
     * @param : [sysRole]
     * @return : java.lang.Integer
     * @author : kanghongjian
     * @date : 2020/4/1  15:00
     */
    @Override
    public Integer saveRole(SysRole sysRole) {

        sysRole.setCreateTime(FormatDateUtil.formatDate_ymd(new Date()));
        //通过shiro拿到用户信息,shiro可以管理session信息和用户的信息
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        sysRole.setCreateUserId(principal.getUserId().toString());
        Integer integer = sysRoleMapper.saveRole(sysRole);

        return integer;
    }

    /**
     * 功能描述:修改权限
     *
     * @param : [sysRole]
     * @return : java.lang.Integer
     * @author : kanghongjian
     * @date : 2020/4/1  15:49
     */
    @Override
    public Integer updateRole(SysRole sysRole) {

        sysRole.setCreateTime(FormatDateUtil.formatDate_ymd(new Date()));
        //通过shiro拿到用户信息,shiro可以管理session信息和用户的信息
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        sysRole.setCreateUserId(principal.getUserId().toString());
        Integer integer = sysRoleMapper.updateRole(sysRole);

        return integer;
    }

    /**
     * 功能描述:删除权限
     *
     * @param : [roleId]
     * @return : java.lang.Integer
     * @author : kanghongjian
     * @date : 2020/4/1  15:16
     */
    @Override
    public Integer deleteRole(String roleId) {

        return sysRoleMapper.deleteRole(roleId);
    }

    /**
     * 功能描述:
     * 〈根据用户名查询用户角色，用于shiro认证〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 9:28
     */
    @Override
    public List<String> findRolesByUserId(Integer userId) {
        return sysRoleMapper.findRolesByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈查询全部角色〉
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysRole>
     * @author : songhuanhao
     * @date : 2020/3/4 14:37
     */
    @Override
    public List<SysRole> listRoles() {
        return sysRoleMapper.listRoles();
    }
}
