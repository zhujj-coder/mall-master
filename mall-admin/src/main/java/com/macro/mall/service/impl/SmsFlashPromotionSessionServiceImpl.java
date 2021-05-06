package com.macro.mall.service.impl;

import com.macro.mall.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.mapper.SmsFlashPromotionSessionMapper;
import com.macro.mall.model.SmsFlashPromotionSession;
import com.macro.mall.model.SmsFlashPromotionSessionExample;
import com.macro.mall.service.SmsFlashPromotionProductRelationService;
import com.macro.mall.service.SmsFlashPromotionSessionService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 限时购场次管理Service实现类
 * Created by macro on 2018/11/16.
 */
@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService {
    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired
    private SmsFlashPromotionProductRelationService relationService;
    @Autowired
    private UmsAdminService umsAdminService;
    @Override
    public int create(SmsFlashPromotionSession promotionSession) {
        promotionSession.setAdminId(umsAdminService.getCurrentAdmin().getId());
        promotionSession.setCreateTime(new Date());
        return promotionSessionMapper.insert(promotionSession);
    }

    @Override
    public int update(Long id, SmsFlashPromotionSession promotionSession) {
        promotionSession.setId(id);
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(umsAdminService.getCurrentAdmin().getId());
        return promotionSessionMapper.updateByExample(promotionSession,example);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(umsAdminService.getCurrentAdmin().getId());
        return promotionSessionMapper.updateByExampleSelective(promotionSession,example);
    }

    @Override
    public int delete(Long id) {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(umsAdminService.getCurrentAdmin().getId());
        return promotionSessionMapper.deleteByExample(example);
    }

    @Override
    public SmsFlashPromotionSession getItem(Long id) {
        return promotionSessionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotionSession> list() {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.or()
                .andAdminIdEqualTo(umsAdminService.getCurrentAdmin().getId());
        return promotionSessionMapper.selectByExample(example);
    }

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {

        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1)
                .andAdminIdEqualTo(umsAdminService.getCurrentAdmin().getId());
        List<SmsFlashPromotionSession> list = promotionSessionMapper.selectByExample(example);
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();
        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            long count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }
}
