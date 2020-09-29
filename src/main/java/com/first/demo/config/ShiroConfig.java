package com.first.demo.config;


import com.first.demo.shiro.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: wsc on 2020/6/1 15:46
 * @param:
 * @return:
 */
@Configuration
public class ShiroConfig {


/**
     * 功能描述:
     * 〈创建sessionManager会话管理，第一步先需要创建会话管理〉
     *
     * @param
     * @return : org.apache.shiro.session.mgt.SessionManager
     * @author : songhuanhao
     * @date : 2019/11/21 13:33
     */

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(-1000);
        //扫描session线程,负责清理超时会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
/**
     * 功能描述:
     * 〈创建securityManager，第二步shiro的核心〉
     *
     * @param userRealm      1
     * @param sessionManager 2
     * @return : java.lang.SecurityManager
     * @author : songhuanhao
     * @date : 2019/11/21 13:50
     */

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);

        //cookie管理 记住我
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();
        cookie.setMaxAge(60000);//秒
        cookie.setPath("/");
        securityManager.setRememberMeManager(rememberMeManager);

        //缓存管理
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        securityManager.setCacheManager(cacheManager);

        return securityManager;
    }

/**
     * 功能描述:
     * 〈shirofilter设置，第三步，shiro核心〉
     *
     * @param securityManager 1
     * @return : org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author : songhuanhao
     * @date : 2019/11/21 14:12
     */

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //shiro的核心接口配置
        shiroFilter.setSecurityManager(securityManager);
        //登录
        shiroFilter.setLoginUrl("/login.html");
        //认证成功
        shiroFilter.setSuccessUrl("/index.html");
        //未授权
        //shiroFilter.setUnauthorizedUrl("/unauthorized.html");
        //未授权
        shiroFilter.setUnauthorizedUrl("/unauthorized.html");

        //拦截路径的详细设置，hash里面有顺序，配置也需要有顺序，范围大的放后面，不然全被拦截了，/**要放在最后
        //anon：它对应的过滤器里面是空的,什么都没做,后面的*表示参数,比方说login.jsp?main -->
        //authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterMap.put("/sys/logout", "logout");
        //filterMap.put("/public/**", "anon");
        //匿名访问,放行
        filterMap.put("/login.html", "anon");
        filterMap.put("/public/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/captcha.jpg", "anon");
        //认证访问
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
/**
     * 功能描述:
     * 〈创建lifecycleBeanPostProcessor对象生命周期，第四步〉
     *
     * @param
     * @return : org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @author : songhuanhao
     * @date : 2019/11/21 14:40
     */

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

/**
     * 功能描述:
     * 〈开启Shiro的注解,需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证〉
     * 〈配置以下两个bean即可实现此功能〉
     *
     * @return : org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @author : songhuanhao
     * @date : 2019/11/21 14:44
     */

    @Bean(name = "defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        //代理层的对象的创建，cglib这种方式创建，controller类不是接口，要在类中使用需要用cglib方式创建代理对象
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

/**
     * 功能描述:
     * 〈配置shiro注解生效〉
     *
     * @param securityManager 1
     * @return : org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author : songhuanhao
     * @date : 2019/11/21 14:47
     */

    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
