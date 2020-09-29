package com.first.demo.User.service.impl;

import com.first.demo.User.dto.SysUserRoleDto;
import com.first.demo.User.entity.SysUserRole;
import com.first.demo.User.entity.User;
import com.first.demo.User.mapper.SysRoleMapper;
import com.first.demo.User.mapper.UserMapper;
import com.first.demo.User.service.userService;
import com.first.demo.common.constant.SysConstant;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.MD5Utils;
import com.first.demo.util.Query;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wangshichao
 * @Date: 2020/5/28 13:55
 * @Version 1.0
 */
@Service
public class userServiceImpl implements userService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 功能描述:
     * 〈查询用户表〉
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysUser>
     * @author : songhuanhao
     * @date : 2019/11/14 11:05
     */
    @Override
    public List<User> listSysuser() {
        return userMapper.listSelectUser();
    }
    /**
     * 功能描述:
     * 〈根据用户名查询用户信息〉
     *
     * @param username 1
     * @return : com.msunhealth.usermanagement.entity.SysUser
     * @author : songhuanhao
     * @date : 2019/11/25 15:20
     */
    @Override
    public User selectUserByUserName(String username) {
        return userMapper.selectSysUser(username);
    }
    /**
     * 功能描述:
     * 〈查询用户列表〉
     *
     * @param query 1
     * @return : java.util.List<com.msunhealth.usermanagement.dto.SysUserRoleDto>
     * @author : songhuanhao
     * @date : 2020/3/5 9:28
     */
    @Override
    public List<SysUserRoleDto> listUsers(Query query) {
        return userMapper.listUsers(query);
    }
    /**
     * 功能描述:
     * 〈根据userID查询用户〉
     *
     * @param userId 1
     * @return : com.msunhealth.usermanagement.dto.SysUserRoleDto
     * @author : songhuanhao
     * @date : 2020/3/5 9:29
     */
    @Override
    public SysUserRoleDto findUserByUserId(String userId) {
        SysUserRoleDto user = userMapper.findUserByUserId(userId);

        return user;
    }
    /**
     * 功能描述:
     * 〈新增用户〉
     *
     * @param sysUserRoleDto 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/6 15:16
     */
    @Override
    public AjaxRusult saveUserRole(SysUserRoleDto sysUserRoleDto) {
        User sysUser = userMapper.selectSysUser(sysUserRoleDto.getUserName());
        if (sysUser!=null){
            return AjaxRusult.error("该用户已存在，请勿重复添加");
        }
        //密码加密
        sysUserRoleDto.setPassWord(MD5Utils.MD5(sysUserRoleDto.getPassWord(), sysUserRoleDto.getUserName(), 1024));
        //将用户状态设为正常
        sysUserRoleDto.setStatus("1");
        //获取创建人ID
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        //设置创建人ID
        sysUserRoleDto.setCreateUserId(principal.getUserId().toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        String createTime = df.format(new Date());
        //设置创建时间
        sysUserRoleDto.setCreateTime(createTime);
        //插入用户表，添加新用户
        Integer integer = userMapper.insertUser(sysUserRoleDto);
        if (integer>0){
            User sysUserNew = userMapper.selectSysUser(sysUserRoleDto.getUserName());
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserNew.getUserId().toString());
            sysUserRole.setRoleId(sysUserRoleDto.getRoleId());
            Integer roleinteger = sysRoleMapper.insertUserRole(sysUserRole);
            if (roleinteger>0){
                return AjaxRusult.ok();
            }else {
                return AjaxRusult.error("添加用户角色失败");
            }
        }
        return AjaxRusult.error("添加用户失败");
    }
    /**
     * 功能描述:
     * 〈修改用户〉
     *
     * @param sysUserRoleDto 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/6 15:16
     */
    @Override
    public AjaxRusult updateUserRole(SysUserRoleDto sysUserRoleDto) {
        User sysUser = userMapper.selectSysUser(sysUserRoleDto.getUserName());
        if (sysUser==null){
            return AjaxRusult.error("该用户不存在，请检查");
        }
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String updateTime = df.format(new Date());
        //设置创建时间
        sysUserRoleDto.setUpdateTime(updateTime);
        try {
            Integer user = userMapper.updateUser(sysUserRoleDto);
            Integer role = sysRoleMapper.updateUserRole(sysUserRoleDto);
            return AjaxRusult.ok("修改成功");
        } catch (Exception e) {
            return AjaxRusult.error("修改失败");
        }
    }
    /**
     * 功能描述:
     * 〈批量删除用户角色〉
     *
     * @param ids 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 9:27
     */
    @Override
    public AjaxRusult delUserRole(List<String> ids) {
        if (ids!=null){
            for (String id : ids) {
                //注意，别忘删除用户角色关联
                Integer role = sysRoleMapper.delUserRole(id);
                Integer user = userMapper.delUser(id);
            }
            return AjaxRusult.ok("删除用户成功");
        }
        return AjaxRusult.error("请选择要删除的用户");
    }
    /**
     * 功能描述:
     * 〈批量重置密码〉
     *
     * @param sysUser 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 9:27
     */
    @Override
    public AjaxRusult resetPassword(List<User> sysUser) {
        if (sysUser != null) {
            for (User user : sysUser) {
                user.setPassword(MD5Utils.MD5(SysConstant.USER_DEFAULT_PASSWORD, user.getUserName(), 1024));
                userMapper.updateUserPassword(user);
            }
            return AjaxRusult.ok("重置密码成功");
        }
        return AjaxRusult.error("请选择需要重置密码的用户");
    }

    /**
     * 功能描述:
     * 〈修改当前用户的密码〉
     *
     * @param password 1
     * @param newPassword 2
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2020/3/11 13:23
     */
    @Override
    public AjaxRusult updatePassword(String password, String newPassword) {
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        String passWord = principal.getPassword();
        String inputPassword = MD5Utils.MD5(password, principal.getUserName(), 1024);
        if (passWord.equals(inputPassword)){
            principal.setPassword(MD5Utils.MD5(newPassword, principal.getUserName(), 1024));
            userMapper.updateUserPassword(principal);
            return AjaxRusult.ok("修改成功");
        }
        return AjaxRusult.error("原密码输入错误，请重新输入或联系管理员");
    }
}
