package com.atguigu.springcloud.alibaba.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/testA")
    public String testA() {
        System.out.println(Thread.currentThread().getName());
        // 测试线程数时候，开启
        // try {
        //     Thread.sleep(500);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        return "----testA";
    }

    @GetMapping("/testB")
    public String testB(){
        return "-----testB";
    }

    //sentinel降级策略
    @GetMapping("/testC")
    public String testC() throws InterruptedException {
        Thread.sleep(1000);
        return "----testC----";
    }

    //热点Key
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler="dealWith") // @RequestParam中，required=false表示当前请求可以不带该参数
    public String testHotkey(@RequestParam(value="param1",required=false) String param1,
                             @RequestParam(value="param2",required=false) String param2){
        return "-----testHotKey success---";
    }
    // 参数列表要和目标方法一样，BlockException一定要带上，否则不进这个方法
    public String dealWith(String param1, String param2, BlockException blockException) {
        blockException.printStackTrace();
        return "----testHotKey fail----";
    }

    //系统规则
//    @GetMapping("/byResource")
//    @SentinelResource(value = "byResource", blockHandler = "handleException")
//    public CommonResult byResource() {
//        return new CommonResult(200, "按照资源名称限流测试");
//    }
//
//    public CommonResult handleException(BlockException exception) {
//        return new CommonResult(500, exception.getClass().getCanonicalName() + "\t 服务不可用");
//    }
}
