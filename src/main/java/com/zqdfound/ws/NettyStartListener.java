package com.zqdfound.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 启动监听器
 * @Author: zhuangqingdian
 * @Date:2024/4/14
 */
@Component

@Slf4j
public class NettyStartListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 注入启动器
     */
    @Resource
    private WebSocketNettyServer webSocketNettyServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //判断event上下文中的父级是否为空
        if (event.getApplicationContext().getParent() == null) {
            try {
                log.info("WS开始启动。。。。。。");
                //为空则调用start方法
                webSocketNettyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
