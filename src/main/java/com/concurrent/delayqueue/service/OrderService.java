package com.concurrent.delayqueue.service;

import com.concurrent.delayqueue.component.DelayOrderComponent;
import com.concurrent.delayqueue.mapper.OrderInfoMapper;
import com.concurrent.delayqueue.message.OrderMessage;
import com.concurrent.delayqueue.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DelayOrderComponent delayOrderComponent;

    /**
     * 插入
     * @param orderInfo
     */
    @Transactional
    public void insert(OrderInfo orderInfo){
        orderInfoMapper.insert(orderInfo);
        //加入到延时队列中，用于超时未支付
        boolean flag = delayOrderComponent.addDelayQueue(new OrderMessage(orderInfo.getOrderId(),orderInfo.getCreateTime().getTime()));
        if(!flag){
            throw new RuntimeException();
        }
    }

    /**
     * 取消
     */
    @Transactional
    public void cancel(Long orderId){
        orderInfoMapper.updateByStatus(orderId,0,-1);
        delayOrderComponent.removeDelayQueue(orderId);
    }

    /**
     * 用户支付成功
     */
    public void paysuccess(Long orderId){
        orderInfoMapper.updateByStatus(orderId,0,1);
        delayOrderComponent.removeDelayQueue(orderId);
    }

}
