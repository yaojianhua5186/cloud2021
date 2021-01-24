package com.atguigu.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    //@LoadBalanced  //访问策略---轮询;注销自动轮询策略
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
