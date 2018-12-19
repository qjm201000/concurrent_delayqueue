package com.concurrent.delayqueue.message;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderMessage implements Delayed {
    private final static long DELAY = 15*60*1000L;//默认延迟15分钟

    private Long orderId;//订单号
    private Long expireTime;//过期时间
    public OrderMessage(Long orderId,Long createTime){
        this.orderId = orderId;
        this.expireTime = createTime + DELAY;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this){
            return 0;
        }
        if(other instanceof OrderMessage){
            OrderMessage otherRequest = (OrderMessage)other;
            long otherStartTime = otherRequest.expireTime;
            return (int)(this.expireTime - otherStartTime);
        }
        return 0;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
