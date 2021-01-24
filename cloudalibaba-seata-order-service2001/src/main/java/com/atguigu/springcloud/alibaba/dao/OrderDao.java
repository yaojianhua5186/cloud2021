package com.atguigu.springcloud.alibaba.dao;

import com.atguigu.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    //创建订单
    int create(Order order);

    /**
     * 修改订单状态，从0改为1
     */
    int update(@Param("id") Long id, @Param("status") Integer status);
}
