package com.first.demo.User.mapper;

import com.first.demo.User.dto.SysUserRoleDto;
import com.first.demo.User.entity.SysRole;
import com.first.demo.User.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/27 9:50
 * @Version 1.0
 */
public interface SysRoleMapper {


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
   List<String>   selectUserIdByroleId(@Param("roleId") String roleId);
    /**
     * 功能描述:添加权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  saveRole(@Param("sysRole") SysRole sysRole);
    /**
     * 功能描述:修改权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  updateRole(@Param("sysRole") SysRole sysRole);

    /**
     * 功能描述:删除权限
     * @author : kanghongjian
     * @date   : 2020/4/1  14:46
     */
    Integer  deleteRole(@Param("roleId") String roleId);

    /**
     * 功能描述:
     * 〈根据用户ID查询用户角色，用于shiro授权〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 9:51
     */
    List<String> findRolesByUserId(Integer userId);

    List<SysRole> listRoles();

    /**
     * 功能描述:
     * 〈在用户和角色关联关系表中添加映射关系〉
     *
     * @param sysUserRole 1
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/6 8:41
     */
    Integer insertUserRole(@Param("sysUserRole") SysUserRole sysUserRole);

    /**
     * 功能描述:
     * 〈更新用户角色〉
     *
     * @param sysUserRoleDto 1
     * @return : java.lang.Integer
     * @author : songhuanhao
     * @date : 2020/3/10 15:20
     */
    Integer updateUserRole(@Param("sysUserRoleDto") SysUserRoleDto sysUserRoleDto);

    Integer delUserRole(@Param("userId") String userId);


    SysRole findRolesByUserIds(@Param("userId")String userId);
}
