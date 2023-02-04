package com.zqdfound.practice;

import com.zqdfound.practice.factory.pay.PayDemo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@SpringBootTest
public class FactoryTestg {

    @Resource
    PayDemo payDemo;

    @Test
    public void testPay(){
        payDemo.pay("alipay");
    }
}
