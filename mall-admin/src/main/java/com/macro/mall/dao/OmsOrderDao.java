package com.macro.mall.dao;

import com.macro.mall.dto.*;
import com.macro.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 订单自定义查询Dao
 * Created by macro on 2018/10/12.
 */
public interface OmsOrderDao {
    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(@Param("id") Long id, @Param("adminId") Long adminId);

    /**
     *
     * @param adminId
     * @param startTime
     * @param endTime
     * @return
     */
    OmsOrderSummary getOrderSummary(@Param("adminId") Long adminId, @Param("startTime") LocalDateTime startTime, @Param("endTime")   LocalDateTime endTime);

    /**
     *
     * @param adminId
     * @return
     */
    OmsOrderStatusCount getOrderStatusCount(@Param("adminId") Long adminId);
}
