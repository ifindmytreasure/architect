package com.chloe.mapper;

import com.chloe.model.vo.center.CenterOrderTrendVO;
import com.chloe.model.vo.center.CenterOrderVO;
import my.mapper.MyMapper;
import com.chloe.model.pojo.Orders;

import java.util.List;

public interface OrdersMapper extends MyMapper<Orders> {
    List<CenterOrderVO> queryUserOrder(String userId, Integer orderStatus);

    Integer getOrderStatusCount(Integer orderStatus, String userId, Integer isComment);

    List<CenterOrderTrendVO> getUserOrderTrend(String userId);
}