package com.macro.mall.service.impl;

import com.macro.mall.dao.OmsOrderSettingDao;
import com.macro.mall.mapper.OmsOrderSettingMapper;
import com.macro.mall.model.OmsOrderSetting;
import com.macro.mall.model.OmsOrderSettingExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.OmsOrderSettingService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单设置管理Service实现类
 * Created by macro on 2018/10/16.
 */
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;
    @Autowired
    private UmsAdminService adminService;

    @Override
    public OmsOrderSetting getItem(Long id) {
        OmsOrderSettingExample example = new OmsOrderSettingExample();
        UmsAdmin admin = adminService.getCurrentAdmin();
        example.createCriteria().andIdEqualTo(id)
                .andAdminIdEqualTo(admin.getId());
        return orderSettingMapper.selectByExample(example).get(0);
    }

    @Override
    public int update(Long id, OmsOrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
}
