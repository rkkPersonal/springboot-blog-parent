package org.xr.happy.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }


    public static <T> T getBean(Class<T> tClass) {
        if (tClass == null) {
            throw new RuntimeException("class  not foud");
        }

        T bean = applicationContext.getBean(tClass);
        return bean;
    }

    public static <T> T getBean(String className, Class<T> tClass) {
        if (tClass == null) {
            throw new RuntimeException("class  not found");
        }

        T bean = applicationContext.getBean(className, tClass);
        return bean;
    }
}
