package com.zqdfound.practice;

import com.zqdfound.practice.event.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@SpringBootTest
public class EventTest {

    @Autowired
    UserService userService;

    @Test
    public void testUserEvent(){
        userService.register();
    }
}
