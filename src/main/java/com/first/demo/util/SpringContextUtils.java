package com.first.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 *  Spring Context 工具类
 * 
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	//装饰着模式


	public static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("setApplicationContext"+applicationContext);
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 通过name获取 Bean
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	/**
	 * 通过class获取Bean.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * @param name
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}