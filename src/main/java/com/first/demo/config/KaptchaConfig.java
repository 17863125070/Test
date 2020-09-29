package com.first.demo.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description:验证码配置，谷歌kaptcha
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2019/11/25 0:23
 * @Version 1.0
 */
@Configuration
public class KaptchaConfig {

    /**
     * 功能描述:
     * 〈根据配置返回一个DefaultKaptcha 默认验证码〉
     *
     * @param
     * @return : com.google.code.kaptcha.impl.DefaultKaptcha
     * @author : songhuanhao
     * @date : 2019/11/25 11:19
     */
    @Bean(name = "kaptcha")
    public DefaultKaptcha kaptcha(){
        DefaultKaptcha defaultKaptcha =new DefaultKaptcha();
        Properties properties =new Properties();
        //验证码的设置
//        验证码边框
        properties.put("kaptcha.border", "yes");
        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        properties.put("kaptcha.textproducer.font.color", "yellow");
        //验证码个数
        properties.put("kaptcha.textproducer.char.length", "1");
        Config config =new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
