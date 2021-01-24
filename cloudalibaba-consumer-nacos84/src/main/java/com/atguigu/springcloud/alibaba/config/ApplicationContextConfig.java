package com.atguigu.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//设置配置类
@Component
public class ApplicationContextConfig {

    //向容器中注册一个带有负载均衡的RestTemplate
    @Bean
    @LoadBalanced //轮询策略
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
