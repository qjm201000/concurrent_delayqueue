package com.concurrent.delayqueue.component;

import com.concurrent.delayqueue.mapper.OrderInfoMapper;
import com.concurrent.delayqueue.message.OrderMessage;
import com.concurrent.delayqueue.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

/**
 * 处理订单超时
 */
@Component
@Lazy(false)
public class DelayOrderComponent {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    private static DelayQueue<OrderMessage> delayQueue = new DelayQueue<OrderMessage>();
    public static int getDelayQueueCount(){
        return delayQueue.size();
    }

    /**
     * 系统启动时，预先加载的数据@PostConstruct
     */
    @PostConstruct
    public void init(){
        /**初始化时加载数据库中需处理超时的订单**/
        System.out.println("获取数据库中需要处理的超时的订单");
        List<OrderInfo> list = orderInfoMapper.selectByStatus(0);
        for(int i=0;i<list.size();i++){
            OrderInfo orderInfo = list.get(i);
            OrderMessage orderMessage = new OrderMessage(orderInfo.getOrderId(),orderInfo.getCreateTime().getTime());
            this.addDelayQueue(orderMessage);//加入队列
        }

        /**
         * 启动线程，取延时消息
         */
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        OrderMessage orderMessage = delayQueue.take();
                        //处理超时订单
                        orderInfoMapper.updateByStatus(orderMessage.getOrderId(),0,2);//订单状态改成超时订单
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 加入延时队列
     * 用户下单时，调用此方法
     */
    public boolean addDelayQueue(OrderMessage orderMessage){
        return delayQueue.add(orderMessage);
    }

    /**
     * 从延时队列中删除
     * 用户主动取消，或者支付成功后，调用此方法
     */
    public boolean removeDelayQueue(Long orderId){
        for (Iterator<OrderMessage> iterator = delayQueue.iterator(); iterator.hasNext();) {
            OrderMessage queue = iterator.next();
            if(orderId.equals(queue.getOrderId())){
                return delayQueue.remove(queue);
            }
        }
        return false;
    }

}
