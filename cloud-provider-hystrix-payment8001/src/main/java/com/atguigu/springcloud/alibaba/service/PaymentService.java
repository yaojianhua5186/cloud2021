package com.atguigu.springcloud.alibaba.service;


import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {

    String paymentInfo_OK(Integer id);

    String paymentInfo_TimeOut(Integer id) throws InterruptedException;

    String paymentCircuitBreaker(Integer id);

}
