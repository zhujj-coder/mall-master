package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.OmsOrderReturnApplyDao;
import com.macro.mall.dto.OmsOrderReturnApplyResult;
import com.macro.mall.dto.OmsReturnApplyQueryParam;
import com.macro.mall.dto.OmsUpdateStatusParam;
import com.macro.mall.mapper.OmsOrderReturnApplyMapper;
import com.macro.mall.model.OmsOrderReturnApply;
import com.macro.mall.model.OmsOrderReturnApplyExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.OmsOrderReturnApplyService;
import com.macro.mall.service.OmsOrderService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单退货管理Service
 * Created by macro on 2018/10/18.
 */
@Service
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyDao returnApplyDao;
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private OmsOrderService orderService;

    @Override
    public List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
        UmsAdmin admin = adminService.getCurrentAdmin();
        queryParam.setAdminId(admin.getId());
        PageHelper.startPage(pageNum,pageSize);
        return returnApplyDao.getList(queryParam);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andIdIn(ids).andStatusEqualTo(3).andAdminIdEqualTo(admin.getId());
        return returnApplyMapper.deleteByExample(example);
    }

    @Override
    @Transactional
    public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
        Integer orderStatus=6;
        if(status.equals(1)){
            //确认退货
            returnApply.setId(id);
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());

//            订单状态6 确认退货

        }else if(status.equals(2)){
            //完成退货
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
//            订单状态7 完成退货
            orderStatus=7;
        }else if(status.equals(3)){
            //拒绝退货
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
//            订单状态8 拒绝退货
            orderStatus=8;
        }else{
            return 0;
        }
        OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andIdEqualTo(id).andAdminIdEqualTo(admin.getId());
        List<OmsOrderReturnApply> omsOrderReturnApplies = returnApplyMapper.selectByExample(example);
        orderService.updateStatus(omsOrderReturnApplies.get(0).getOrderId(),orderStatus);
        return returnApplyMapper.updateByExampleSelective(returnApply, example);
    }

    @Override
    public OmsOrderReturnApplyResult getItem(Long id) {
        return returnApplyDao.getDetail(id);
    }
}
