package com.macro.mall.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.common.util.aes.WXBizMsgCrypt;
import com.macro.mall.dto.FastRegisterAppDto;
import com.macro.mall.dto.wx.XmlRequest;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.OssService;
import com.macro.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ThirdPlatformWxService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private OssService ossService;
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
    public String getPreAuthUrl(){
//        若用户拥有授权二维码，则不必授权
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        Integer status = currentAdmin.getPublishStatus();
        if(status!=null&&status==1){
            log.info("已授权过，status[{}]",status);
            return "exist";
        }
        return String.format("https://adminserver.jianjiakeji.com/wx/getPreAuthUrl?adminId=%s",currentAdmin.getId());
    }
    public String getPreAuthCodeUrl(String adminId){
//        若用户拥有授权二维码，则不必授权
//        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
//        String authorizerRefreshToken = currentAdmin.getAuthorizerRefreshToken();
//        if(authorizerRefreshToken!=null&&authorizerRefreshToken.length()>0){
//            log.info("已授权过，authorizerRefreshToken[{}]",authorizerRefreshToken);
//            return "exist";
//        }
        String preAuthCode = getPreAuthCode();
        String callBackUrl =String.format("https://adminserver.jianjiakeji.com/wx/%s/authCallBack",adminId);
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
           throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
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
        log.info("getApiComponentTokenUrl req [{}]",jsonObject);
        JSONObject jsonObject1 = restTemplate.postForObject(getApiComponentTokenUrl, jsonObject, JSONObject.class);
        log.info("getApiComponentTokenUrl res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("component_access_token")!=null){
            COMPONENT_ACCESS_TOKEN =jsonObject1.getString("component_access_token");
            return COMPONENT_ACCESS_TOKEN;
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }
    private String getTicket(){
        return  String.valueOf(redisService.get(TICKET));

    }

    public void notify( String signature,  String timestamp,  String nonce,  String msg_signature,XmlRequest request) {
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
            if(nodelist2!=null&&nodelist2.getLength()>0){
                String textContent = nodelist2.item(0).getTextContent();
                redisService.set(TICKET,textContent);
                log.info("刷新token值[{}]",textContent);
            }
            NodeList nodelist3 = root.getElementsByTagName("appid");
            if(nodelist3!=null&&nodelist3.getLength()>0){
                String appid = nodelist3.item(0).getTextContent();
//               修改appid

            }
        } catch (Exception e) {
            log.error("微信回调异常",e);
            throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }
    public String callback( String appId ,String signature,  String timestamp,  String nonce,  String msg_signature,XmlRequest request) {
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
            NodeList nodelist2 = root.getElementsByTagName("Event");
            String textContent = nodelist2.item(0).getTextContent();
            log.info("Event[{}]",textContent);
            if(textContent.contains("weapp_audit_success")){
//                需要发布代码
//                根据appId找到adminId
                UmsAdmin adminByAppId = umsAdminService.getAdminByAppId(appId);
                release(adminByAppId.getId());
            }
            return "success";
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.WX_ERROR);
        }
    }

    private void release(Long id) {
        UmsAdmin currentAdmin = umsAdminService.getAdminId(id);
        if(currentAdmin==null||currentAdmin.getPublishStatus()==null||currentAdmin.getPublishStatus()==0){
            log.info("代码审核通过，开始发布代码");
            String authorizerToken = getAuthorizerToken(id);
            //        发布代码
            JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/wxa/release?access_token="+authorizerToken,new JSONObject(), JSONObject.class);
            log.info("release res [{}]",jsonObject1);
            if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&jsonObject1.getString("errcode").equals("0")){
                log.info("发布成功！");
//                会员默认给一个月
                if(currentAdmin.getVipEndDate()==null){
                    Calendar calendar  = Calendar.getInstance();
                    calendar.add(Calendar.MONTH,+1);
                    currentAdmin.setVipEndDate(calendar.getTime());
                    umsAdminService.updateUmsAdmin(id,currentAdmin);
                }
            }else{
                throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
            }
            UmsAdmin umsAdmin = new UmsAdmin();
            umsAdmin.setPublishStatus(1);
            umsAdminService.updateUmsAdmin(id,umsAdmin);
        }else{
            log.info("已发布，想二次发布去改库！");
        }

    }

    public void apiQueryAuth(Long id ,String auth_code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("component_appid",componentAppid);
        jsonObject.put("component_access_token",this.getAccessToken());
        jsonObject.put("authorization_code",auth_code);
        JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="+this.getAccessToken(), jsonObject, JSONObject.class);
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
//            TODO 配置环境
            modifyDomain(authorizer_access_token);
//            提交代码审查准备发布
//            publish(authorizer_access_token,id);
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }
    public void publishAlone(){
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        publish(this.getAuthorizerToken(currentAdmin.getId()),currentAdmin.getId());
    }
    private void publish(String authorizer_access_token,Long adminId) {

//        1.获取模板ID
        JSONObject jsonObject1 = restTemplate.getForObject("https://api.weixin.qq.com/wxa/gettemplatelist?access_token="+this.getAccessToken(), JSONObject.class);
        log.info("gettemplatelist res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&jsonObject1.getString("errcode").equals("0")){
            JSONArray template_list = jsonObject1.getJSONArray("template_list");
            LinkedHashMap jsonObject = (LinkedHashMap)template_list.get(0);
            String template_id = jsonObject.get("template_id").toString();
            String user_version = jsonObject.get("user_version").toString();
            String user_desc = jsonObject.get("user_desc").toString();
            //        2.上传小程序代码
            commit(authorizer_access_token, adminId, template_id, user_version, user_desc);
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }

    }

    /**
     *
     * @param authorizer_access_token
     * @param adminId
     * @param template_id
     * @param user_version
     * @param user_desc
     */
    public void commit(String authorizer_access_token, Long adminId, String template_id, String user_version, String user_desc) {
        JSONObject jsonObject21 = new JSONObject();
        jsonObject21.put("template_id",template_id);
        jsonObject21.put("user_version",user_version);
        jsonObject21.put("user_desc",user_desc);
        UmsAdmin item = umsAdminService.getItem(adminId);
        jsonObject21.put("ext_json","{\"extEnable\": true,\"ext\":{\"adminId\":"+adminId+"},\"extAppid\":\""+item.getId()+"\"}");

        JSONObject jsonObject22 = restTemplate.postForObject("https://api.weixin.qq.com/wxa/commit?access_token="+authorizer_access_token, jsonObject21, JSONObject.class);
        log.info("commit res [{}]",jsonObject22);
        if(jsonObject22!=null&&jsonObject22.getString("errcode")!=null&&jsonObject22.getString("errcode").equals("0")){
            //3.审核
            audit(authorizer_access_token);
        }else{
            throw new MyException(Integer.valueOf(jsonObject22.getString("errcode")),jsonObject22.getString("errmsg"));
        }
    }

    private void audit(String authorizer_access_token) {
//        GET https://api.weixin.qq.com/cgi-bin/wxopen/getcategory?access_token=ACCESS_TOKEN
//      categories
        JSONObject forObject = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/wxopen/getcategory?access_token=" + authorizer_access_token, JSONObject.class);
        log.info("getcategory res [{}]",forObject);
        JSONArray jsonArray = forObject.getJSONArray("categories");

        JSONArray request = new JSONArray();
        jsonArray.forEach(item->{
            LinkedHashMap hashMap = (LinkedHashMap)item;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_id",hashMap.get("first"));
            jsonObject.put("first_class",hashMap.get("first_name"));
            jsonObject.put("second_id",hashMap.get("second"));
            jsonObject.put("second_class",hashMap.get("second_name"));
            request.add(jsonObject);
        });
        //       获取 审核项列表（选填，至多填写 5 项）
        JSONObject jsonObject31 = new JSONObject();
        jsonObject31.put("item_list",request);
        JSONObject jsonObject32 = restTemplate.postForObject("https://api.weixin.qq.com/wxa/submit_audit?access_token="+authorizer_access_token, jsonObject31, JSONObject.class);
        log.info("submit_audit res [{}]",jsonObject32);
        if(jsonObject32!=null&&jsonObject32.getString("errcode")!=null&&jsonObject32.getString("errcode").equals("0")){

        }else{
            throw new MyException(Integer.valueOf(jsonObject32.getString("errcode")),jsonObject32.getString("errmsg"));
        }
    }

    private void modifyDomain(String authorizer_access_token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","set");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("https://portal.jianjiakeji.com");
        jsonArray.add("https://szjjkj.oss-cn-beijing.aliyuncs.com");
        jsonObject.put("requestdomain",jsonArray);
//        jsonObject.put("wsrequestdomain",jsonArray);
        jsonObject.put("uploaddomain",jsonArray);
        jsonObject.put("downloaddomain",jsonArray);
        JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/wxa/modify_domain?access_token="+authorizer_access_token, jsonObject, JSONObject.class);
        log.info(" modify_domain res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&(jsonObject1.getString("errcode").equals("0")||jsonObject1.getString("errcode").equals("85017"))){
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }

    public String getAuthorizerToken(Long id){
        Object s = redisService.get("REDIS:authorizer_access_token" + id);
        log.info("redis authorizer_access_token [{}]",s);
        if(s==null){
//            刷新token
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("component_appid",componentAppid);
            UmsAdmin item = umsAdminService.getItem(id);
            jsonObject.put("authorizer_appid",item.getAppId());
            jsonObject.put("authorizer_refresh_token",item.getAuthorizerRefreshToken());
            JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="+this.getAccessToken(), jsonObject, JSONObject.class);
            log.info("api_authorizer_token res [{}]",jsonObject1);
            if(jsonObject1!=null&&jsonObject1.getString("authorizer_access_token")!=null){
                String authorizer_access_token = jsonObject1.getString("authorizer_access_token");
                //            有效期两小时
                redisService.set("REDIS:authorizer_access_token"+id,authorizer_access_token,7200);
                return authorizer_access_token;
            }else{
                throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
            }
        }else{
            return (String) s;
        }
    }

    public void fastregisterweapp(FastRegisterAppDto fastRegisterAppDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",fastRegisterAppDto.getName());
        jsonObject.put("code",fastRegisterAppDto.getCode());
        jsonObject.put("code_type",fastRegisterAppDto.getCode_type());
        jsonObject.put("legal_persona_wechat",fastRegisterAppDto.getLegal_persona_wechat());
        jsonObject.put("legal_persona_name",fastRegisterAppDto.getLegal_persona_name());
        jsonObject.put("component_phone","13311493480");
        JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=create&component_access_token="+this.getAccessToken(), jsonObject, JSONObject.class);
        log.info("fastregisterweapp res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&(jsonObject1.getString("errcode").equals("0")||jsonObject1.getString("errcode").equals("89249"))){
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }

    }
//    获取媒体文件
    public String  media(String type,  String path){
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        Long id =currentAdmin.getId();
        Object s = redisService.get("REDIS:media"+type + id);
        log.info("redis media [{}]",s);
        if(s==null){
            //设置头信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            //构造表单参数
            MultiValueMap<String, Object> params= new LinkedMultiValueMap<>();
            FileSystemResource fileResource = new FileSystemResource(new File(path));
            params.add("media",fileResource);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(String.format("https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s",this.getAuthorizerToken(id),type), requestEntity, String.class);
            JSONObject body = JSONObject.parseObject(responseEntity.getBody());
            log.info("media/upload res [{}]",body);
            String media_id = body.getString("media_id");
            if(media_id!=null&& media_id.length()>0){
                redisService.set("REDIS:media"+type + id,media_id,24*3600);
                return media_id;
            }else{
                throw new MyException(Integer.valueOf(body.getString("errcode")),body.getString("errmsg"));
            }
        }else{
            return (String) s;
        }
    }
//    查询小程序授权状态
    public JSONObject get_latest_auditstatus(){
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        JSONObject jsonObject1 = restTemplate.getForObject("https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token="+this.getAuthorizerToken(currentAdmin.getId()), JSONObject.class);
        log.info("get_latest_auditstatus res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&jsonObject1.getString("errcode").equals("0")){
//            状态值	说明
//            0	审核成功
//            1	审核被拒绝
//            2	审核中
//            3	已撤回
//            4	审核延后
            Integer status = jsonObject1.getInteger("status");
            if(status==0){
//                查询是否已发布
                release(currentAdmin.getId());
//                查询会员到期日期 要到期展示付费码
                Calendar calendar  = Calendar.getInstance();
                if(currentAdmin.getVipEndDate()==null){
                    jsonObject1.put("expire",true);
                    jsonObject1.put("expireDate",format(Calendar.getInstance().getTime()));
                    jsonObject1.put("linkVip","http://adminserver.jianjiakeji.com/wx/getOpenId?adminId="+currentAdmin.getId());
                    return jsonObject1;
                }
                calendar.setTime(currentAdmin.getVipEndDate());
                calendar.add(Calendar.MONTH,-1);
                if(calendar.before(Calendar.getInstance())){
//
                    jsonObject1.put("expire",true);
                    jsonObject1.put("expireDate",format(currentAdmin.getVipEndDate()));
                    jsonObject1.put("linkVip","http://adminserver.jianjiakeji.com/wx/getOpenId?adminId="+currentAdmin.getId());
                }
            }
            return jsonObject1;
        }else{
            throw new MyException(ExceptionEnum.PROGRAM_NOT_PUBLISH);
        }
    }
    //要在高并发环境下能有比较好的体验，可以使用ThreadLocal来限制SimpleDateFormat只能在线程内共享，这样就避免了多线程导致的线程安全问题。
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }
//    public String checkwxverifynickname(String nick_name ){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("nick_name",nick_name);
//        JSONObject jsonObject1 = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/wxverify/checkwxverifynickname?access_token="+this.getAccessToken(), jsonObject, JSONObject.class);
//        log.info("checkwxverifynickname res [{}]",jsonObject1);
//        if(jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&(jsonObject1.getString("errcode").equals("0")||jsonObject1.getString("errcode").equals("89249"))){
//        }else{
//            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
//        }
//    }
    public String getOpenId(String appId,String code){
        String format = String.format("https://api.weixin.qq.com/sns/component/jscode2session?appid=%s&js_code=%s&grant_type=authorization_code&component_appid=%s&component_access_token=%s", appId, code, componentAppid, this.getAccessToken());
        String s = restTemplate.getForObject(format, String.class);
        JSONObject jsonObject1 = JSONObject.parseObject(s);
        log.info("jscode2session res [{}]",jsonObject1);
        if(jsonObject1!=null&&jsonObject1.getString("openid")!=null){
            return jsonObject1.getString("openid");
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }

    public String getwxacode(String path){
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        String format = String.format("https://api.weixin.qq.com/wxa/getwxacode?access_token=%s", this.getAuthorizerToken(currentAdmin.getId()));
        restTemplate.getMessageConverters().add(new BufferedImageHttpMessageConverter());
        Map<String,Object> map =new HashMap<>();
        map.put("path",path);
        ResponseEntity<BufferedImage> jsonObject1 = restTemplate.postForEntity(format,map, BufferedImage.class);
        log.info("getwxacode res [{}]",jsonObject1);
        BufferedImage body = jsonObject1.getBody();
        File file = new File("/home/"+ UUID.randomUUID().toString()+".jpg");
        try {
            ImageIO.write(body,"jpg",file);
//            file 上传至阿里云
            String s = ossService.uploadObject2OSS(file);
            return s;
        }catch (Exception e){
            log.error("下载二维码异常！",e);
            throw new MyException(ExceptionEnum.WX_ERROR.getCode(),body.toString());
        }finally {
            file.delete();
        }
    }

    public JSONObject getaccountbasicinfo(Long adminId){
        UmsAdmin currentAdmin = umsAdminService.getItem(adminId);
        String jsonObject = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/account/getaccountbasicinfo?access_token=" + this.getAuthorizerToken(currentAdmin.getId()), String.class);
        log.info("getaccountbasicinfo res [{}]",jsonObject);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        if (jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&jsonObject1.getString("errcode").equals("0")){
            return jsonObject1;
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }

    public void uniformSend(Long adminId, String memberUsername, String code) {
        UmsAdmin currentAdmin = umsAdminService.getItem(adminId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("touser",memberUsername);
        JSONObject msg = new JSONObject();
        msg.put("template_id",currentAdmin.getWxTemplateId());
        msg.put("page","pages/ucenter/order/order");
        msg.put("form_id",4037);
        JSONObject data = new JSONObject();
//        取货地点
        data.put("thing7",currentAdmin.getContactAddress()+" "+ currentAdmin.getContactMobile());
//        提货码
        data.put("character_string8",code);
        msg.put("data",data);
//      放大关键字
        msg.put("emphasis_keyword","character_string8.DATA");
        jsonObj.put("weapp_template_msg",msg);
//      发送
        log.info("uniform_send request [{}]",jsonObj);
        String jsonObject = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + this.getAuthorizerToken(currentAdmin.getId()),jsonObj, String.class);
        log.info("uniform_send res [{}]",jsonObject);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        if (jsonObject1!=null&&jsonObject1.getString("errcode")!=null&&jsonObject1.getString("errcode").equals("0")){
            log.info("消息模板发送成功[{}]",jsonObj);
        }else{
            throw new MyException(Integer.valueOf(jsonObject1.getString("errcode")),jsonObject1.getString("errmsg"));
        }
    }
}
