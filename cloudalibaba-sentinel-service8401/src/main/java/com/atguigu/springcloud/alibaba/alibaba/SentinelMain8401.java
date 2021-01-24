package com.atguigu.springcloud.alibaba.alibaba;


import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentinelMain8401 {
    public static void main(String[] args) {
        System.setProperty(TransportConfig.HEARTBEAT_CLIENT_IP, "10.253.105.38");
        SpringApplication.run(SentinelMain8401.class,args);
    }
}
