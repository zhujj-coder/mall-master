package com.macro.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 生成订单时传入的参数
 * Created by macro on 2018/8/30.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDirectPayOrderParam {
    @ApiModelProperty("优惠券ID")
    private Long couponId;
    @ApiModelProperty("支付方式")
    private Integer payType;
    @ApiModelProperty("支付金额")
    private BigDecimal value;
    @ApiModelProperty(value = "备注")
    private String message;
}
