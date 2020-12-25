package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@Slf4j
public class OrderRibbonLbController {
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult getPaymentById2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        log.info("status code=" + entity.getStatusCode());
        log.info("headers=" + entity.getHeaders());
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(404, "查找失败");
        }
    }
    @GetMapping("/consumer/payment/create2")
    public CommonResult create2(Payment payment) {
        return restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class).getBody();
    }

    //使用自定义负载均衡策略
    @GetMapping("/consumer/payment/loadbalance")
    public String getPaymentLoadBalance() {
        // 通过discoveryClient，使用服务提供者对应的应用名称大写获取所有服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() == 0) {
            return null;
        }
        // instances()方法拿到所有的服务实例，使用访问次数%服务实例数量求得目标服务的下标，返回下标对应的服务实例
        ServiceInstance instance = loadBalancer.instances(instances);
        URI uri = instance.getUri();// 获取这个实例的uri
        // /payment/loadbalance请求对应服务提供者controller中新加的映射方法，返回当前服务提供者的serverPort的值
        return restTemplate.getForObject(uri + "/payment/loadbalance", String.class);
    }

}
