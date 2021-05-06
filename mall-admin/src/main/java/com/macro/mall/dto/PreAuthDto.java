package com.macro.mall.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PreAuthDto {
//    statue: "exist": 已授权
    private String status;


}
