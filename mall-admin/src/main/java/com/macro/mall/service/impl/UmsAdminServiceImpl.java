package com.macro.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.macro.mall.bo.AdminUserDetails;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.Asserts;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.common.util.RequestUtil;
import com.macro.mall.dao.UmsAdminRoleRelationDao;
import com.macro.mall.dto.GetLocationSrcParam;
import com.macro.mall.dto.UmsAdminParam;
import com.macro.mall.dto.UpdateAdminPasswordParam;
import com.macro.mall.mapper.UmsAdminLoginLogMapper;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.mapper.UmsAdminRoleRelationMapper;
import com.macro.mall.model.*;
import com.macro.mall.security.util.JwtTokenUtil;
import com.macro.mall.service.UmsAdminCacheService;
import com.macro.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UmsAdminService?????????
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Value("${common.getCodeUrl}")
    private String getCodeUrl;
    @Value("${common.checkCodeUrl}")
    private String checkCodeUrl;
    @Value("${common.updateMch}")
    private String updateMch;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ThirdPlatformWxService thirdPlatformWxService;
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = adminCacheService.getAdmin(username);
        if(admin!=null) {
            return  admin;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }
    @Override
    public UmsAdmin getAdminByAppId(String appId) {
        UmsAdmin admin;
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andAppIdEqualTo(appId);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }
    @Override
    public UmsAdmin getAdminId(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }
    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
//        ?????????
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        umsAdmin.setIcon("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
        //???????????????????????????????????????
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            throw new MyException(ExceptionEnum.DOUBLE_REGISTER_ERROR);
        }
        //???????????????????????????
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }
    @Override
    public UmsAdmin registerCode(UmsAdminParam umsAdminParam) {
//        ?????????
        checkCode(umsAdminParam);
        return register(umsAdminParam);
    }
    @Override
    public void getCode(String mobile, String ip) {
//        ?????????
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("mobile",mobile);
        jsonObject.put("ip",ip);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject resData = restTemplate.postForObject(getCodeUrl, jsonObject, JSONObject.class,headers);
        String code = resData.getString("code");
        if(!"200".equals(code)){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),resData.getString("message"));
        }
    }
    private void checkCode(UmsAdminParam umsAdminParam) {
        if(umsAdminParam.getCode().length()<4){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),"????????????????????????4??????");
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> httpEntity =new HttpEntity<>(JSONObject.toJSONString(umsAdminParam).replace("username","mobile"),headers);
        JSONObject resData = restTemplate.postForObject(checkCodeUrl, httpEntity, JSONObject.class);
        String code = resData.getString("code");
        if(!"200".equals(code)){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),resData.getString("message"));
        }
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //????????????????????????????????????
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("???????????????");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("??????????????????");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("????????????:{}", e.getMessage());
        }
        return token;
    }

    /**
     * ??????????????????
     * @param username ?????????
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null){
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLogMapper.insert(loginLog);
    }

    /**
     * ?????????????????????????????????
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record, example);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        return adminMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
//        if(rawAdmin.getPassword().equals(admin.getPassword())){
//            //??????????????????????????????????????????
//            admin.setPassword(null);
//        }else{
//            //?????????????????????????????????????????????
//            if(StrUtil.isEmpty(admin.getPassword())){
//                admin.setPassword(null);
//            }else{
//                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
//            }
//        }
//        rawAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        rawAdmin.setAppId(admin.getAppId());
        rawAdmin.setAppSecret(admin.getAppSecret());
        int count = adminMapper.updateByPrimaryKeySelective(rawAdmin);
        adminCacheService.delAdmin(id);
        return count;
    }
    @Override
    public int updateUmsAdmin(Long id, UmsAdmin admin) {
        admin.setId(id);
        int count = adminMapper.updateByPrimaryKeySelective(admin);
        adminCacheService.delAdmin(id);
        return count;
    }
    @Override
    public int updateWithMch(Long id, UmsAdmin admin) {
//        UmsAdmin admin = new UmsAdmin();
//        BeanUtils.copyProperties(admin,admin);
//        if(id==null||id==0){
            id=this.getCurrentAdmin().getId();
//        }
//?????? all-in-one ????????????
        updateRemote(id, admin);
//        ???????????????
        admin.setId(id);
        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
        rawAdmin.setAppId(admin.getAppId());
        rawAdmin.setAppSecret(admin.getAppSecret());
        rawAdmin.setMchId(admin.getMchId());
        rawAdmin.setMchKey(admin.getMchKey());
        int count = adminMapper.updateByPrimaryKeySelective(rawAdmin);
        adminCacheService.delAdmin(id);
        return count;
    }

    private void updateRemote(Long id, UmsAdmin admin) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("appId",admin.getAppId());
        jsonObject.put("mchId",admin.getMchId());
        jsonObject.put("mchKey",admin.getMchKey());
        jsonObject.put("bizId",id);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject resData = restTemplate.postForObject(updateMch, jsonObject, JSONObject.class,headers);
        String code = resData.getString("code");
        if(!"200".equals(code)){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),resData.getString("message"));
        }
    }

    @Override
    public int updatePassword(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
        rawAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int count = adminMapper.updateByPrimaryKeySelective(rawAdmin);
        adminCacheService.delAdmin(id);
        return count;
    }
    @Override
    public int updateAuthorizerRefreshToken(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
        rawAdmin.setAppId(admin.getAppId());
        rawAdmin.setAuthorizerRefreshToken(admin.getAuthorizerRefreshToken());
        int count = adminMapper.updateByPrimaryKeySelective(rawAdmin);
        this.updateRemote(id,rawAdmin);
        adminCacheService.delAdmin(id);
        return count;
    }
    @Override
    public int delete(Long id) {
        adminCacheService.delAdmin(id);
        int count = adminMapper.deleteByPrimaryKey(id);
        adminCacheService.delResourceList(id);
        return count;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //????????????????????????
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //???????????????
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationDao.insertList(list);
        }
        adminCacheService.delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminMapper.updateByPrimaryKey(umsAdmin);
        adminCacheService.delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //??????????????????
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("????????????????????????");
    }
    @Override
    public UmsAdmin getCurrentAdmin() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails details = (AdminUserDetails) auth.getPrincipal();
        return details.getUmsAdmin();
    }
    @Override
    public void updateNotice(UmsAdmin umsAdmin) {
        UmsAdmin umsAdminCurrent = this.getCurrentAdmin();
        UmsAdmin umsAdminNew = new UmsAdmin();
        umsAdminNew.setId(umsAdminCurrent.getId());
        umsAdminNew.setNoticeOn(umsAdmin.getNoticeOn());
        umsAdminNew.setNoticeContent(umsAdmin.getNoticeContent());
        umsAdminNew.setNoticeType(umsAdmin.getNoticeType());
        umsAdminNew.setNoticeStart(umsAdmin.getNoticeStart());
        umsAdminNew.setNoticeEnd(umsAdmin.getNoticeEnd());
        adminMapper.updateByPrimaryKeySelective(umsAdminNew);
    }
   @Override
    public void updateConcat(UmsAdmin umsAdmin) {
        UmsAdmin umsAdminCurrent = this.getCurrentAdmin();
        UmsAdmin umsAdminNew = new UmsAdmin();
        umsAdminNew.setId(umsAdminCurrent.getId());
        umsAdminNew.setContactMobile(umsAdmin.getContactMobile());
        umsAdminNew.setContactAddress(umsAdmin.getContactAddress());
       umsAdminNew.setSupportDelivery(umsAdmin.getSupportDelivery());
       umsAdminNew.setFreightAmount(umsAdmin.getFreightAmount());
        adminMapper.updateByPrimaryKeySelective(umsAdminNew);
    }

    @Override
    public String getLocationSrc(@RequestBody  GetLocationSrcParam param) {
        String path;
        if(param!=null&&param.getLocation()!=null&&param.getLocation().length()>0){
            String url1 ="pages/index/index?q={addr:PARAMS}";
            path = url1.replace("PARAMS", URLEncoder.encode(param.getLocation()));
        }else{
            path = "pages/index/index";
            UmsAdmin currentAdmin = getCurrentAdmin();
            if(currentAdmin.getWxacodeUrl()!=null&&currentAdmin.getWxacodeUrl().length()>0){
                return currentAdmin.getWxacodeUrl();
            }else{
                String getwxacode = thirdPlatformWxService.getwxacode(path);
                UmsAdmin umsAdmin = new UmsAdmin();
                umsAdmin.setWxacodeUrl(getwxacode);
                this.updateUmsAdmin(currentAdmin.getId(),umsAdmin);
                return getwxacode;
            }
        }
        return thirdPlatformWxService.getwxacode(path);
    }
    @Override
    public String getPaySrc() {
        String path = "pages/ucenter/pay/pay";
        UmsAdmin currentAdmin = getCurrentAdmin();
        if(currentAdmin.getWxacodePayUrl()!=null&&currentAdmin.getWxacodePayUrl().length()>0){
            return currentAdmin.getWxacodePayUrl();
        }else{
            String getwxacode = thirdPlatformWxService.getwxacode(path);
            UmsAdmin umsAdmin = new UmsAdmin();
            umsAdmin.setWxacodePayUrl(getwxacode);
            this.updateUmsAdmin(currentAdmin.getId(),umsAdmin);
            return getwxacode;
        }
    }
}
