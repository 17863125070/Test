package com.first.demo.User.controller;

import com.alibaba.fastjson.JSONArray;
import com.first.demo.User.entity.SysRole;
import com.first.demo.User.service.SysRoleService;
import com.first.demo.util.AjaxRusult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述:权限的增加删除修改
 *
 * @author : kanghongjian
 * @date : 2020/4/1  14:28
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 功能描述:查询所有的权限
     *
     * @param : [offset, limit]
     * @return : com.github.pagehelper.PageInfo<com.msunhealth.usermanagement.entity.SysRole>
     * @author : kanghongjian
     * @date : 2020/4/1  14:29
     */
    @RequestMapping("/selectAllRole")
    @ResponseBody
    public PageInfo<SysRole> selectAllRole(@RequestParam(defaultValue = "1", value = "offset")
                                                   Integer offset, @RequestParam(defaultValue = "100", value = "limit") Integer limit) {
        PageHelper.offsetPage(offset, limit);
        List<SysRole> sysRoles = sysRoleService.selectAllRole();
        PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(sysRoles);

        return pageInfo;
    }

    /**
     * 功能描述:权限增加
     *
     * @param : [sysRole]
     * @return : int
     * @author : kanghongjian
     * @date : 2020/4/1  14:41
     */
    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRusult saveRole(@RequestBody SysRole sysRole) {

        Integer integer = sysRoleService.saveRole(sysRole);
        if (integer != 1) {
            return AjaxRusult.error();

        }
        return AjaxRusult.ok();
    }

    /**
     * 功能描述:修改增加
     *
     * @param : [sysRole]
     * @author : kanghongjian
     * @date : 2020/4/1  14:41
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRusult updateRole(@RequestBody SysRole sysRole) {

        Integer integer = sysRoleService.updateRole(sysRole);
        if (integer != 1) {
            return AjaxRusult.error();

        }

        return AjaxRusult.ok();
    }

    /**
     * 功能描述:删除权限
     *
     * @param : [sysRole]
     * @author : kanghongjian
     * @date : 2020/4/1  14:41
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRusult deleteRole(@RequestBody String roleId) {

        int code = 0;

        JSONArray jsonArray = JSONArray.parseArray(roleId);
        if (!jsonArray.isEmpty() || jsonArray.size() != 0) {

            for (int i = 0; i < jsonArray.size(); i++) {
                //根据roleId查询所对应的的用户，如果存在不能被删除

                List<String> userIdByroleId = sysRoleService.selectUserIdByroleId(jsonArray.get(i).toString());

                if (userIdByroleId.size() > 1) {
                    code++;
                    continue;
                } else {
                    sysRoleService.deleteRole(jsonArray.get(i).toString());

                }


            }

        }
        //说明有被使用的权限没有被删除
        if (code > 0) {

            return AjaxRusult.error(2, "有权限正在被使用不能被删除，请检查");

        }
        return AjaxRusult.ok();
    }

}
