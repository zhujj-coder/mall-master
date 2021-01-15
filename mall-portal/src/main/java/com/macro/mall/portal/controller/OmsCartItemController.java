package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.PmsPortalProductService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理Controller
 * Created by macro on 2018/8/2.
 */
@Controller
@Api(tags = "OmsCartItemController", description = "购物车管理")
@RequestMapping("/cart")
public class OmsCartItemController {
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PmsPortalProductService productService;
    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/add/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OmsCartItem cartItem,@PathVariable Long adminId) {
        PmsPortalProductDetail detail = productService.detail(cartItem.getProductId(), adminId);
        cartItem.setPrice(detail.getProduct().getPrice());
        int count = cartItemService.add(cartItem,adminId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/list/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsCartItem>> list(@PathVariable Long adminId) {
        List<OmsCartItem> cartItemList = cartItemService.list(memberService.getCurrentMember().getId(),adminId);
        return CommonResult.success(cartItemList);
    }

    @ApiOperation("获取某个会员的购物车列表,包括促销信息")
    @RequestMapping(value = "/list/promotion/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CartPromotionItem>> listPromotion(@RequestParam(required = false) List<Long> cartIds,@PathVariable Long adminId) {
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(memberService.getCurrentMember().getId(), cartIds,adminId);
        return CommonResult.success(cartPromotionItemList);
    }

    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateQuantity(@RequestParam Long id,
                                       @RequestParam Integer quantity,@PathVariable Long adminId) {
        // 删除
        if(quantity<=0){
            List<Long> list =new ArrayList<>();
            list.add(id);
            int count =  cartItemService.delete(memberService.getCurrentMember().getId(), list,adminId);
            return CommonResult.success(count);
        }else{
            int count = cartItemService.updateQuantity(id, memberService.getCurrentMember().getId(), quantity,adminId);
            if (count > 0) {
                return CommonResult.success(count);
            }
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取购物车中某个商品的规格,用于重选规格")
    @RequestMapping(value = "/getProduct/{adminId}/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CartProduct> getCartProduct(@PathVariable Long productId,@PathVariable Long adminId) {
        CartProduct cartProduct = cartItemService.getCartProduct(productId,adminId);
        return CommonResult.success(cartProduct);
    }

    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "/update/attr/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAttr(@RequestBody OmsCartItem cartItem,@PathVariable  Long adminId) {
        int count = cartItemService.updateAttr(cartItem,adminId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "/delete/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids,@PathVariable  Long adminId) {
        int count = cartItemService.delete(memberService.getCurrentMember().getId(), ids,adminId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("清空购物车")
    @RequestMapping(value = "/clear/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult clear(@PathVariable Long adminId) {
        int count = cartItemService.clear(memberService.getCurrentMember().getId(),adminId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();

    }
}
