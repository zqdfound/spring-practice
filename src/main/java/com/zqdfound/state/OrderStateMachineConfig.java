package com.zqdfound.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfig;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.ModelData;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.model.ConfigurationData;
import org.springframework.statemachine.config.model.StatesData;
import org.springframework.statemachine.config.model.TransitionsData;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;

import java.util.EnumSet;

/**
 * @Author: zhuangqingdian
 * @Date:2024/3/26
 */
@Slf4j
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus,OrderStatusChangeEvent> {

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }
    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER)
                .event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH)
                .event(OrderStatusChangeEvent.RECEIVED);
    }


    /**
     * 持久化配置
     * 在实际使用中，可以配合Redis等进行持久化操作
     * @return
     */
    @Bean
    public DefaultStateMachinePersister persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object,Order>() {

            @Override
            public void write(StateMachineContext<Object, Object> stateMachineContext, Order order) throws Exception {
                log.info("持久化WRITE：订单信息：{}",order.toString());
            }

            @Override
            public StateMachineContext<Object, Object> read(Order order) throws Exception {
                log.info("持久化READ：订单信息：{}",order.toString());
                return null;
            }
        });
    }


}
