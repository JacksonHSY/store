package com.ymkj.springside.modules.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHelper implements ApplicationContextAware {
	public static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// 在加载Spring时自动获得context
		SpringContextHelper.context = context;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) context.getBean(clz);
        return result;
    }

	public static <T> T getBean(String beanName, Class<T> clz) throws BeansException {
        T result = (T) context.getBean(beanName, clz);
        return result;
    }

}
