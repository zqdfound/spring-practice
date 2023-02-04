package com.zqdfound.practice.factory.pay;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@Component
public class PayDemo {
    @Resource
    PayFactory payFactory;

    public void pay(String code){
        PayHandler payHandler = payFactory.getPayHandler(code);
        payHandler.dealPay();
    }
}
