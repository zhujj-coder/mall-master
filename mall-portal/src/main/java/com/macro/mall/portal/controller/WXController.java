package com.macro.mall.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.mapper.UmsIntegrationConsumeSettingMapper;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsIntegrationConsumeSetting;
import com.macro.mall.model.UmsIntegrationConsumeSettingExample;
import com.macro.mall.model.UmsMember;
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
import java.util.*;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020/7/4.
 *
 * @author genglintong.
 */
@Api(tags = "WX 相关对外接口")
@RestController
@RequestMapping("/wx")
@Slf4j
public class WXController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wx.webAccessTokenhttps}")
    private String webAccessTokenhttps;
    @Value("${wx.getaccountbasicinfo}")
    private String getaccountbasicinfo;
    @Value("${wx.payUrl}")
    private String payUrl;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private OmsPortalOrderService orderService;
    @Resource
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;

    /**
     * 显示登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login_by_weixin/{adminId}")
    public CommonResult loginByWeixin(@RequestBody LoginInfo loginInfo, HttpServletRequest request,@PathVariable Long adminId) {
        /* 获取appId 和secret*/
        UmsAdmin umsAdmin = umsAdminMapper.selectByPrimaryKey(adminId);
        //获取openid
        String requestUrl = String.format(this.webAccessTokenhttps,
                umsAdmin.getAppId(),
                umsAdmin.getAppSecret(),
                loginInfo.getCode());//通过自定义工具类组合出小程序需要的登录凭证 code

        JSONObject res = restTemplate.getForObject(requestUrl, JSONObject.class);
        String openId;
        if(res.getInteger("code")==200){
            openId=res.getString("data");
        }else{
            return CommonResult.failed(res.getString("message"));
        }
        String token = memberService.loginWx(openId);
        if (token == null) {
            memberService.registerByWX(openId,"",adminId);
            token = memberService.loginWx(openId);
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("openId", openId);
        tokenMap.put("userid", "1");

        return CommonResult.success(tokenMap);
    }
    /**
     * 静默登录
     */
    @ApiOperation(value = "静默登录")
    @RequestMapping("/login/{adminId}")
    public CommonResult loginByWeixin(@RequestParam String code,@PathVariable Long adminId) {
        /* 获取appId 和secret*/
        UmsAdmin umsAdmin = umsAdminMapper.selectByPrimaryKey(adminId);
        //获取openid
        String requestUrl = String.format(this.webAccessTokenhttps,
                umsAdmin.getAppId(),// redis 中获取没有用接口拿
                code);//通过自定义工具类组合出小程序需要的登录凭证 code

        JSONObject res = restTemplate.getForObject(requestUrl,JSONObject.class);
        String openId;
        if(res.getInteger("code")==200){
            openId=res.getString("data");
        }else{
            return CommonResult.failed(res.getString("message"));
        }

        String token = memberService.loginWx(openId);
        if (token == null) {
            memberService.registerByWX(openId,"",adminId);
            token = memberService.loginWx(openId);
        }
        UmsMember byOpenId = memberService.getByOpenId(openId);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("openId", openId);
        tokenMap.put("userid", openId);
        tokenMap.put("integration", byOpenId.getIntegration()==null?"0":byOpenId.getIntegration().toString());

//        公告
        try {
            String notice =getNotice(adminId);
            tokenMap.put("notice", notice);
        }catch (Exception e){
            log.error("获取公告异常",e);
        }

        return CommonResult.success(tokenMap);
    }

    private void initContact(Map<String, String> tokenMap,Long adminId) {
        UmsAdmin admin = umsAdminMapper.selectByPrimaryKey(adminId);
        if(admin!=null){
            tokenMap.put("contactMobile",admin.getContactMobile());
            tokenMap.put("contactAddress",admin.getContactAddress());
            Optional.ofNullable(admin.getVipEndDate()).ifPresent(date -> {
                if(date.after(new Date())){
                    tokenMap.put("isVIP","1");
                }else{
                    tokenMap.put("isVIP","0");
                }
            });
        }

    }

    @ApiOperation("获取小程序商户信息")
    @RequestMapping("/getaccountbasicinfo/{adminId}")
    public CommonResult getaccountbasicinfo(@PathVariable Long adminId){
        String requestUrl = String.format(this.getaccountbasicinfo,adminId.toString());
        JSONObject res = restTemplate.getForObject(requestUrl,JSONObject.class);
        String openId;
        if(res.getInteger("code")==200){
            return CommonResult.success(JSONObject.parseObject(res.getString("data")));
        }else{
            return CommonResult.failed(res.getString("message"));
        }
    }
    @ApiOperation(value = "获取用户积分等信息")
    @RequestMapping("/getUserInfo/{adminId}")
    public CommonResult getUserInfo(@PathVariable Long adminId) {
        /* 获取appId 和secret*/
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMember byOpenId = memberService.getByOpenId(currentMember.getOpenId());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("integration", byOpenId.getIntegration()==null?"0":byOpenId.getIntegration().toString());
//        获取取货码
        tokenMap.put("takeCode",byOpenId.getTakeCode());
        UmsIntegrationConsumeSettingExample example =new UmsIntegrationConsumeSettingExample();
        example.or().andAdminIdEqualTo(adminId);
        List<UmsIntegrationConsumeSetting> umsIntegrationConsumeSettings = integrationConsumeSettingMapper.selectByExample(example);
        if(umsIntegrationConsumeSettings.size()>0){
            UmsIntegrationConsumeSetting integrationConsumeSetting = umsIntegrationConsumeSettings.get(0);
            tokenMap.put("imageUrl", integrationConsumeSetting.getImageUrl());
            if(integrationConsumeSetting.getCouponStatus()!=2) {
                tokenMap.put("isAllowUseIntegrationAmount","true");
            }else{
                tokenMap.put("isAllowUseIntegrationAmount","false");
            }
        }
        //        获取团购联系方式
        try {
            initContact(tokenMap,adminId);
        }catch (Exception e){
            log.error("获取团购信息异常");
        }
        return CommonResult.success(tokenMap);
    }
    @ApiOperation(value = "更新用户取货码")
    @RequestMapping("/updateTakeCode/{adminId}")
    public CommonResult updateTakeCode(@PathVariable Long adminId,String takeCode) {
        if(takeCode==null||takeCode.length()!=4){
            throw new MyException(ExceptionEnum.TAKE_CODE_ERROR);
        }
        /* 获取appId 和secret*/
        UmsMember currentMember = memberService.getCurrentMember();
        memberService.updateTakeCode(currentMember.getId(),takeCode);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "获取用户积分等信息")
    @RequestMapping("/calMoney/{adminId}")
    public CommonResult calMoney(String value,@PathVariable Long adminId) {
        /* 获取appId 和secret*/
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMember byOpenId = memberService.getByOpenId(currentMember.getOpenId());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("integration", byOpenId.getIntegration()==null?"0":byOpenId.getIntegration().toString());
        ConfirmOrderResult.CalcAmount calcAmount = orderService.calcCartAmount(new BigDecimal(value),byOpenId.getIntegration() , adminId);
        if(calcAmount.isAllowUseIntegrationAmount()) {
            tokenMap.put("useIntegration", calcAmount.getUseIntegration() + "");
            tokenMap.put("integrationAmount", calcAmount.getIntegrationAmount().toString());
            tokenMap.put("isAllowUseIntegrationAmount","true");
        }else{
            tokenMap.put("isAllowUseIntegrationAmount","false");
        }

        tokenMap.put("payAmount",calcAmount.getPayAmount().toString());
        return CommonResult.success(tokenMap);
    }
    private String getNotice(Long adminId) {
        String notice ="";
        UmsAdmin admin = umsAdminMapper.selectByPrimaryKey(adminId);
        if(admin!=null){
            if(admin.getNoticeStart()==null||admin.getNoticeOn()==null||admin.getNoticeOn()==0){
                return notice;
            }
            String[] split = admin.getNoticeStart().split(":");
            LocalDateTime  now = LocalDateTime.now();
            if(now.getHour()<Integer.valueOf(split[0])){
                return notice;
            }else if(now.getHour()==Integer.valueOf(split[0])&&now.getMinute()<Integer.valueOf(split[1])){
                return notice;
            }
            String[] splitEnd = admin.getNoticeEnd().split(":");
            if(now.getHour()>Integer.valueOf(splitEnd[0])){
                return notice;
            }else if(now.getHour()==Integer.valueOf(splitEnd[0])&&now.getMinute()>Integer.valueOf(splitEnd[1])){
                return notice;
            }
            notice=admin.getNoticeContent();
        }
        return notice;
    }

    /* 请求发起支付目录prepay */
    @RequestMapping("/prepay")
    public CommonResult prepay(@RequestParam Long orderId){
        //获取openid
        OmsOrderDetail detail = orderService.detail(orderId);

        String requestUrl = String.format(this.payUrl,
                detail.getAdminId().toString(),"商城购物",
                detail.getPayAmount().multiply(new BigDecimal(100)).intValue()+"",
                detail.getMemberUsername() ,
                orderId);//请求公共支付方法，获取前端所需要的的支付参数
        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        return CommonResult.success(sessionData);
    }
}
