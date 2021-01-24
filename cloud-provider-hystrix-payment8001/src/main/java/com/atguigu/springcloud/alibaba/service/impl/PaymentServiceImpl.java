package com.atguigu.springcloud.alibaba.service.impl;

import cn.hutool.core.lang.UUID;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "\t 8001,paymentInfo_OK, id=" + id;
    }

    //===============服务降级
    //程序运行超时
    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) throws InterruptedException {
        int time = 5000;
        Thread.sleep(time);
        return "线程池：" + Thread.currentThread().getName() + "\t 8001,paymentInfo_TimeOut, id=" + id + "\t耗时：" + time;
    }

    public String paymentInfo_TimeOutHandler(Integer id) throws InterruptedException {
        return "线程池：" + Thread.currentThread().getName() + "\t 8001,系统繁忙或运行错误，请稍后！ id=" + id;
    }

    //==============服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            // 一个rolling window内请求的次数，当请求次数达到10，才计算失败率，从而判断是否满足断路的条件
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 触发断路后的10s都会直接失败，超过10s后，尝试一次恢复
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 失败率达到60%的时候，进行断路
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        String uuid = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "\t调用成功，UUID=" + uuid;
    }
    // 服务降级方法
    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试，id=" + id;
    }
}
