package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.alibaba.entities.Payment;

public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
