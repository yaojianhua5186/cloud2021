package com.atguigu.springcloud.alibaba.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class feignConfig {
    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }
}
