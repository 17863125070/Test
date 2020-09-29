package com.first.demo.shiro.realm;

import com.first.demo.User.entity.User;
import com.first.demo.User.service.SysMenuService;
import com.first.demo.User.service.SysRoleService;
import com.first.demo.User.service.userService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wangshichao
 * @Date: 2020/5/28 16:51
 * @Version 1.0
 */
@Component
public class UserRealm extends AuthorizingRealm {
    //注入用户角色信息service
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private userService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user =(User) principals.getPrimaryPrincipal();
        Integer userId =user.getUserId();
        //查询用户角色
        List<String> listRoles = sysRoleService.findRolesByUserId(userId);
        //查询用户的权限
        List<String> listPerms = sysMenuService.findPermsByUserId(userId);
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(listRoles);
        simpleAuthorizationInfo.addStringPermissions(listPerms);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户输入的用户名和密码，从登录接口中设置的Token中取
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;
        //username从token中取出来
        String username = usernamePasswordToken.getUsername();
        //将password取出来，password是一个char类型的数组，需要做处理转换成字符串
        String password = new String(usernamePasswordToken.getPassword());
        //调用service层比对用户名和密码
        User sysUser = userService.selectUserByUserName(username);
        if (null==sysUser){
            throw new UnknownAccountException("账号不存在！");
        }
        if (!sysUser.getPassword().equals(password)){
            throw new IncorrectCredentialsException("密码不正确！");
        }
        if (sysUser.getStatus().equals("0")){
            throw new LockedAccountException("账号被冻结！");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(sysUser,password,this.getName());

        return simpleAuthenticationInfo;
    }
}
