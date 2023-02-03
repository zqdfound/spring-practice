package com.zqdfound.issue;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * https://articles.zsxq.com/id_0xzi96f3k6rw.html
 * https://github.com/spring-projects/spring-framework/issues/22263
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Configuration
@EnableScheduling
@SpringBootApplication
public class DemoApplication implements ApplicationContextAware, SchedulingConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    private ApplicationContext context;

    public static class Volatile { }

    @Scheduled(fixedRate = 400)
    public void addAndRemove() {

        BeanDefinitionRegistry factory = (BeanDefinitionRegistry)context.getAutowireCapableBeanFactory();

        // Simulate add/remove of some beans in one background thread.
        // Using larger numbers here makes the exception increasingly easier to hit in get().
        for (int i = 0; i < 1000; i++) {
            String beanName = "volatile" + i;
            if (factory.containsBeanDefinition(beanName)) {
                factory.removeBeanDefinition(beanName);
            }
            factory.registerBeanDefinition(beanName, BeanDefinitionBuilder.genericBeanDefinition(Volatile.class).getBeanDefinition());
        }
    }

    public static class Stable { }

    @Bean
    public Stable stable()
    {
        return new Stable();
    }

    @Scheduled(fixedRate = 1)
    public void get() {
        try {
            // Here get a bean that is not the one(s) being added/removed. Expect to be able to get
            // it every time.
            context.getBean(Stable.class);

        } catch (NoSuchBeanDefinitionException e) {

            // Eventually NoSuchBeanDefinitionException occurs (the missing bean being one of the Volatile ones!)
            // In DefaultListableBeanFactory.removeBeanDefinition the map is modified, then it starts replacing
            // the list w/ new copy. Meanwhile in getBean it iterates across that list in doGetBeanNamesForType,
            // but then gets from the map. They're not in the map anymore.

            throw new RuntimeException("This is the problem", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        // It's necessary to have get() and addAndRemove running on separate threads
        threadPoolTaskScheduler.setPoolSize(2);
        threadPoolTaskScheduler.initialize();
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
