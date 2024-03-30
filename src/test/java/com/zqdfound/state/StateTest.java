package com.zqdfound.state;

import com.zqdfound.practice.event.UserDTO;
import com.zqdfound.practice.event.service.UserService;
import com.zqdfound.state.service.IOrderService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@SpringBootTest
public class StateTest {

    @Resource(name = "orderService")
    IOrderService orderService;
    @Test
    public void state(){
        Thread.currentThread().setName("主线程");

        //ConfigurableApplicationContext context = SpringApplication.run(Test.class,args);

        //IOrderService orderService = (IOrderService)context.getBean("orderService");

        orderService.create();
        orderService.create();

        orderService.pay(1);

        new Thread("客户线程"){
            @Override
            public void run() {
                orderService.deliver(1);
                orderService.receive(1);
            }
        }.start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println("全部订单状态：" + orderService.getOrders());
    }
    public static void main(String[] args) {

        Thread.currentThread().setName("主线程");

        ConfigurableApplicationContext context = SpringApplication.run(Test.class,args);

        IOrderService orderService = (IOrderService)context.getBean("orderService");

        orderService.create();
        orderService.create();

        orderService.pay(1);

        new Thread("客户线程"){
            @Override
            public void run() {
                orderService.deliver(1);
                orderService.receive(1);
            }
        }.start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println("全部订单状态：" + orderService.getOrders());

    }

}
