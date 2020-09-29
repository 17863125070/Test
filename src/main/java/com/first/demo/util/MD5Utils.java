package com.first.demo.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Description:MD5加密密码工具类
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/25 14:38
 * @Version 1.0
 */
public class MD5Utils {
    /**
     * 功能描述:
     * 〈shiro提供的MD5加密工具类〉
     *
     * @param source 1 原密码
     * @param salt 2 盐值
     * @param hashIterations 3 迭代次数
     * @return : java.lang.String
     * @author : songhuanhao
     * @date : 2019/11/25 14:49
     */
    public static String MD5(String source,String salt,int hashIterations){
        if (null==source){
            return null;
        }

        //shiro提供得到MD5加密工具
        Md5Hash md5Hash = new Md5Hash(source,salt,hashIterations);

        return md5Hash.toString();
    }
    /**
     * 功能描述:
     * 〈用于生成MD5密码〉
     *
     * @param args 1
     * @return : void
     * @author : songhuanhao
     * @date : 2019/11/25 15:31
     */
    public static void main(String[] args){
        System.out.println(MD5("123456","songhuanhao",1024));
    }
}
