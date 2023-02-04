package com.zqdfound.practice.factory.pay.handler;

import com.zqdfound.practice.factory.pay.PayChannelEnum;
import com.zqdfound.practice.factory.pay.PayHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@Component
public class WxPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WX;
    }

    @Override
    public void dealPay() {
        System.out.println("微信支付");
    }
}
