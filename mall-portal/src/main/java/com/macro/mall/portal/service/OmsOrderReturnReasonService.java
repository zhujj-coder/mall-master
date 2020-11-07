package com.macro.mall.portal.service;

import com.macro.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * 订单原因管理Service
 * Created by macro on 2018/10/17.
 */
public interface OmsOrderReturnReasonService {

    /**
     * 分页获取退货原因
     */
    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum, Long adminId);
}
