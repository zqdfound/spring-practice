package com.zqdfound.practice.factory.pay.handler;

import com.zqdfound.practice.factory.pay.PayChannelEnum;
import com.zqdfound.practice.factory.pay.PayHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@Component
public class AliPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.ALIPAY;
    }

    @Override
    public void dealPay() {
        System.out.println("支付宝支付");
    }
}
