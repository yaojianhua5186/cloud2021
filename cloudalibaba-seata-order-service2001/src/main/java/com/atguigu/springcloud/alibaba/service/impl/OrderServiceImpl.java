package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;
    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说:
     * 下订单->减库存->减余额->改状态
     * @GlobalTransactional seata开启分布式事务，异常时回滚，name保证唯一即可
     * @param order 订单对象
     */
    @Override
    @GlobalTransactional(name = "wsy-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        //新建订单
        log.info("------->开始新建订单");
        orderDao.create(order);
        //扣减库存
        log.info("------->订单微服务开始调用库存，做扣减count  start");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("------->订单微服务开始调用库存，做扣减count  END");
        //扣减账户
        log.info("------->订单微服务开始调用账户,做扣减Money   start  Moneyt="+order.getMoney());
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("------->订单微服务开始调用账户,做扣减Money   END ");

        //修改订单状态 1代表完成
        log.info("------->修改订单状态  开始 Id = "+order.getId());
        orderDao.update(order.getId(),1);
    }
}
