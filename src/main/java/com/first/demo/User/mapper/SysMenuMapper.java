package com.first.demo.User.mapper;


import com.first.demo.User.entity.SysMenu;
import com.first.demo.User.entity.SysRoleMenu;
import com.first.demo.User.entity.SysRoleMenuChildren;
import com.first.demo.User.entity.SysRoleMenuParent;
import com.first.demo.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {

    /**
     * 功能描述:
     * 〈批量删除〉
     *
     * @param ids 1
     * @return : int
     * @author : songhuanhao
     * @date : 2019/11/27 9:13
     */
    int  deleteMenu(List<Long> ids);

    /**
     * 查询目录
     */
   //List<SysMenuService> findMenu();
    /**
     * 功能描述:
     * 〈查询用户权限，用于shiro认证〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 9:17
     */
    List<String> findPermsByUserId(Integer userId);

    /**
     * 功能描述:
     * 〈查询当前用户ID下的一级菜单〉
     *
     * @param
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author : songhuanhao
     * @date : 2019/11/28 11:27
     */
    List<Map<String,Object>> findDirMenuByUserId(Integer userId);
    /**
     * 功能描述:
     * 〈查询当前用户角色下和父菜单下的子菜单〉
     *
     * @param userId 1
     * @param parentId 2
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author : songhuanhao
     * @date : 2019/11/28 13:32
     */
    List<Map<String,Object>> findSubMenuByUserId(@Param("userId") Integer userId, @Param("parentId") Integer parentId);
    /**
     * 功能描述:
     * 〈分页查询菜单列表〉
     *
     * @param query 1
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysMenu>
     * @author : songhuanhao
     * @date : 2019/12/26 10:48
     */
    List<SysMenu> findMenuByPage(@Param("query") Query query);
    /**
     * 功能描述:
     * 〈查询菜单的树形结构〉
     *
     * @param  1
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysMenu>
     * @author : songhuanhao
     * @date : 2019/12/27 14:49
     */
    List<SysMenu> selectMenuTree();


    List<SysMenu> findMenu(@Param("query") Query query,@Param("name") String name);

    /**
     * 功能描述:
     *
     * @param  : 查询一二级菜单列表
     * @return :
     * @author : kanghongjian
     * @date   : 2020/3/31  13:25
     */
    List<SysRoleMenuParent> selectAllMenu();

    /**
     * 功能描述:根据roleID获取所属权限菜单
     *
     * @param  : 查询二级菜单列表
     * @return :
     * @author : kanghongjian
     * @date   : 2020/3/31  13:25
     */
    List<SysRoleMenuChildren> selectByRoleIdAllMenu(@Param("roleId") String roleId);

    /**
     * 功能描述:查询所有的三级菜单（按钮）
     *
     * @param  :
     * @return :
     * @author : kanghongjian
     * @date   : 2020/3/31  13:25
     */
    List<SysRoleMenuChildren> selectAllButton();

    /**
     * 功能描述:根据roleID删除所属权限菜单
     *
     * @author : kanghongjian
     * @date   : 2020/4/1  10:28
     */
    int deleteByroleId(@Param("roleId") String roleId);

    /**
     * 功能描述:插入权限
     *
     * @author : kanghongjian
     * @date   : 2020/4/1  11:02
     */
    int insertRoleMenu(@Param("sysRoleMenuID") String sysRoleMenuID, @Param("roleId") String roleId, @Param("menuId") String menuId);

    SysMenu findThisMenuByMenuId(Integer menuId);

    Integer insertMenu(SysMenu sysMenu);

    int updateMenu(@Param("sysMenu") SysMenu sysMenu);


    Integer upMenu(String id);

    Integer deleteBymenuId(String id);

    Integer insertRoleMenus(SysRoleMenu sysRoleMenu);

    /*Integer updateRoleMenu(@Param("menuId") String menuId , @Param("roleId") String roleId);*/

    String selectMaxMenuId();

    List<SysMenu> getNameList();
}