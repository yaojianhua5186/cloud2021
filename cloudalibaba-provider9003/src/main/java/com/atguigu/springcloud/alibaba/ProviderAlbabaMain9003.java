package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderAlbabaMain9003 {
    public static void main(String[] args) {
        SpringApplication.run(ProviderAlbabaMain9003.class,args);
    }
}
