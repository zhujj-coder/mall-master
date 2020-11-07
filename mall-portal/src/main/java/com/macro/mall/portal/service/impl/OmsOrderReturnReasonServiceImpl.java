package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.OmsOrderReturnReasonMapper;
import com.macro.mall.model.OmsOrderReturnReason;
import com.macro.mall.model.OmsOrderReturnReasonExample;
import com.macro.mall.portal.service.OmsOrderReturnReasonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单原因管理Service实现类
 * Created by macro on 2018/10/17.
 */
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
    @Resource
    private OmsOrderReturnReasonMapper returnReasonMapper;
    @Override
    public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum, Long adminId) {
        PageHelper.startPage(pageNum,pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andAdminIdEqualTo(adminId).andStatusEqualTo(1);
        example.setOrderByClause("sort desc");
        return returnReasonMapper.selectByExample(example);
    }

}
