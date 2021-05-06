package com.macro.mall.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.dto.CheckwxverifynicknameDto;
import com.macro.mall.dto.FastRegisterAppDto;
import com.macro.mall.dto.wx.XmlRequest;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.service.impl.ThirdPlatformWxService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Controller
@Api(tags = "ThirdPlatWXController", description = "第三方平台授权")
@RequestMapping("/wx/")
@Slf4j
public class ThirdPlatWXController {
    @Autowired
    private ThirdPlatformWxService thirdPlatformWxService;
    @Value("${wx.payUrl}")
    private String payUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private RedisService redisService;
    @PostMapping("getPreUrl")
    @ResponseBody
    public CommonResult getPrehUrl(){
        return CommonResult.success(thirdPlatformWxService.getPreAuthUrl());
    }
    @GetMapping("getPreAuthUrl")
    public String getPreAuthUrl(@RequestParam String adminId){
        return "redirect:"+thirdPlatformWxService.getPreAuthCodeUrl(adminId);
    }
//    事件通知
    @PostMapping("notify")
    @ResponseBody
    public String notify1(@RequestParam String signature, @RequestParam String timestamp,@RequestParam String nonce,@RequestParam String encrypt_type,@RequestParam String msg_signature, @RequestBody XmlRequest request){
        thirdPlatformWxService.notify(signature,timestamp,nonce,msg_signature,request);
        return "success";
    }
//    授权后
    @PostMapping("{id}/callback")
    @ResponseBody
    public String callback( @PathVariable String id,@RequestParam String signature, @RequestParam String timestamp,@RequestParam String nonce,@RequestParam String encrypt_type,@RequestParam String msg_signature, @RequestBody XmlRequest request){
        thirdPlatformWxService.callback(id,signature,timestamp,nonce,msg_signature,request);
        return "success";
    }
//    授权回调
    @GetMapping("{id}/authCallBack")
    public String authCallBack(@RequestParam String auth_code, @PathVariable Long id){
        thirdPlatformWxService.apiQueryAuth(id,auth_code);
        return "redirect:http://admin.jianjiakeji.com/tpl/authSuccess.html";
    }
//    发布代码
    @PostMapping("commitCode")
    @ResponseBody
    public CommonResult commitCode(@RequestParam String templateId){
//        String authorizer_access_token, Long adminId, String template_id, String user_version, String user_desc
        UmsAdmin umsAdmin = umsAdminService.getCurrentAdmin();
        String authorizerToken = thirdPlatformWxService.getAuthorizerToken(umsAdmin.getId());
        thirdPlatformWxService.commit(authorizerToken,umsAdmin.getId(),templateId,"1.0.0","");
        return CommonResult.success("已提交到微信进行审核！");
    }
//    代替创建小程序接口
    @PostMapping("fastregisterweapp")
    @ResponseBody
    public CommonResult fastregisterweapp(@RequestBody FastRegisterAppDto fastRegisterAppDto){
        thirdPlatformWxService.fastregisterweapp(fastRegisterAppDto);
        return CommonResult.success("申请成功，请在微信内完成实名认证！");
    }
// 上传修改商户配置
//    @PostMapping("uploadMedia")
//    @ResponseBody
//    public String uploadMedia(@RequestBody FastRegisterAppDto media){
//        String path ="/home/test.jpg";
//        thirdPlatformWxService.media("image",path);
//        return "success";
//    }
    @GetMapping("get_latest_auditstatus")
    @ResponseBody
    public CommonResult get_latest_auditstatus(){
        return CommonResult.success(thirdPlatformWxService.get_latest_auditstatus());
    }
    @GetMapping("publishAlone")
    @ResponseBody
    public CommonResult publishAlone(){
        thirdPlatformWxService.publishAlone();
        return CommonResult.success("");
    }
//   校验商户名称
    @PostMapping("checkwxverifynickname")
    @ResponseBody
    public CommonResult checkwxverifynickname(@RequestBody CheckwxverifynicknameDto checkwxverifynicknameDto){
//        thirdPlatformWxService.checkwxverifynickname(checkwxverifynicknameDto.getName());
        return CommonResult.success("");
    }
    @RequestMapping("getOpenid")
    @ResponseBody
    public CommonResult getOpenid(String appId,String code){
        return CommonResult.success(thirdPlatformWxService.getOpenId(appId,code));
    }
    @RequestMapping("getaccountbasicinfo")
    @ResponseBody
    public CommonResult<String> getaccountbasicinfo(Long adminId){
        return CommonResult.success(JSONObject.toJSONString(thirdPlatformWxService.getaccountbasicinfo(adminId)));
    }
//    网页获取用户token
    @RequestMapping("getOpenId")
    public String  getOpenId(Long adminId) throws UnsupportedEncodingException {
        String url = URLEncoder.encode("https://adminserver.jianjiakeji.com/wx/authCallBack","UTF-8") ;
        String s = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0ed7ad903bddbcd3&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state="+adminId+"#wechat_redirect";
        return "redirect:"+s;
    }
    @RequestMapping("paySuccess")
    @ResponseBody
    public CommonResult  paySuccess(String orderId) {
//        支付成功，延长该用户会员时间一年
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(Long.valueOf(orderId));
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = localDateTime.plusYears(1L);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime1.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        umsAdmin.setVipEndDate(date);
        umsAdminService.updateUmsAdmin(umsAdmin.getId(),umsAdmin);
        return CommonResult.success("支付成功");
    }
    /*
     * 微信授权回调函数
      *
      * */
    @RequestMapping("/authCallBack")
    public String authCallBack(@RequestParam String code,@RequestParam String state,HttpServletRequest request){
        try {
            //获取openid
            String openid =getOpenid(code);
            JSONObject prepay = prepay(openid, state).getJSONObject("data");
            String str  = "redirect:http://jianjiakeji.com/pay.html?appId=%s&timeStamp=%s&nonceStr=%s&package=%s&signType=%s&paySign=%s";
            String url = String.format(str,prepay.getString("appId"),prepay.getString("timeStamp"),
                    prepay.getString("nonceStr"),prepay.getString("package"),prepay.getString("signType"),prepay.getString("paySign"));
            return url;
        }catch (Exception e){
            log.error("获取openid！",e);
        }
        return "error";
    }
    /* 请求发起支付目录prepay */
    public JSONObject prepay(String openId,String state){
        try {
            //获取openid
            String requestUrl = String.format(this.payUrl,
                    "19","爱购商城年费",
                    "10",
                    openId,
                    state);//请求公共支付方法，获取前端所需要的的支付参数
            String res = restTemplate.getForObject(requestUrl, String.class);
            JSONObject sessionData = JSON.parseObject(res);
            return  sessionData;
        }catch (Exception e){
            log.error("支付异常！",e);
        }
        return null;
    }

    private String getOpenid(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0ed7ad903bddbcd3&secret=8f28385252b9fe514f5da13749392a1e&code="+code+"&grant_type=authorization_code";
        String s = restTemplate.getForObject(url, String.class);
        String openid = JSONObject.parseObject(s).getString("openid");
        return openid;
    }

    @RequestMapping("uniformSend")
    @ResponseBody
    public CommonResult uniformSend(Long adminId,String memberUsername,String code){
        thirdPlatformWxService.uniformSend(adminId,memberUsername,code);
        return CommonResult.success(null);
    }
}
