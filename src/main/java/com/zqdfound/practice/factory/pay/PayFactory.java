package com.zqdfound.practice.factory.pay;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付工厂
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
@Component
public class PayFactory implements InitializingBean {

    //所有实现PayHandler的类都会被加载进来
    @Resource
    private List<PayHandler> payHandlerList;

    private Map<PayChannelEnum,PayHandler> handlerMap = new HashMap<>();


    public PayHandler getPayHandler(String code){
        PayChannelEnum payChannelEnum = PayChannelEnum.getChannelByCode(code);
        return handlerMap.get(payChannelEnum);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        for (PayHandler p: payHandlerList) {
            handlerMap.put(p.getChannel(),p);
        }
    }
}
