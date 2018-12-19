package com.concurrent.delayqueue.mapper;

import com.concurrent.delayqueue.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> selectByStatus(int status);
    int updateByStatus(@Param("orderId")Long orderId, @Param("oldstatus")Integer oldstatus,@Param("newstatus")Integer newstatus);
}