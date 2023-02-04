package com.zqdfound.practice.factory.pay;

/**
 *
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
public interface PayHandler {
    PayChannelEnum getChannel();
    void dealPay();
}
