package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.entities.CommonResult;
import com.atguigu.springcloud.alibaba.entities.Payment;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value="/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果：{}"+result);
        int age = 10/2;
        if(result > 0){
            return new CommonResult<>(200, "插入数据库成功,serverPort:"+serverPort, result);
        }else{
            return new CommonResult<>(500, "插入数据库失败,serverPort:"+serverPort, null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果：{}", payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功,serverPort:"+serverPort, payment);
        } else {
            return new CommonResult<Payment>(400, "没有记录,serverPort:"+serverPort, null);
        }
    }

    @GetMapping(value="/paymnet/discovery")
    public Object discovery(){
        // 获取所有的服务
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("Eureka中的服务：" + service);
        }
        /* 根据服务名获取服务实例 */
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return discoveryClient;
    }

    //手写负责均衡，返回服务端端口号
    @GetMapping(value="/payment/loadbalance")
    public String getPaymentLB(){
        return serverPort;
    }

    //OpenFeign 超时控制设置
    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){e.printStackTrace();}
        return serverPort;
    }

    //测试Spring cloud sleuth
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "this is zipkin feedback";
    }
}
