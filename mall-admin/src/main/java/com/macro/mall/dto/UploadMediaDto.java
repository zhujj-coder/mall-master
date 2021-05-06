package com.macro.mall.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UploadMediaDto {
// 小程序昵称
    private String name;
//    头像
    private String headImageUrl;
//    经营范围
    private ArrayList categories;
//    简介
    private String desc;

}
