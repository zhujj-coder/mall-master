package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.dao.HomeDao;
import com.macro.mall.portal.domain.FlashPromotionProduct;
import com.macro.mall.portal.domain.HomeContentResult;
import com.macro.mall.portal.domain.HomeFlashPromotion;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.service.HomeService;
import com.macro.mall.portal.service.PmsPortalProductService;
import com.macro.mall.portal.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 首页内容管理Service实现类
 * Created by macro on 2019/1/28.
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SmsHomeAdvertiseMapper advertiseMapper;
    @Autowired
    private HomeDao homeDao;
    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;
    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;
    @Autowired
    private CmsSubjectMapper subjectMapper;
    @Autowired
    private PmsPortalProductService productService;

    @Override
    public HomeContentResult content(Long adminId) {
        HomeContentResult result = new HomeContentResult();
        //获取首页广告
        result.setAdvertiseList(getHomeAdvertiseList(adminId));
        //获取推荐品牌
        result.setBrandList(homeDao.getRecommendBrandList(0,6,adminId));
        //获取秒杀信息
        result.setHomeFlashPromotion(getHomeFlashPromotion(adminId));
        //获取新品推荐
        result.setNewProductList(homeDao.getNewProductList(0,4,adminId));
        //获取人气推荐
        result.setHotProductList(homeDao.getHotProductList(0,4,adminId));
        //获取推荐专题
        result.setSubjectList(homeDao.getRecommendSubjectList(0,4,adminId));
        return result;
    }

    @Override
    public List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum) {
        // TODO: 2019/1/29 暂时默认推荐所有商品
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andDeleteStatusEqualTo(0)
                .andPublishStatusEqualTo(1);
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategory> getProductCateList(Long parentId,Long adminId) {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria()
                .andShowStatusEqualTo(1)
                .andParentIdEqualTo(parentId)
                .andAdminIdEqualTo(adminId);
        example.setOrderByClause("sort desc");
        return productCategoryMapper.selectByExample(example);
    }

    @Override
    public List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
        criteria.andShowStatusEqualTo(1)
            ;
        if(cateId!=null){
            criteria.andCategoryIdEqualTo(cateId);
        }
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize,Long  adminId) {
        int offset = pageSize * (pageNum - 1);
        return homeDao.getHotProductList(offset, pageSize,adminId);
    }

    @Override
    public List<PmsProduct> newProductList(Integer pageNum, Integer pageSize,Long  adminId) {
        int offset = pageSize * (pageNum - 1);
        return homeDao.getNewProductList(offset, pageSize,adminId);
    }

    @Override
    public HomeFlashPromotion getHomeFlashPromotion(Long adminId) {
        HomeFlashPromotion homeFlashPromotion = new HomeFlashPromotion();
        //获取当前秒杀活动
        Date now = new Date();
        SmsFlashPromotion flashPromotion = getFlashPromotion(now,adminId);
        if (flashPromotion != null) {
            // 设置活动时间
            String start = DateUtil.formatTime(flashPromotion.getStartDate());
            String end = DateUtil.formatTime(flashPromotion.getEndDate());
            homeFlashPromotion.setActivityDurationString(String.format("%s - %s",start,end));
            //获取当前秒杀场次
            SmsFlashPromotionSession flashPromotionSession = getFlashPromotionSession(now,adminId);
            if (flashPromotionSession != null) {
                homeFlashPromotion.setStartTime(flashPromotionSession.getStartTime());
                homeFlashPromotion.setEndTime(flashPromotionSession.getEndTime());
                homeFlashPromotion.setLeftTime(flashPromotionSession.getEndTime().getTime()-DateUtil.getTime(now).getTime());
                //获取下一个秒杀场次
                SmsFlashPromotionSession nextSession = getNextFlashPromotionSession(homeFlashPromotion.getStartTime(),adminId);
                if(nextSession!=null){
                    homeFlashPromotion.setNextStartTimeStr(nextSession.getStartTime().getHours()+"时"+nextSession.getStartTime().getMinutes()+"分");
                    homeFlashPromotion.setNextEndTimeStr(nextSession.getEndTime().getHours()+"时"+nextSession.getEndTime().getMinutes()+"分");
                }
                //获取秒杀商品
                List<FlashPromotionProduct> flashProductList = homeDao.getFlashProductList(flashPromotion.getId(), flashPromotionSession.getId(),adminId);
                flashProductList.forEach(item->{
                    item.setSellPercentage((item.getFlashPromotionCount()-item.getFlashPromotionStock())*100/item.getFlashPromotionCount());
                });
                homeFlashPromotion.setProductList(flashProductList);
            }else{
                SmsFlashPromotionSession nextSession = getNextFlashPromotionSession(Calendar.getInstance().getTime(),adminId);
                if(nextSession!=null){
                    homeFlashPromotion.setNextStartTimeStr(nextSession.getStartTime().getHours()+"时"+nextSession.getStartTime().getMinutes()+"分");
                    homeFlashPromotion.setNextEndTimeStr(nextSession.getEndTime().getHours()+"时"+nextSession.getEndTime().getMinutes()+"分");
                }
            }
        }
        return homeFlashPromotion;
    }

    //获取下一个场次信息
    private SmsFlashPromotionSession getNextFlashPromotionSession(Date date,Long adminId) {
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeGreaterThan(date)
                .andAdminIdEqualTo(adminId);
        sessionExample.setOrderByClause("start_time asc");
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    private List<SmsHomeAdvertise> getHomeAdvertiseList(Long adminId) {
        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        example.createCriteria().andTypeEqualTo(1).andStatusEqualTo(1)
            .andAdminIdEqualTo(adminId);
        example.setOrderByClause("sort desc");
        return advertiseMapper.selectByExample(example);
    }

    //根据时间获取秒杀活动
    private SmsFlashPromotion getFlashPromotion(Date date,Long adminId) {
        Date currDate = DateUtil.getDate(date);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.createCriteria()
                .andStatusEqualTo(1)
                .andStartDateLessThanOrEqualTo(currDate)
                .andEndDateGreaterThanOrEqualTo(currDate)
                .andAdminIdEqualTo(adminId);
        List<SmsFlashPromotion> flashPromotionList = flashPromotionMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(flashPromotionList)) {
            return flashPromotionList.get(0);
        }
        return null;
    }

    //根据时间获取秒杀场次
    private SmsFlashPromotionSession getFlashPromotionSession(Date date,Long adminId) {
//        Date currTime = DateUtil.getTime(date);
        SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
        sessionExample.createCriteria()
                .andStartTimeLessThanOrEqualTo(date)
                .andEndTimeGreaterThanOrEqualTo(date)
                .andAdminIdEqualTo(adminId);
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectByExample(sessionExample);
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }
    @Override
    public void checkCartItem(OmsCartItem cartItem){
        PmsPortalProductDetail detail = productService.detail(cartItem.getProductId(), cartItem.getAdminId());
        cartItem.setPrice(detail.getProduct().getPrice());
        FlashPromotionProduct flashPromotionProduct = detail.getFlashPromotionProduct();

        if(flashPromotionProduct!=null){
            //        库存不足 秒杀完了 来晚了
            if(flashPromotionProduct.getFlashPromotionStock()-cartItem.getQuantity()<0){
                throw new MyException(ExceptionEnum.FLASH_STOCK_EMPTY);
            }
            cartItem.setPrice(flashPromotionProduct.getFlashPromotionPrice());
            cartItem.setBuyLimit(flashPromotionProduct.getFlashPromotionLimit());
            cartItem.setFlashRelationId(flashPromotionProduct.getFlashRelationId());
        }
    }
}
