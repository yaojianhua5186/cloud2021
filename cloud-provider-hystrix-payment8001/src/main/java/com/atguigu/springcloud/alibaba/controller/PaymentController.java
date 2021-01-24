package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentServiceImpl paymentServiceImpl;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentServiceImpl.paymentInfo_OK(id);
        log.info(result);
        return result;
    }

    @GetMapping(value="/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id)throws InterruptedException{
        String result = paymentServiceImpl.paymentInfo_TimeOut(id);
        log.info(result);
        return result;
    }

    //======服务熔断
    @GetMapping(value="/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentServiceImpl.paymentCircuitBreaker(id);
        log.info("***** result = "+result);
        return result;
    }


}
