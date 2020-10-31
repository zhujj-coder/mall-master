package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.PmsProductAttributeCategoryDao;
import com.macro.mall.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.mapper.PmsProductAttributeCategoryMapper;
import com.macro.mall.model.PmsProductAttributeCategory;
import com.macro.mall.model.PmsProductAttributeCategoryExample;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.PmsProductAttributeCategoryService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * PmsProductAttributeCategoryService实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Resource
    private PmsProductAttributeCategoryDao productAttributeCategoryDao;
    @Autowired
    private UmsAdminService umsAdminService;
    @Override
    public int create(String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        productAttributeCategory.setAdminId(currentAdmin.getId());
        return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
    }

    @Override
    public int update(Long id, String name) {
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(id);
        productAttributeCategory.setName(name);
        productAttributeCategory.setId(id);
        PmsProductAttributeCategoryExample example =new PmsProductAttributeCategoryExample();
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        example.or().andIdEqualTo(productAttributeCategory.getId())
                .andAdminIdEqualTo(currentAdmin.getId());
        return productAttributeCategoryMapper.updateByExampleSelective(productAttributeCategory,example);
    }

    @Override
    public int delete(Long id) {
        PmsProductAttributeCategoryExample example =new PmsProductAttributeCategoryExample();
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        example.or().andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return productAttributeCategoryMapper.deleteByExample(example);
    }

    @Override
    public PmsProductAttributeCategory getItem(Long id) {
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(id);
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        if(currentAdmin.getId().equals(pmsProductAttributeCategory.getAdminId())){
            return pmsProductAttributeCategory;
        }
        return pmsProductAttributeCategory;
    }

    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PmsProductAttributeCategoryExample pmsProductAttributeCategoryExample = new PmsProductAttributeCategoryExample();
         pmsProductAttributeCategoryExample.or().andAdminIdEqualTo(currentAdmin.getId());
        return productAttributeCategoryMapper.selectByExample(pmsProductAttributeCategoryExample);
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return productAttributeCategoryDao.getListWithAttr();
    }
}
