package com.first.demo.User.service;

import com.first.demo.User.entity.SysMenu;
import com.first.demo.User.entity.SysRoleMenuParent;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/5/29 17:52
 * @param:
 * @return:
 */
public interface SysMenuService {

    /**
     * 功能描述:
     * 〈根据用户Id查询授权，用于shiro认证〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 9:34
     */
    List<String> findPermsByUserId(Integer userId);

    /**
     * 功能描述:
     * 〈查询当前用户下的菜单〉
     *
     * @param userId 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2019/11/28 13:34
     */
    AjaxRusult findUserMenu(Integer userId);
    /**
     * 功能描述:
     * 〈分页查询查询菜单列表，同时具备搜索功能〉
     *
     * @param query 1
     * @return : com.msunhealth.utils.BootstrapTableResult
     * @author : songhuanhao
     * @date : 2019/12/26 11:06
     */
    List<SysMenu> listMenus(Query query);

    /**
     * 功能描述:
     * 〈〉
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysMenu>
     * @author : songhuanhao
     * @date : 2019/12/27 15:06
     */
    List<SysMenu> menuZtree();

    /**
     * 功能描述:
     * 〈对菜单管理进行功能筛选，筛选出系统管理下的各级标识〉
     *
     * @param
     * @return :com.msunhealth.utils.BootstrapTableResult
     * @author : wangshichao
     * @date : 2019/1/2
     */
    List<SysMenu> menuSelected(@Param("query") Query query,@Param("name") String name);


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
     * 功能描述:根据roleID获取所属权限菜单,查询二级菜单列表
     *
     * @param  :
     * @return :
     * @author : kanghongjian
     * @date   : 2020/3/31  13:25
     */
    List selectByRoleIdAllMenu(String roleId);

    /**
     * 功能描述:查询所有的三级菜单（按钮）
     *
     * @param  :
     * @return :
     * @author : kanghongjian
     * @date   : 2020/3/31  13:25
     */
    List selectAllButton();

    /**
     * 功能描述:根据roleID删除所属权限菜单
     *
     * @author : kanghongjian
     * @date   : 2020/4/1  10:28
     */
    int deleteByroleId(String roleId);


    /**
     * 功能描述:插入权限
     *
     * @author : kanghongjian
     * @date   : 2020/4/1  11:02
     */
    int insertRoleMenu(String roleId, String menuId);


    SysMenu findThisMenuByMenuId(Integer menuId);

    AjaxRusult saveMenu(SysMenu sysMenu);

    int updateMenu(SysMenu sysMenu);

    AjaxRusult delRoleMenu(List<String> ids);


    List<SysMenu> getNameList();
}
