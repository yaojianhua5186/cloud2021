package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.alibaba.entities.CommonResult;
import com.atguigu.springcloud.alibaba.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value="CLOUD-PAYMENT-SERVICE")//微服务提供者应用名称
public interface PaymentFeignService {
    //调用对应的服务提供者
    @GetMapping("/payment/get/{id}") //服务提供者的调用地址
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout();
}
