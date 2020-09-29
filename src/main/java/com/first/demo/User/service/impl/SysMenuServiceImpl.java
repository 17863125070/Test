package com.first.demo.User.service.impl;

import com.first.demo.User.entity.*;
import com.first.demo.User.mapper.SysMenuMapper;
import com.first.demo.User.mapper.SysRoleMapper;
import com.first.demo.User.service.SysMenuService;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.GetUserInfoUtils;
import com.first.demo.util.Query;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/6/1 14:36
 * @param:
 * @return:
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 功能描述:
     * 〈根据用户id查询权限〉
     *
     * @param userId 1
     * @return : java.util.List<java.lang.String>
     * @author : songhuanhao
     * @date : 2019/11/27 9:59
     */
    @Override
    public List<String> findPermsByUserId(Integer userId) {
        //库中原始数据，含有null项和重复权限
        List<String> listpermsAll = sysMenuMapper.findPermsByUserId(userId);
        //set集合不能重重，用于去重复
        Set<String> set = new HashSet<>();
        //对查出来的权限集合进行处理，去重复和nill项
        for (String listperm : listpermsAll) {
            if (null != listperm) {
                //将多个权限的情况的以","分割开
                String[] perms = listperm.split(",");
                for (String perm : perms) {
                    set.add(perm);
                }
            }
        }
        List<String> listperms = new ArrayList<>();
        listperms.addAll(set);
        return listperms;
    }

    /**
     * 功能描述:
     * 〈根据用户ID查询菜单〉
     * 前端需要封装的格式：
     * {
     * "menuList": [{
     * "menuId": 1,
     * "parentId": 0,
     * "parentName": null,
     * "name": "系统管理",
     * "url": null,
     * "perms": null,
     * "type": 0,
     * "icon": "fa fa-cog",
     * "orderNum": 0,
     * "open": null,
     * "list": [{
     * "menuId": 2,
     * "parentId": 1,
     * "parentName": null,
     * "name": "用户管理",
     * "url": "sys/user.html",
     * "perms": null,
     * "type": 1,
     * "icon": "fa fa-user",
     * "orderNum": 1,
     * "open": null,
     * "list": null
     * }]
     * }]
     * "code": 0,
     * "permissions": ["sys:schedule:info", "sys:menu:update", "sys:menu:delete", "sys:config:info", "sys:generator:list", "sys:menu:list", "sys:config:save", "sys:menu:perms", "sys:config:update", "sys:schedule:resume", "sys:user:delete", "sys:config:list", "sys:user:update", "sys:role:list", "sys:menu:info", "sys:menu:select", "sys:schedule:update", "sys:schedule:save", "sys:role:select", "sys:user:list", "sys:menu:save", "sys:role:save", "sys:schedule:log", "sys:role:info", "sys:schedule:delete", "sys:role:update", "sys:schedule:list", "sys:user:info", "sys:generator:code", "sys:schedule:run", "sys:config:delete", "sys:role:delete", "sys:user:save", "sys:schedule:pause", "sys:log:list"]
     * }
     *
     * @param userId 1
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2019/11/28 13:37
     */
    @Override
    public AjaxRusult findUserMenu(Integer userId) {
        List<Map<String, Object>> dirMenuByUserId = sysMenuMapper.findDirMenuByUserId(userId);
        //对产生的数据进行处理，封装成前端需求的数据格式
        for (Map<String, Object> map : dirMenuByUserId) {
            Integer menuId = Integer.parseInt(map.get("menuId").toString());
            ;
            map.put("list", sysMenuMapper.findSubMenuByUserId(userId, menuId));
        }
        List<String> perms = this.findPermsByUserId(userId);
        return AjaxRusult.ok().put("menuList", dirMenuByUserId).put("permissions", perms);
    }

    /**
     * 功能描述:
     * 〈分页查询菜单列表，具备搜索功能〉
     *
     * @param query 1
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysMenu>
     * @author : songhuanhao
     * @date : 2019/12/26 11:22
     */
    @Override
    public List<SysMenu> listMenus(Query query) {
        PageHelper.offsetPage(query.getOffset(), query.getLimit());//参数一：（当前页）页码，参数二：每页显示条数
        //PageHelper.startPage(pageNum,pageSize) 参数一：当前页(页码)，参数二：每页显示多少条
        List<SysMenu> menuByPage = sysMenuMapper.findMenuByPage(query);
        return menuByPage;
    }

    /**
     * 功能描述:
     * 〈查询菜单的ztree的树形结构图
     *
     * @param
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysMenu>
     * @author : songhuanhao
     * @date : 2019/12/27 15:07
     */
    @Override
    public List<SysMenu> menuZtree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectMenuTree();
        //为了能够添加根目录（和系统管理同级的目录）
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0);
        sysMenu.setType(0);
        sysMenu.setParentId(-1);
        sysMenu.setName("一级菜单");

        sysMenus.add(sysMenu);

        return sysMenus;
    }


    @Override
    public List<SysMenu> menuSelected(Query query,String name) {
        PageHelper.offsetPage(query.getOffset(), query.getLimit());//参数一：（当前页）页码，参数二：每页显示条数
        //PageHelper.startPage(pageNum,pageSize) 参数一：当前页(页码)，参数二：每页显示多少条

        List<SysMenu> list = sysMenuMapper.findMenu(query,name);
        return list;
    }

    /**
     * 功能描述:查询一二级菜单
     *
     * @param : []
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysRoleMenuParent>
     * @author : kanghongjian
     * @date : 2020/3/31  13:33
     */
    @Override
    public List<SysRoleMenuParent> selectAllMenu() {


        List<SysRoleMenuParent> sysRoleMenuParents = sysMenuMapper.selectAllMenu();
        for (SysRoleMenuParent sysRoleMenuParent : sysRoleMenuParents) {
            sysRoleMenuParent.setSpread(true);
        }
        return sysRoleMenuParents;
    }

    /**
     * 功能描述:根据roleId获取所属二级菜单Id
     *
     * @param : [roleId]
     * @return : java.util.List
     * @author : kanghongjian
     * @date : 2020/3/31  15:53
     */
    @Override
    public List selectByRoleIdAllMenu(String roleId) {

        List<SysRoleMenuChildren> list = sysMenuMapper.selectByRoleIdAllMenu(roleId);

        List menuIdList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (SysRoleMenuChildren sysRoleMenuParent : list) {
                menuIdList.add(sysRoleMenuParent.getId());

            }


        }
        return menuIdList;

    }

    /**
     * 功能描述:查询所有的三级菜单（按钮）
     *
     * @param :
     * @return :
     * @author : kanghongjian
     * @date : 2020/3/31  13:25
     */
    @Override
    public List selectAllButton() {

        List<SysRoleMenuChildren> sysRoleMenuChildren = sysMenuMapper.selectAllButton();
        List menuIdListButton = new ArrayList();
        if (sysRoleMenuChildren != null && !sysRoleMenuChildren.isEmpty()) {
            for (SysRoleMenuChildren sysRoleMenuParent : sysRoleMenuChildren) {
                menuIdListButton.add(sysRoleMenuParent.getId());

            }


        }
        return menuIdListButton;
    }

    /**
     * 功能描述:根据roleId删除菜单
     *
     * @param : [roleId]
     * @return : int
     * @author : kanghongjian
     * @date : 2020/4/1  10:31
     */

    @Override
    public int deleteByroleId(String roleId) {
        int i = sysMenuMapper.deleteByroleId(roleId);

        return i;
    }
    /**
     * 功能描述:插入权限菜单
     *
     * @param  : [roleId, menuId]
     * @return : int
     * @author : kanghongjian
     * @date   : 2020/4/1  11:05
     */
    @Override
    public int insertRoleMenu(String roleId, String menuId) {
        Random  r = new Random();

        long sysTime  = System.nanoTime();
        String sysRoleMenuID =String.valueOf(sysTime);
        int roleMenu = sysMenuMapper.insertRoleMenu(sysRoleMenuID,roleId, menuId);
        return roleMenu;
    }

    @Override
    public SysMenu findThisMenuByMenuId(Integer menuId) {
        SysMenu sysMenu=sysMenuMapper.findThisMenuByMenuId(menuId);
        return sysMenu;
    }

    @Override
    public AjaxRusult saveMenu(SysMenu sysMenu) {
        SysMenu sysMenu1 = sysMenuMapper.findThisMenuByMenuId(sysMenu.getMenuId());
        if (sysMenu1 != null) {
            return AjaxRusult.error("该菜单已存在，请勿重复添加");
        }
        sysMenu.setIcon(sysMenu.getIcon());
        sysMenu.setName(sysMenu.getName());
        sysMenu.setUrl(sysMenu.getUrl());
        sysMenu.setOrderNum(sysMenu.getOrderNum());
        sysMenu.setParentId(sysMenu.getParentId());
        sysMenu.setType(sysMenu.getType());
        sysMenu.setPerms(sysMenu.getPerms());
        Integer integer = sysMenuMapper.insertMenu(sysMenu);
        if (integer > 0) {
            SysRole sysRole = sysRoleMapper.findRolesByUserIds(GetUserInfoUtils.getuserId());
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            String maxMenuId=sysMenuMapper.selectMaxMenuId();
            sysRoleMenu.setMenuId(maxMenuId);
            sysRoleMenu.setRoleId(sysRole.getRoleId().toString());
            Integer rolemenulist = sysMenuMapper.insertRoleMenus(sysRoleMenu);
            if (rolemenulist > 0) {
                return AjaxRusult.ok();
            } else {
                return AjaxRusult.error("添加角色菜单失败");
            }
        }
        return AjaxRusult.error("添加菜单失败");
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
            int row = sysMenuMapper.updateMenu(sysMenu);
            return row;
    }
    /**
     * @Description:
     * @Company：
     * @Author: wsc on 2020/6/4 15:34
     * @param:
     * @return:
     */
    @Override
    public AjaxRusult delRoleMenu(List<String> ids) {
        if (ids!=null){
            for (String id : ids) {
                //注意，别忘删除角色菜单关联
                Integer roleMenu = sysMenuMapper.deleteBymenuId(id);
                Integer menu = sysMenuMapper.upMenu(id);
            }
            return AjaxRusult.ok("删除菜单成功");
        }
        return AjaxRusult.error("请选择要删除的菜单");
    }

    @Override
    public List<SysMenu> getNameList() {
        return sysMenuMapper.getNameList();
    }
}
