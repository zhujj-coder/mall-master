package com.macro.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单详情信息
 */
@Data
public class ProductPublishStatusCount {
    private int countPublish;
    private int countOff;
    private int countTotal;
}
