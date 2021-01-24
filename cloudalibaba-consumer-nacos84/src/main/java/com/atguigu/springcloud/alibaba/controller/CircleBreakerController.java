package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.entities.CommonResult;
import com.atguigu.springcloud.alibaba.entities.Payment;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.metadata.IIOInvalidTreeException;

@RestController
@Slf4j
public class CircleBreakerController {
    // nacos-payment-provider就是9003和9004微服务的spring.application.name的值
    private static final String  SERVICE_URL="http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value="/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback") //无配置，直接在页面抛出异常信息，展示不友好。
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") // fallback 配置，只负责业务异常。
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") // blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler") //当fallback和blockHandler都配置后，被限流降级抛出BlockException只会进入blockHandler指定的方法。
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgument，非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException，该ID没有对应记录，空指针异常");
        }
        return result;
    }
    //fallback
    public CommonResult handlerFallback(@PathVariable("id") Long id,Throwable throwable){
        Payment payment = new Payment(id,null);
        return new CommonResult(444,"异常fallback方法，异常内容是："+throwable.getMessage(),payment);
    }
    //blockHander
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult(444, "blockHandler-sentinel限流，BlockException：" + e.getMessage(), payment);
    }

    //Sentinel服务熔断OpenFeign===============================
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
