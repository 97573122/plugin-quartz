package com.yt.plugin.quartz.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory
        implements
        ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    /**
     * 使job类支持spring的自动注入
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();

    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle)
            throws Exception {

        Object job = super.createJobInstance(bundle);
        // 解决不能spring注入bean的问题
        beanFactory.autowireBean(job);
        return job;

    }
}
