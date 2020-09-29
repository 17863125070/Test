package com.first.demo.common.aspect;

import com.alibaba.fastjson.JSON;
import com.first.demo.User.entity.User;
import com.first.demo.common.anno.SysLog;
import com.first.demo.util.HttpContextUtils;
import com.first.demo.util.IPUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/6/1 14:13
 * @param:
 * @return:
 */
@Aspect
@Component
public class SysLogAspect {

    @Pointcut(value = "@annotation(com.first.demo.common.anno.SysLog)")
    public void  myPointcut(){}


    /**
     * 执行后切入
     */
    @After("myPointcut()")
    public void afterAdvice(JoinPoint joinPoint){
        //ip地址
        String  ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());

        String targetName  =joinPoint.getTarget().getClass().getName();
        //方法
        String methodName = joinPoint.getSignature().getName();
        //被增强的方法参数
        Object  arg = joinPoint.getArgs();
        //参数转json
        String jsonArg = JSON.toJSONString(arg);
        //当前登录的用户
        User sysUser = (User) SecurityUtils.getSubject().getPrincipal();
        //登录用户中文名
        String cnName = sysUser.getCnName();
        //登录用户账号
        String userName = sysUser.getUserName();
        //1,先得到某个的 Method对象
        Signature signature =  joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method =  methodSignature.getMethod();

        //2,调用Method对象的方法，得到指定注解
        SysLog myLog = method.getAnnotation(SysLog.class);
        String opration = "";
        if (myLog!=null){
            opration = myLog.value();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateFormat = simpleDateFormat.format(new Date());

        System.out.println("日期"+dateFormat+"-->ip地址:"+ip+"-->用户："+cnName+"-->账号："+userName);
        System.out.println("操作的方法："+targetName+"."+methodName+"-->传递的参数"+jsonArg+"-->操作："+opration);
    }
}
