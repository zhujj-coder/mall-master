package com.macro.mall.portal.controller;

import cn.hutool.core.net.multipart.UploadFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.mapper.UmsIntegrationConsumeSettingMapper;
import com.macro.mall.mapper.UmsUploadFileMapper;
import com.macro.mall.model.*;
import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.domain.LoginInfo;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.service.OmsPortalOrderService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020/7/4.
 *
 * @author genglintong.
 */
@Api(tags = "新的商户文件上传")
@RestController
@RequestMapping("/uploadFile")
@Slf4j
public class NewAdminController {

    @Autowired
    private UmsUploadFileMapper umsUploadFileMapper;

    /**
     * 文件上传
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload/{adminId}")
    public CommonResult loginByWeixin(@RequestBody UmsUploadFile umsUploadFile, @PathVariable Long adminId) {
        /* 获取appId 和secret*/
        umsUploadFile.setShareAdminId(adminId);
        umsUploadFileMapper.insert(umsUploadFile);
        return CommonResult.success(null);
    }

}
