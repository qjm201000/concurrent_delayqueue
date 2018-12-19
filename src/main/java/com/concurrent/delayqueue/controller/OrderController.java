package com.concurrent.delayqueue.controller;

import com.concurrent.delayqueue.component.DelayOrderComponent;
import com.concurrent.delayqueue.model.OrderInfo;
import com.concurrent.delayqueue.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //创建订单
    @RequestMapping("insert")
    public void insert() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTime(new Date());
        orderInfo.setStatus(0);
        orderService.insert(orderInfo);
    }

    //取消订单
    @RequestMapping("cancel")
    public void cancel(Long orderId) {
        orderService.cancel(orderId);
    }

    //支付订单
    @RequestMapping("paysuccess")
    public void paysuccess(Long orderId) {
        orderService.paysuccess(orderId);
    }

    //查看队列中剩余处理数
    @RequestMapping("queuecount")
    public int queuecount() {
        return DelayOrderComponent.getDelayQueueCount();
    }
}
