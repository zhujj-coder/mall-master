package com.macro.mall.dto;

import lombok.Data;

/**
 * 订单详情信息
 */
@Data
public class UmsMemberCount {
    private int todayCount;
    private int yesterdayCount;
    private int monthCount;
    private int totalCount;
}
