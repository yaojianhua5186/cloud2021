package com.atguigu.springcloud.alibaba.service;

import org.springframework.messaging.Message;

public interface IReceiveMessage {
    public void input(Message<String> message);
}
