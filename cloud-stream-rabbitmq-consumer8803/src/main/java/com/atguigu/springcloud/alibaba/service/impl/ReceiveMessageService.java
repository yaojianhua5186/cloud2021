package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.service.IReceiveMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

// 这里使用@EnableBinding(Sink.class)，这是Stream里的注解，定义一个消息接收管道
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageService implements IReceiveMessage {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("ServerPort=" + serverPort + "消费者，消费消息：" + message.getPayload());
    }
}
