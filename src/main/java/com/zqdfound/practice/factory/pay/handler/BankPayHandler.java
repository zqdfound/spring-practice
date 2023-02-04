package com.zqdfound.practice.factory.pay.handler;

import com.zqdfound.practice.factory.pay.PayChannelEnum;
import com.zqdfound.practice.factory.pay.PayHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@Component
public class BankPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.BANK;
    }

    @Override
    public void dealPay() {
        System.out.println("银行卡支付");
    }
}
