package com.macro.mall.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.portal.domain.LoginInfo;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.service.OmsPortalOrderService;
import com.macro.mall.portal.service.UmsMemberService;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
public class WXController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wx.webAccessTokenhttps}")
    private String webAccessTokenhttps;
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

        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        String openId=sessionData.getString("openid");
        if (StringUtils.isNullOrEmpty(openId)) {
            return CommonResult.failed("登录失败");
        }

        String token = memberService.loginWx(openId);
        if (token == null) {
            memberService.registerByWX(openId,sessionData.getString("unionid"));
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
                umsAdmin.getAppSecret(),
                code);//通过自定义工具类组合出小程序需要的登录凭证 code

        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        String openId=sessionData.getString("openid");
        if (StringUtils.isNullOrEmpty(openId)) {
            return CommonResult.failed("登录失败");
        }

        String token = memberService.loginWx(openId);
        if (token == null) {
            memberService.registerByWX(openId,sessionData.getString("unionid"));
            token = memberService.loginWx(openId);
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("openId", openId);
        tokenMap.put("userid", openId);

        return CommonResult.success(tokenMap);
    }
    /* 请求发起支付目录prepay */
    @RequestMapping("/prepay")
    public CommonResult prepay(@RequestParam Long orderId){
        //获取openid
        OmsOrderDetail detail = orderService.detail(orderId);

        String requestUrl = String.format(this.payUrl,
                detail.getAdminId().toString(),"商城购物",
                detail.getPayAmount().multiply(new BigDecimal(100)).intValue()+"",
                detail.getMemberUsername(),
                orderId);//请求公共支付方法，获取前端所需要的的支付参数
        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);
        return CommonResult.success(sessionData);
    }
}
