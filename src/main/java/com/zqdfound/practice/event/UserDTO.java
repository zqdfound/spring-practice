package com.zqdfound.practice.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: zhuangqingdian
 * @Data:2023/2/3
 */
@Getter
@Setter
@ToString
public class UserDTO extends ApplicationEvent {

    private Integer userId;
    private String name;
    private Integer age;
    private String remark;

    public UserDTO(Object source) {
        super(source);
    }

}
