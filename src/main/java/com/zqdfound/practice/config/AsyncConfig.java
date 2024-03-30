package com.zqdfound.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Configuration
public class AsyncConfig {
    @Bean("asyncThreadPool")
    public Executor getAsyncExecutor(){
        System.out.println("asyncThreadPool init");
        Executor executor = new ThreadPoolExecutor(
                10,20,60L, TimeUnit.SECONDS
                ,new ArrayBlockingQueue<>(100),new MyThreadFactory());
        return executor;
    }

    class MyThreadFactory implements ThreadFactory {
        final AtomicInteger threadNumber = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r){
            Thread t = new Thread(r);
            t.setName("测试线程池-"+threadNumber.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列容量
        executor.setQueueCapacity(30);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("测试线程池-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
