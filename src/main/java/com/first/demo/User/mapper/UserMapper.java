package com.first.demo.User.mapper;

import com.first.demo.User.dto.SysUserRoleDto;
import com.first.demo.User.entity.User;
import com.first.demo.util.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wangshichao
 * @Date: 2020/5/28 10:43
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    /*@Select("select * from sys_user")*/
    List<User> listSelectUser();

    /**
     * 功能描述:
     * 〈根据userName查询用户信息〉
     * @param username 1
     * @return : com.msunhealth.usermanagement.entity.SysUser
     * @author : songhuanhao
     * @date : 2019/11/25 15:19
     */
    User selectSysUser(String username);

    /**
     * 功能描述:
     * 〈用户管理 用户账号角色展示〉
     * @param 1
     * @return : java.util.List<com.msunhealth.usermanagement.dto.SysUserRoleDto>
     * @author : songhuanhao
     * @date : 2020/3/3 10:47
     */
    List<SysUserRoleDto> listUsers(@Param("query") Query query);

    /**
     * 功能描述:
     * 〈根据用户id查询用户〉
     * @param userId 1
     * @return : com.msunhealth.usermanagement.dto.SysUserRoleDto
     * @author : songhuanhao
     * @date : 2020/3/5 14:24
     */
    SysUserRoleDto findUserByUserId(String userId);

    /**
     * 功能描述:
     * 〈插入新增用户〉
     * @param sysUserRoleDto 1
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/6 10:10
     */
    Integer insertUser(@Param("sysUserRoleDto") SysUserRoleDto sysUserRoleDto);

    /**
     * 功能描述:
     * 〈更新用户〉
     * @param sysUserRoleDto 1
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/6 17:29
     */
    Integer updateUser(@Param("sysUserRoleDto") SysUserRoleDto sysUserRoleDto);

    Integer delUser(@Param("userId") String userId);

    /**
     * 功能描述:
     * 〈更新用户密码〉
     * @param sysUser
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/11 9:28
     */
    Integer updateUserPassword(@Param("sysUser") User sysUser);

    /**
     * @return
     * @Date 2020/4/20 14:25
     * @Author jiangheng
     * @Description //TODO  查询用户的用户名
     **/
    String selectUsername(Integer userId);
}
