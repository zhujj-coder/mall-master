package com.macro.mall.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckwxverifynicknameDto {
//    名称（昵称）
    private String name;

}
