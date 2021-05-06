package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.OmsPortalOrderService;
import com.macro.mall.portal.service.OmsPromotionService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
    @Resource
    private OmsCartItemMapper cartItemMapper;
    @Resource
    private PortalProductDao productDao;
    @Autowired
    private OmsPromotionService promotionService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private OmsPortalOrderService omsPortalOrderService;
    @Override
    public int add(OmsCartItem cartItem,Long adminId) {
        int count;
        UmsMember currentMember =memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        cartItem.setAdminId(adminId);
//        校验:买过不能买
        List<OmsOrderItem> list = omsPortalOrderService.queryByCartItem(cartItem);
        if(list!=null&&list.size()>0){
            throw  new MyException(ExceptionEnum.FLASH_BUY_FINISH);
        }

        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            count = cartItemMapper.insert(cartItem);
            return cartItem.getId().intValue();
        } else {
            cartItem.setModifyDate(new Date());
//            OmsCartItem byId = this.getById(existCartItem.getId(), adminId);
            if(existCartItem.getQuantity()>=existCartItem.getBuyLimit()){
                throw new MyException(ExceptionEnum.FLASH_BUY_LIMIT);
            }
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existCartItem);
            return  existCartItem.getId().intValue();
        }
    }

    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0)
                .andAdminIdEqualTo(cartItem.getAdminId());
        if (!StringUtils.isEmpty(cartItem.getProductSkuId())) {
            criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Override
    public List<OmsCartItem> list(Long memberId, Long adminId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId)
            .andAdminIdEqualTo(adminId);
        return cartItemMapper.selectByExample(example);
    }

    @Override
    public OmsCartItem getById(Long id, Long adminId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdEqualTo(id)
                .andAdminIdEqualTo(adminId);
        List<OmsCartItem> omsCartItems = cartItemMapper.selectByExample(example);
        if(omsCartItems.size()>0){
            return omsCartItems.get(0);
        }
        return null;
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds, Long adminId) {
        List<OmsCartItem> cartItemList = list(memberId,adminId);
        cartItemList.forEach(omsCartItem -> {
            omsCartItem.setProductAttrJson(JSONArray.parseArray(omsCartItem.getProductAttr()));
        });
        if(CollUtil.isNotEmpty(cartIds)){
            cartItemList = cartItemList.stream().filter(item->cartIds.contains(item.getId())).collect(Collectors.toList());
        }
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartItemList)){
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity,Long adminId) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId)
                .andAdminIdEqualTo(adminId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }

    @Override
    public int delete(Long memberId, List<Long> ids,Long amindId) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId)
        .andAdminIdEqualTo(amindId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public CartProduct getCartProduct(Long productId, Long adminId) {
        return productDao.getCartProduct(productId,adminId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem, Long adminId) {
        //删除原购物车信息
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        add(cartItem,adminId);
        return 1;
    }

    @Override
    public int clear(Long memberId,Long admindId) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
            .andAdminIdEqualTo(admindId);
        return cartItemMapper.updateByExampleSelective(record,example);
    }
}
