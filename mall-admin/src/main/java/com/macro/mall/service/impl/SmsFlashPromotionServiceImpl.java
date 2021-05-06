package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.SmsFlashPromotionMapper;
import com.macro.mall.model.SmsFlashPromotion;
import com.macro.mall.model.SmsFlashPromotionExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.SmsFlashPromotionService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 限时购活动管理Service实现类
 * Created by macro on 2018/11/16.
 */
@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService {
    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;
    @Autowired
    private UmsAdminService umsAdminService;
    @Override
    public int create(SmsFlashPromotion flashPromotion) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        flashPromotion.setCreateTime(new Date());
        flashPromotion.setAdminId(currentAdmin.getId());
        return flashPromotionMapper.insert(flashPromotion);
    }

    @Override
    public int update(Long id, SmsFlashPromotion flashPromotion) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        flashPromotion.setId(id);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return flashPromotionMapper.updateByExampleSelective(flashPromotion,example);
    }

    @Override
    public int delete(Long id) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return flashPromotionMapper.deleteByExample(example);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotion flashPromotion = new SmsFlashPromotion();
        flashPromotion.setId(id);
        flashPromotion.setStatus(status);
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return flashPromotionMapper.updateByExample(flashPromotion,example);
    }

    @Override
    public SmsFlashPromotion getItem(Long id) {
        return flashPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotion> list(String keyword, Integer pageSize, Integer pageNum) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PageHelper.startPage(pageNum, pageSize);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andTitleLike("%" + keyword + "%");
        }
        example.createCriteria().andAdminIdEqualTo(currentAdmin.getId());
        return flashPromotionMapper.selectByExample(example);
    }
}
