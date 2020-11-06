package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.OmsOrderReturnReasonMapper;
import com.macro.mall.model.OmsOrderReturnReason;
import com.macro.mall.model.OmsOrderReturnReasonExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.OmsOrderReturnReasonService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单原因管理Service实现类
 * Created by macro on 2018/10/17.
 */
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
    @Autowired
    private OmsOrderReturnReasonMapper returnReasonMapper;
    @Autowired
    private UmsAdminService adminService;

    @Override
    public int create(OmsOrderReturnReason returnReason) {
        UmsAdmin admin = adminService.getCurrentAdmin();
        returnReason.setCreateTime(new Date());
        returnReason.setAdminId(admin.getId());
        return returnReasonMapper.insert(returnReason);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        UmsAdmin admin = adminService.getCurrentAdmin();
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdEqualTo(id).andAdminIdEqualTo(admin.getId());
        return returnReasonMapper.updateByExampleSelective(returnReason, example);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andIdIn(ids).andAdminIdEqualTo(admin.getId());
        return returnReasonMapper.deleteByExample(example);
    }

    @Override
    public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andAdminIdEqualTo(admin.getId());
        example.setOrderByClause("sort desc");

        return returnReasonMapper.selectByExample(example);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        if(!status.equals(0)&&!status.equals(1)){
            return 0;
        }
        OmsOrderReturnReason record = new OmsOrderReturnReason();
        record.setStatus(status);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andIdIn(ids).andAdminIdEqualTo(admin.getId());
        return returnReasonMapper.updateByExampleSelective(record,example);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return returnReasonMapper.selectByPrimaryKey(id);
    }
}
