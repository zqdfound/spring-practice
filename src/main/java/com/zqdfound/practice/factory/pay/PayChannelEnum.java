package com.zqdfound.practice.factory.pay;

import lombok.Data;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/4
 */
public enum PayChannelEnum {
    BANK("bank","银行卡支付"),
    ALIPAY("alipay","支付宝"),
    WX("wx","微信"),
    ;
    private String code;
    private String name;

    PayChannelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static PayChannelEnum getChannelByCode(String codeV){
        for (PayChannelEnum p: PayChannelEnum.values()) {
            if(p.code.equals(codeV)){
                return p;
            }
        }
        return null;
    }

}
