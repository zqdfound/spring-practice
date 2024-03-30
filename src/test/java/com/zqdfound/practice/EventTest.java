package com.zqdfound.practice;

import com.zqdfound.practice.event.UserDTO;
import com.zqdfound.practice.event.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@SpringBootTest
public class EventTest {

    @Autowired
    UserService userService;
    @Autowired
    TaskExecutor taskExecutor;

    @Test
    public void testUserEvent() throws InterruptedException {
        UserDTO dto = new UserDTO(this);
        dto.setUserId(123);
        dto.setName("樱木花道");
        dto.setAge(11);
        dto.setRemark(" ");
        taskExecutor.execute(()->{
            userService.register(dto);
        });
        System.out.println("=============第二次触发===============");
        taskExecutor.execute(()->{
            userService.register(dto);
        });
        Thread.sleep(3000l);
        System.out.println(dto.toString());
    }

}
