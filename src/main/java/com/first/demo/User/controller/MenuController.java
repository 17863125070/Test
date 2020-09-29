package com.first.demo.User.controller;

import com.alibaba.fastjson.JSONArray;
import com.first.demo.User.entity.SysMenu;
import com.first.demo.User.entity.SysRoleMenuParent;
import com.first.demo.User.entity.User;
import com.first.demo.User.service.SysMenuService;
import com.first.demo.common.anno.SysLog;
import com.first.demo.util.AjaxRusult;
import com.first.demo.util.BootstrapTableResult;
import com.first.demo.util.Query;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:菜单相关接口、菜单管理、根据用户查询出菜单等模块
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/28 11:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/sysmenu")
public class MenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 功能描述:
     * 〈根据用户Id查询出用户菜单〉
     *
     * @param
     * @return : com.msunhealth.utils.AjaxRusult
     * @author : songhuanhao
     * @date : 2019/11/28 13:59
     */
    @RequestMapping("/menu/user")
    @ResponseBody
    public AjaxRusult findUserMenu() {
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        return sysMenuService.findUserMenu(principal.getUserId());
    }

    /**
     * 功能描述:
     * 〈菜单管理模块展示，包含查询>
     *
     * @param query 1
     * @return : com.msunhealth.utils.BootstrapTableResult
     * @author : songhuanhao
     * @date : 2019/12/27 14:28
     */
    @RequestMapping("/menulist")
    @ResponseBody
    public BootstrapTableResult menulistByPage(Query query) {
        //调用分页查询出来的list
        List<SysMenu> sysMenus = sysMenuService.listMenus(query);
        //对查询出来的list做处理，封装成BootstrapTable需求的返回格式
        PageInfo<SysMenu> info = new PageInfo<SysMenu>(sysMenus);
        List<SysMenu> rows = info.getList();
        long total = info.getTotal();
        BootstrapTableResult bootstrapTableResult = new BootstrapTableResult(total, rows);
        return bootstrapTableResult;
    }

    @RequestMapping("/select")
    @ResponseBody
    public AjaxRusult selectZtree() {
        List<SysMenu> sysMenus = sysMenuService.menuZtree();
        return AjaxRusult.ok().put("menuList", sysMenus);
    }


    /**
     * 功能描述:
     * 〈对菜单管理进行功能筛选，筛选出系统管理下的各级标识〉
     *
     * @param
     * @return :com.msunhealth.utils.BootstrapTableResult
     * @author : wangshichao
     * @date : 2019/1/2
     */
    @RequestMapping("/menulisttwo")
    @ResponseBody
    public BootstrapTableResult selectMenu(Query query,String name) {
        List<SysMenu> sysMenus = sysMenuService.menuSelected(query,name);
        PageInfo<SysMenu> info = new PageInfo<SysMenu>(sysMenus);
        List<SysMenu> rows = info.getList();
        long total = info.getTotal();
        BootstrapTableResult bootstrapTableResult = new BootstrapTableResult(total, rows);
        return bootstrapTableResult;
    }

    /**
     * 功能描述:查询一二级菜单
     *
     * @param : []
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysRoleMenuParent>
     * @author : kanghongjian
     * @date : 2020/3/31  13:33
     */
    @RequestMapping("/selectAllMenu")
    @ResponseBody
    public List<SysRoleMenuParent> selectAllMenu() {


        List<SysRoleMenuParent> sysRoleMenuParents = sysMenuService.selectAllMenu();

        return sysRoleMenuParents;
    }

    /**
     * 功能描述:根据roleID获取所属权限二级菜单
     *
     * @param : []
     * @return : java.util.List<com.msunhealth.usermanagement.entity.SysRoleMenuParent>
     * @author : kanghongjian
     * @date : 2020/3/31  13:33
     */
    @RequestMapping("/selectByRoleIdAllMenu")
    @ResponseBody
    public List selectByRoleIdAllMenu(@RequestParam("roleId") String roleId) {


        List list = sysMenuService.selectByRoleIdAllMenu(roleId);

        return list;
    }

    /**
     * 功能描述:添加和修改权限
     *
     * @param : []
     * @return :
     * @author : kanghongjian
     * @date : 2020/3/31  13:33
     */
    @RequestMapping("/chieckAllMenuId")
    @ResponseBody
    public int chieckAllMenuId(@RequestBody String menuId) {


        JSONArray jsonArray = JSONArray.parseArray(menuId);
        int roleMenu = -1;
        int rint = -1;
        if (!jsonArray.isEmpty() && jsonArray !=null) {
            //数组最后一位是roleId,其余是一二级menuId
            String roleId = jsonArray.get(jsonArray.size() - 1).toString();
            //根据roleId删除所属菜单
            rint = sysMenuService.deleteByroleId(roleId);

            //前台返回的一二级菜单Id;
            List listOneAndTwoMenuId = new ArrayList();
            //获取所有三级菜单
            List selectAllButton = sysMenuService.selectAllButton();

            //组合两个List
            listOneAndTwoMenuId.addAll(selectAllButton);

            for (int i = 0; i < jsonArray.size() - 1; i++) {
                System.out.println(jsonArray.get(i).toString());

                listOneAndTwoMenuId.add(jsonArray.get(i).toString());
            }

            if (jsonArray.size() > 1) {
                //根据roleId添加菜单
                for (int i = 0; i < listOneAndTwoMenuId.size(); i++) {
                    roleMenu = sysMenuService.insertRoleMenu(roleId, listOneAndTwoMenuId.get(i).toString());
                }

            }

            if (rint < 0 ) {
                //添加或删除失败
                return 0;
            }
        }


        return 1;
    }

    @RequestMapping("/menu/info/{menuId}")
    @ResponseBody
    public AjaxRusult findMenuByMenuId(@PathVariable("menuId") Integer menuId) {
        SysMenu sysMenu =  sysMenuService.findThisMenuByMenuId(menuId);
        return AjaxRusult.ok().put("menu", sysMenu);
    }
    @RequestMapping("/menu/save")
    @ResponseBody
    @RequiresPermissions("sys:menu:save")
    @SysLog(value = "新增菜单")
    public AjaxRusult saveMenu(@RequestBody SysMenu sysMenu) {
        String  name=sysMenu.getName();
        Integer type=sysMenu.getType();
        Integer orderNum=sysMenu.getOrderNum();
        String parentName=sysMenu.getParentName();
//        Boolean istrue =  name != "" && name != null && type != null&&orderNum != null&&parentName != "" && parentName != null;
        if ("" != name && null != name && null != type && null != orderNum && null != parentName && "" != parentName)
        {
//        if (name != "" && name != null && type != null&&orderNum != null&&parentName != "" && parentName != null) {
            return sysMenuService.saveMenu(sysMenu);
        }
        return AjaxRusult.error("有空值请检查");
    }

    @RequestMapping("/menu/update")
    @ResponseBody
    //@RequiresRoles("管理员")
    @SysLog(value = "更新菜单")
    public AjaxRusult updateMenu(@RequestBody SysMenu sysMenu) {

        int rows=sysMenuService.updateMenu(sysMenu);
        if (rows > 0) {
            return AjaxRusult.ok();
        } else {
            return AjaxRusult.error("修改失败");
        }
    }
    @RequestMapping("/delrolemenu")
    @ResponseBody
    @SysLog("删除角色和菜单")
    public AjaxRusult delRoleMenu(@RequestBody List<String> ids) {
        return sysMenuService.delRoleMenu(ids);
    }

    @RequestMapping("/getName")
    @ResponseBody
    public List<SysMenu> getNameList(){
        return  sysMenuService.getNameList();
    }


}
