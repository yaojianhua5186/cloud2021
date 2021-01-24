package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //该注解用于使用consul或者zookeeper作为注册中心是注册服务
public class PaymnetMain8004 {
    public static void main(String[] args){
        SpringApplication.run(PaymnetMain8004.class,args);
    }
}
