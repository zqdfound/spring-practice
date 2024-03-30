package com.zqdfound.state.service;

import com.zqdfound.state.Order;

import java.util.Map;

/**
 * @Author: zhuangqingdian
 * @Date:2024/3/26
 */
public interface IOrderService {
    //创建新订单
    Order create();
    //发起支付
    Order pay(int id);
    //订单发货
    Order deliver(int id);
    //订单收货
    Order receive(int id);
    //获取所有订单信息
    Map<Integer, Order> getOrders();
}
