package com.atguigu.springcloud.alibaba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableConfigServer
public class ConfigCenterMain3344 {

    public static void main(String[] args) {
        log.info("开始启动");

        SpringApplication.run(ConfigCenterMain3344.class,args);

        log.info("结束启动");
    }
}
