package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户名密码参数
 * Created by macro on 2019/10/9.
 */
@Getter
@Setter
public class GetLocationSrcParam {
    @ApiModelProperty(value = "位置", required = false)
    private String location;
}
