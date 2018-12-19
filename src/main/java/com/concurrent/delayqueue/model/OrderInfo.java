package com.concurrent.delayqueue.model;

import java.util.Date;

public class OrderInfo {
    private Long orderId;//订单状态
    private Date createTime;//创建时间
    private Integer status;//订单状态：0待支付1已支付-1取消2已超时

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}