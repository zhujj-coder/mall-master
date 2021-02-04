package com.macro.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.common.util.aes.WXBizMsgCrypt;
import com.macro.mall.dto.wx.XmlRequest;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
@Slf4j
@Service
public class ThirdPlatformWxService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    private UmsAdminService umsAdminService;

    @Value("${wx.pre_auth_code_url}")
    private String getPreAuthCodeUrl;
    @Value("${wx.api_component_token_url}")
    private String getApiComponentTokenUrl;
    @Value("${wx.component_appid}")
    private String componentAppid;
    @Value("${wx.component_appsecret}")
    private String component_appsecret;
    @Value("${wx.component_encodingAesKey}")
    private String component_encodingAesKey;
    @Value("${wx.component_token}")
    private String component_token;


    String COMPONENT_ACCESS_TOKEN;
    static String TICKET="WX_TICKET";
    public String getPreAuthCodeUrl(){
//        若用户拥有授权二维码，则不必授权
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        String authorizerRefreshToken = currentAdmin.getAuthorizerRefreshToken();
        if(authorizerRefreshToken!=null&&authorizerRefreshToken.length()>0){
            log.info("已授权过，authorizerRefreshToken[{}]",authorizerRefreshToken);
            return "exist";
        }
        String preAuthCode = getPreAuthCode();
        String callBackUrl =String.format("adminserver.jianjiakeji.com/wx/%s/authCallBack",currentAdmin.getId());
        return String.format("https://mp.weixin.qq.com/safe/bindcomponent?action=bindcomponent&no_scan=1&component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=2#wechat_redirect",componentAppid,preAuthCode,callBackUrl);
    }
    private String getPreAuthCode(){
        String component_access_token = getPreAuthCodeUrl.replace("COMPONENT_ACCESS_TOKEN", this.getAccessToken());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid",componentAppid);
        JSONObject jsonObject1 = restTemplate.postForObject(component_access_token, jsonObject, JSONObject.class);
        log.info("component_access_token res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("pre_auth_code")!=null){
            return jsonObject1.getString("pre_auth_code");
        }else{
           throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }

    public String getAccessToken() {
        if(COMPONENT_ACCESS_TOKEN!=null){
            return COMPONENT_ACCESS_TOKEN;
        }else{
            return this.refreshNewAccessToken();
        }
    }

    public String refreshNewAccessToken() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid",componentAppid);
        jsonObject.put("component_appsecret",component_appsecret);
        jsonObject.put("component_verify_ticket",getTicket());
        JSONObject jsonObject1 = restTemplate.postForObject(getApiComponentTokenUrl, jsonObject, JSONObject.class);
        log.info("getApiComponentTokenUrl res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("component_access_token")!=null){
            COMPONENT_ACCESS_TOKEN =jsonObject1.getString("component_access_token");
            return COMPONENT_ACCESS_TOKEN;
        }else{
            throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }
    private String getTicket(){
        return  String.valueOf(redisService.get(TICKET));

    }

    public String getNewTicket( String signature,  String timestamp,  String nonce,  String msg_signature,XmlRequest request) {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(component_token, component_encodingAesKey, componentAppid);
//            	 * @param msgSignature 签名串，对应URL参数的msg_signature
//                    * @param timeStamp 时间戳，对应URL参数的timestamp
//                    * @param nonce 随机串，对应URL参数的nonce
//                    * @param postData 密文，对应POST请求的数据
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            String s = pc.decryptMsg(msg_signature, timestamp, nonce, request.getEncrypt());
            StringReader sr = new StringReader(s);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist2 = root.getElementsByTagName("ComponentVerifyTicket");
            String textContent = nodelist2.item(0).getTextContent();
            redisService.set(TICKET,textContent);
            log.info("刷新token值[{}]",textContent);
            return textContent;
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }

    public void apiQueryAuth(Long id ,String auth_code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid",componentAppid);
        jsonObject.put("component_access_token",COMPONENT_ACCESS_TOKEN);
        jsonObject.put("authorization_code",auth_code);
        JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="+COMPONENT_ACCESS_TOKEN, jsonObject, JSONObject.class);
        log.info("apiQueryAuth res [{}]",jsonObject1);
        JSONObject authorization_info = jsonObject1.getJSONObject("authorization_info");
        if(authorization_info!=null&&authorization_info.getString("authorizer_appid")!=null){
            String authorizer_refresh_token = authorization_info.getString("authorizer_refresh_token");
            String authorizer_access_token = authorization_info.getString("authorizer_access_token");
            String authorizer_appid = authorization_info.getString("authorizer_appid");
            UmsAdmin admin = new UmsAdmin();
            admin.setAuthorizerRefreshToken(authorizer_refresh_token);
            admin.setAppId(authorizer_appid);
            umsAdminService.updateAuthorizerRefreshToken(id,admin);
//            有效期两小时
            redisService.set("REDIS:authorizer_access_token"+id,authorizer_access_token,7200);
        }else{
            throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }
    public String getAuthorizerRefreshToken(Long id){
        String s = redisService.get("REDIS:authorizer_access_token" + id).toString();
        log.info("redis authorizer_access_token [{}]",s);
        if(s==null||s.length()<=0){
//            刷新token
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("component_appid",componentAppid);
            UmsAdmin item = umsAdminService.getItem(id);
            jsonObject.put("authorizer_appid",item.getAppId());
            jsonObject.put("authorizer_refresh_token",item.getAuthorizerRefreshToken());
            JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="+COMPONENT_ACCESS_TOKEN, jsonObject, JSONObject.class);
            log.info("api_authorizer_token res [{}]",jsonObject1);
            if(jsonObject1!=null&&jsonObject1.getString("authorizer_access_token")!=null){
                String authorizer_access_token = jsonObject1.getString("authorizer_access_token");
                //            有效期两小时
                redisService.set("REDIS:authorizer_access_token"+id,authorizer_access_token,7200);
                return authorizer_access_token;
            }else{
                throw new MyException(ExceptionEnum.WX_ERROR);
            }
        }else{
            return s;
        }
    }
}
