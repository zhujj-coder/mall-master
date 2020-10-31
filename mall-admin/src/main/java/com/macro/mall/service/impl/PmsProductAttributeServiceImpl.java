package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.PmsProductAttributeDao;
import com.macro.mall.dto.PmsProductAttributeParam;
import com.macro.mall.dto.ProductAttrInfo;
import com.macro.mall.mapper.PmsProductAttributeCategoryMapper;
import com.macro.mall.mapper.PmsProductAttributeMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.PmsProductAttributeService;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Resource
    private PmsProductAttributeMapper productAttributeMapper;
    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Resource
    private PmsProductAttributeDao productAttributeDao;
    @Autowired
    private UmsAdminService umsAdminService;
    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type)
        .andAdminIdEqualTo(currentAdmin.getId());
        return productAttributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam pmsProductAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeParam, pmsProductAttribute);
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        pmsProductAttribute.setAdminId(currentAdmin.getId());
        int count = productAttributeMapper.insertSelective(pmsProductAttribute);
        //新增商品属性以后需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        if(pmsProductAttribute.getType()==0){
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()+1);
        }else if(pmsProductAttribute.getType()==1){
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()+1);
        }
        PmsProductAttributeCategoryExample example2 =new PmsProductAttributeCategoryExample();
        example2.or().andIdEqualTo(pmsProductAttributeCategory.getId())
                .andAdminIdEqualTo(currentAdmin.getId());
        productAttributeCategoryMapper.updateByExampleSelective(pmsProductAttributeCategory,example2);
        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        pmsProductAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        PmsProductAttributeExample example =new PmsProductAttributeExample();
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        pmsProductAttribute.setAdminId(currentAdmin.getId());
        example.or().andIdEqualTo(pmsProductAttribute.getId())
                .andAdminIdEqualTo(currentAdmin.getId());
        return productAttributeMapper.updateByExampleSelective(pmsProductAttribute,example);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(id);
        if(pmsProductAttribute.getAdminId().equals(currentAdmin.getId())){
            return pmsProductAttribute;
        }
        return null;
    }

    @Override
    public int delete(List<Long> ids) {
        //获取分类
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        Integer type = pmsProductAttribute.getType();
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        pmsProductAttribute.setAdminId(currentAdmin.getId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids)
        .andAdminIdEqualTo(currentAdmin.getId());
        int count = productAttributeMapper.deleteByExample(example);
        //删除完成后修改数量
        if(type==0){
            if(pmsProductAttributeCategory.getAttributeCount()>=count){
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()-count);
            }else{
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        }else if(type==1){
            if(pmsProductAttributeCategory.getParamCount()>=count){
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()-count);
            }else{
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        PmsProductAttributeCategoryExample example2 =new PmsProductAttributeCategoryExample();
        example2.or().andIdEqualTo(pmsProductAttributeCategory.getId())
                .andAdminIdEqualTo(currentAdmin.getId());
        productAttributeCategoryMapper.updateByExampleSelective(pmsProductAttributeCategory,example2);
        return count;
    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
