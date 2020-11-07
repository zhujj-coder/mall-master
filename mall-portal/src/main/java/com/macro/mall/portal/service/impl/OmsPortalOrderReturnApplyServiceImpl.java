package com.macro.mall.portal.service.impl;

import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.OmsOrderReturnApplyMapper;
import com.macro.mall.model.OmsOrderReturnApply;
import com.macro.mall.model.OmsOrderReturnApplyExample;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.domain.OmsOrderReturnApplyParam;
import com.macro.mall.portal.service.OmsPortalOrderReturnApplyService;
import com.macro.mall.portal.service.OmsPortalOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单退货管理Service实现类
 * Created by macro on 2018/10/17.
 */
@Service
public class OmsPortalOrderReturnApplyServiceImpl implements OmsPortalOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Autowired
    private OmsPortalOrderService orderService;

    @Override
    public int create(OmsOrderReturnApplyParam returnApply, Long adminId) {
        OmsOrderReturnApplyExample example =new OmsOrderReturnApplyExample();
        example.or().andOrderIdEqualTo(returnApply.getOrderId());
        List<OmsOrderReturnApply> omsOrderReturnApplies = returnApplyMapper.selectByExample(example);
        if(omsOrderReturnApplies.size()>0){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR);
        }
        OmsOrderReturnApply realApply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        Long orderId = realApply.getOrderId();
        OmsOrderDetail detail = orderService.detail(orderId);
        BigDecimal payAmount = detail.getPayAmount();
        realApply.setProductRealPrice(payAmount);
        realApply.setProductCount(1);
        realApply.setReturnName(detail.getMemberUsername());
        realApply.setReturnPhone(detail.getMemberUsername());
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        realApply.setAdminId(adminId);
        return returnApplyMapper.insert(realApply);
    }
}
