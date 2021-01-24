package com.atguigu.springcloud.alibaba.controller;import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //cmd中通过curl发送一个post形式的refresh请求给3355 ； curl -X POST "http://localhost:3355/actuator/refresh
@Slf4j
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/configInfo")
    public String getConfiginfo() {
        log.info("serverPort = "+serverPort);
        return configInfo;
    }
}