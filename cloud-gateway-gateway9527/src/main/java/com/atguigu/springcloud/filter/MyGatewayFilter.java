package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGatewayFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Mono<Void> result;
        // 获取Request，获取参数
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String username = queryParams.getFirst("username");
        // 这里可以对queryParams里的值做一些校验
        if (username == null) {
            log.info("非法用户");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            // 表示该请求已经处理完成
            result = exchange.getResponse().setComplete();
        } else {
            log.info("username  = "+username);
            result = chain.filter(exchange);
        }
        return result;
    }

    /**
     * 过滤器的优先级，值越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
