package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.SmsHomeRecommendProductMapper;
import com.macro.mall.model.SmsHomeRecommendProduct;
import com.macro.mall.model.SmsHomeRecommendProductExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.SmsHomeRecommendProductService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 首页人气推荐管理Service实现类
 * Created by macro on 2018/11/7.
 */
@Service
public class SmsHomeRecommendProductServiceImpl implements SmsHomeRecommendProductService {
    @Autowired
    private SmsHomeRecommendProductMapper recommendProductMapper;
    @Autowired
    private UmsAdminService umsAdminService;
    @Override
    public int create(List<SmsHomeRecommendProduct> homeRecommendProductList) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        for (SmsHomeRecommendProduct recommendProduct : homeRecommendProductList) {
            recommendProduct.setRecommendStatus(1);
            recommendProduct.setSort(0);
            recommendProduct.setAdminId(currentAdmin.getId());
            recommendProductMapper.insert(recommendProduct);
        }
        return homeRecommendProductList.size();
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        SmsHomeRecommendProduct recommendProduct = new SmsHomeRecommendProduct();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        recommendProduct.setAdminId(currentAdmin.getId());
        SmsHomeRecommendProductExample example =new SmsHomeRecommendProductExample();
        example.or()
                .andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return recommendProductMapper.updateByExampleSelective(recommendProduct,example);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids)
                .andAdminIdEqualTo(currentAdmin.getId());
        return recommendProductMapper.deleteByExample(example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids)
                .andAdminIdEqualTo(currentAdmin.getId());
        SmsHomeRecommendProduct record = new SmsHomeRecommendProduct();
        record.setRecommendStatus(recommendStatus);
        return recommendProductMapper.updateByExampleSelective(record,example);
    }

    @Override
    public List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PageHelper.startPage(pageNum,pageSize);
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        SmsHomeRecommendProductExample.Criteria criteria = example.createCriteria();
        criteria.andAdminIdEqualTo(currentAdmin.getId());
        if(!StringUtils.isEmpty(productName)){
            criteria.andProductNameLike("%"+productName+"%");
        }
        if(recommendStatus!=null){
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return recommendProductMapper.selectByExample(example);
    }
}
