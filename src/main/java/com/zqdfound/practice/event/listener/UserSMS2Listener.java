package com.zqdfound.practice.event.listener;

import com.zqdfound.practice.event.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Component
@Slf4j
public class UserSMS2Listener {
    //通过注解实现监听器，优先级要高于接口实现的监听器
    @EventListener
    //@Async("asyncThreadPool")
    @Order(2)
    public void handleUserEvent(UserDTO userDTO){
        log.info("执行顺序：2");
        userDTO.setRemark(userDTO.getRemark()+"执行2");
//        System.out.println("触发注解实现的用户监听器SMS："+userDTO.toString());
        log.info("触发注解实现的用户监听器SMS：{}",userDTO.toString());
    }
}
