package com.macro.mall.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class FastRegisterAppDto {
//    名称（昵称）
    private String name;
    private String code;
    private String code_type;
    private String legal_persona_wechat;
    private String legal_persona_name;
    private String component_phone="13311493480";

}
