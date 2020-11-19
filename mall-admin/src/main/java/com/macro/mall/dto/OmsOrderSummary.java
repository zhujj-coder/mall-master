package com.macro.mall.dto;

import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情信息
 * Created by macro on 2018/10/11.
 */
@Data
public class OmsOrderSummary {
    private int count;
    private BigDecimal payAmount;
}
