package com.macro.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.util.DateUtil;
import com.macro.mall.common.util.RequestUtil;
import com.macro.mall.dao.UmsMemberDao;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.UmsIntegrationConsumeSettingMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private OmsOrderService orderService;
    @Autowired
    private PmsProductService productService;
    @Autowired
    private UmsMemberDao umsMemberDao;
    @Autowired
    private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;
    @Autowired
    private PmsProductCategoryService productCategoryService;
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        //      插入积分抵扣表
        UmsIntegrationConsumeSetting record =new UmsIntegrationConsumeSetting();
        record.setAdminId(umsAdmin.getId());
        record.setUseUnit(100);
        record.setCouponStatus(1);
        record.setDeductionPerAmount(10);
        record.setMaxPercentPerOrder(50);
        integrationConsumeSettingMapper.insert(record);
//        默认分配商品管理员角色（roleIds:1）
        List<Long> list  = new ArrayList<>();
        list.add(1L);
        adminService.updateRole(umsAdmin.getId(),list);
//        默认添加一条商品分类和商品
        addProduct(umsAdmin);
        return CommonResult.success(umsAdmin);

    }

    private void addProduct(UmsAdmin umsAdmin) {
        PmsProductCategoryParam productCategoryParam = new PmsProductCategoryParam();
        productCategoryParam.setName("纸巾");
        productCategoryParam.setNavStatus(1);
        productCategoryParam.setParentId(0L);
        productCategoryParam.setShowStatus(0);
        productCategoryParam.setSort(0);
        productCategoryParam.setAdminId(umsAdmin.getId());
        productCategoryService.create(productCategoryParam);
        List<PmsProductCategory> list1 = productCategoryService.getList(0L, 1, 1,umsAdmin.getId());
        PmsProductParam productParam  =new PmsProductParam();
        productParam.setAdminId(umsAdmin.getId());
        productParam.setAlbumPics("http://szjjkj.oss-cn-beijing.aliyuncs.com/mall/images/20210319/fa36ed92005d967f.jpg");
        List<String> listPic = new ArrayList<>();
        listPic.add("http://szjjkj.oss-cn-beijing.aliyuncs.com/mall/images/20210319/fa36ed92005d967f.jpg");
        productParam.setAlbumPicsList(listPic);
        productParam.setPic("http://szjjkj.oss-cn-beijing.aliyuncs.com/mall/images/20210319/fa36ed92005d967f.jpg");
        productParam.setDeleteStatus(0);
        productParam.setName("餐巾纸");
        productParam.setOriginalPrice(new BigDecimal(10));
        productParam.setPrice(new BigDecimal(6));
        productParam.setPublishStatus(1);
        productParam.setNewStatus(1);
        productParam.setSort(0);
        productParam.setProductSn("0");
        productParam.setSubTitle("开心朵朵 本色抽纸50包4层加厚纸巾抽纸整箱家用实惠装纸抽餐巾纸擦手纸卫生纸 开心熊10包");
        productParam.setProductCategoryId(list1.get(0).getId());
        productService.create(productParam);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/registerCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> registerCode(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.registerCode(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
//      插入积分抵扣表
        UmsIntegrationConsumeSetting record =new UmsIntegrationConsumeSetting();
        record.setAdminId(umsAdmin.getId());
        record.setUseUnit(100);
        record.setCouponStatus(1);
        record.setDeductionPerAmount(10);
        record.setMaxPercentPerOrder(50);
        integrationConsumeSettingMapper.insert(record);
//        默认分配商品管理员角色（roleIds:1）
        List<Long> list  = new ArrayList<>();
        list.add(1L);
        adminService.updateRole(umsAdmin.getId(),list);
        //        默认添加一条商品分类和商品
        addProduct(umsAdmin);
        return CommonResult.success(umsAdmin);

    }
    @ApiOperation(value = "用户获取注册验证码")
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getCode( @RequestBody Map<String,String> map,HttpServletRequest request) {
        String ip = RequestUtil.getRequestIp(request);
        adminService.getCode(map.get("mobile"),ip);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        data.put("admin_id", umsAdmin.getId());
//        公告
        data.put("noticeContent", umsAdmin.getNoticeContent());
        data.put("noticeType", umsAdmin.getNoticeType());
        data.put("noticeStart", umsAdmin.getNoticeStart());
        data.put("noticeEnd", umsAdmin.getNoticeEnd());
        data.put("noticeOn", umsAdmin.getNoticeOn());
//        取货联系地址
        data.put("contactMobile", umsAdmin.getContactMobile());
        data.put("contactAddress", umsAdmin.getContactAddress());
        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/getNotice")
    @ResponseBody
    public CommonResult getNotice() {
        UmsAdmin umsAdmin0 = adminService.getCurrentAdmin();

        UmsAdmin umsAdmin = adminService.getItem(umsAdmin0.getId());
        Map<String, Object> data = new HashMap<>();
//        公告
        data.put("id", umsAdmin.getId());
        data.put("noticeContent", umsAdmin.getNoticeContent());
        data.put("noticeType", umsAdmin.getNoticeType());
        data.put("noticeStart", umsAdmin.getNoticeStart());
        data.put("noticeEnd", umsAdmin.getNoticeEnd());
        data.put("noticeOn", umsAdmin.getNoticeOn());
//       微信相关
        data.put("appId", umsAdmin.getAppId());
        data.put("appSecret", umsAdmin.getAppSecret());
        data.put("mchId", umsAdmin.getMchId());
        data.put("mchKey", umsAdmin.getMchKey());
//        取货联系地址
        data.put("contactMobile", umsAdmin.getContactMobile());
        data.put("contactAddress", umsAdmin.getContactAddress());
        data.put("freightAmount", umsAdmin.getFreightAmount());
        data.put("supportDelivery", umsAdmin.getSupportDelivery());
//        积分相关
        UmsIntegrationConsumeSettingExample example =new UmsIntegrationConsumeSettingExample();
        example.or().andAdminIdEqualTo(umsAdmin.getId());
        List<UmsIntegrationConsumeSetting> umsIntegrationConsumeSettings = integrationConsumeSettingMapper.selectByExample(example);
        if(umsIntegrationConsumeSettings!=null&&umsIntegrationConsumeSettings.size()>0){
            data.put("deductionPerAmount",umsIntegrationConsumeSettings.get(0).getDeductionPerAmount());
            data.put("maxPercentPerOrder",umsIntegrationConsumeSettings.get(0).getMaxPercentPerOrder());
            data.put("couponStatus",umsIntegrationConsumeSettings.get(0).getCouponStatus());
        }
        return CommonResult.success(data);
    }

    @RequestMapping(value = "/staticInfo", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult staticInfo() {
        Map<String, Object> data = new HashMap<>();
//        订单信息 待处理事务 商品总览 用户总览
        data.put("todaySummary",orderService.getOrderSummary(DateUtil.getToadyMin(), LocalDateTime.now()));
        data.put("yesterdaySummary",orderService.getOrderSummary(DateUtil.getYesterdayMin(),DateUtil.getToadyMin()));
        data.put("stateCount",orderService.getOrderStatusCount());
        data.put("publishStatus",productService.getPublishStatus());
        data.put("memberCount",umsMemberDao.getMemberCount(adminService.getCurrentAdmin().getId()));
        return CommonResult.success(data);
    }
    @RequestMapping(value = "/updateNotice", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNotice(@RequestBody UmsAdmin umsAdmin) {
        adminService.updateNotice(umsAdmin);
        return CommonResult.success(null);
    }
    @RequestMapping(value = "/updateConcat", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateConcat(@RequestBody UmsAdmin umsAdmin) {
        adminService.updateConcat(umsAdmin);
        return CommonResult.success(null);
    }
    @RequestMapping(value = "/updateIntegration", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateIntegration(@RequestBody UmsIntegrationConsumeSetting record) {
        UmsAdmin umsAdmin = adminService.getCurrentAdmin();
        UmsIntegrationConsumeSetting recordNew = new UmsIntegrationConsumeSetting();
        recordNew.setMaxPercentPerOrder(record.getMaxPercentPerOrder());
        recordNew.setDeductionPerAmount(record.getDeductionPerAmount());
        recordNew.setCouponStatus(record.getCouponStatus());
        recordNew.setImageUrl(record.getImageUrl());
        UmsIntegrationConsumeSettingExample example =new UmsIntegrationConsumeSettingExample();
        example.or().andAdminIdEqualTo(umsAdmin.getId());
        integrationConsumeSettingMapper.updateByExampleSelective(recordNew,example);
        return CommonResult.success(null);
    }
    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable(required = false) Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.updateWithMch(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        int count = adminService.update(id,umsAdmin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }
    @ApiOperation("生成小程序二维码")
    @RequestMapping(value = "/getLocationSrc", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getLocationSrc(@Validated @RequestBody GetLocationSrcParam param) {
        return CommonResult.success(adminService.getLocationSrc(param));
    }
    @ApiOperation("积分支付二维码")
    @RequestMapping(value = "/getPaySrc", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getPaySrc() {
        return CommonResult.success(adminService.getPaySrc());
    }
}
