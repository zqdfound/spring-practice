package com.zqdfound.practice.event.service;

import com.zqdfound.practice.event.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Service
public class UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void register(){
        UserDTO dto = new UserDTO(this);
        dto.setUserId(123);
        dto.setName("樱木花道");
        dto.setAge(11);
        dto.setRemark(" ");
        applicationEventPublisher.publishEvent(dto);
        System.out.println("======发布事件=============");
    }

    public void register( UserDTO dto){
        applicationEventPublisher.publishEvent(dto);
        System.out.println("======发布事件=============");
    }

}
