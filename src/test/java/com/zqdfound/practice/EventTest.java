package com.zqdfound.practice;

import com.zqdfound.practice.event.UserDTO;
import com.zqdfound.practice.event.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void testUserEvent() throws InterruptedException {
        UserDTO dto = new UserDTO(this);
        dto.setUserId(123);
        dto.setName("樱木花道");
        dto.setAge(11);
        dto.setRemark(" ");
        userService.register(dto);
        System.out.println("=============第二次触发===============");
        userService.register(dto);
        Thread.sleep(3000l);
        System.out.println(dto.toString());
    }

    public static void main(String[] args) {
        String key = "next6544827d453f59297caf3933";
        String secret = "5OLbCJvCTmZMfzrfUhvK2J1oODGajU6z5I6tSSTQN0";
        System.out.println(createAccessToken(key,secret));
    }

    public static String createAccessToken(String accessKey,String accessSecret){
        SecretKey secretKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        JwtBuilder builder = Jwts.builder();
        builder.claim("role","role.visit");
        builder.claim("timestamp",System.currentTimeMillis());
//        builder.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000));//10分钟有效
        builder.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000));//10分钟有效
        return accessKey + "@" + builder.signWith(secretKey).compact();
    }
}
