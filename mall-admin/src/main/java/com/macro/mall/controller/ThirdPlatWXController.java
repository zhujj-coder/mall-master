package com.macro.mall.controller;


import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.wx.XmlRequest;
import com.macro.mall.service.impl.ThirdPlatformWxService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(tags = "ThirdPlatWXController", description = "第三方平台授权")
@RequestMapping("/wx/")
public class ThirdPlatWXController {
    @Autowired
    private ThirdPlatformWxService thirdPlatformWxService;

    @PostMapping("getPreAuthUrl")
    @ResponseBody
    public CommonResult getPreAuthUrl(){
        return CommonResult.success(thirdPlatformWxService.getPreAuthCodeUrl());
    }
    @PostMapping("notify")
    @ResponseBody
    public String notify1(@RequestParam String signature, @RequestParam String timestamp,@RequestParam String nonce,@RequestParam String encrypt_type,@RequestParam String msg_signature, @RequestBody XmlRequest request){
        thirdPlatformWxService.getNewTicket(signature,timestamp,nonce,msg_signature,request);
        return "success";
    }
    @PostMapping("{id}/authCallBack")
    @ResponseBody
    public String authCallBack(@RequestParam String auth_code, @PathVariable Long id){
        thirdPlatformWxService.apiQueryAuth(id,auth_code);
        return "success";
    }
}
