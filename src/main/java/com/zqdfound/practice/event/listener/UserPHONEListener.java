package com.zqdfound.practice.event.listener;

import com.zqdfound.practice.event.UserDTO;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 接口实现监听器
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Component
public class UserPHONEListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == UserDTO.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("触发注解实现的用户监听器PHONE："+ event.toString());
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
