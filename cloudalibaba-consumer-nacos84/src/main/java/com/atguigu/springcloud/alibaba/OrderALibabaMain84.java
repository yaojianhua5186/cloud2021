package com.atguigu.springcloud.alibaba;

import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderALibabaMain84 {
    public static void main(String[] args) {
        //Linux下模式运行Sentinel时使用下面这行代码,sentinel可监控到客户端。
        System.setProperty(TransportConfig.HEARTBEAT_CLIENT_IP,"10.253.105.253");
        SpringApplication.run(OrderALibabaMain84.class,args);
    }
}
