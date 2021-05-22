package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UmsAdmin implements Serializable {
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    @ApiModelProperty(value = "通知方式：1 每天 2 指定时间")
    private String noticeType;

    @ApiModelProperty(value = "hh:mm 或 yyyy-MM-DD hh:mm")
    private String noticeStart;

    @ApiModelProperty(value = "时间段")
    private String noticeEnd;

    @ApiModelProperty(value = "是否开启通知0 关闭 1 开启")
    private Integer noticeOn;

    @ApiModelProperty(value = "商户ID")
    private String mchId;

    @ApiModelProperty(value = "商户秘钥")
    private String mchKey;

    @ApiModelProperty(value = "刷新令牌")
    private String authorizerRefreshToken;

    @ApiModelProperty(value = "是否已发布小程序:0未发布 1已发布")
    private Integer publishStatus;

    @ApiModelProperty(value = "小程序码，不带参数")
    private String wxacodeUrl;

    @ApiModelProperty(value = "小程序码，支付码")
    private String wxacodePayUrl;

    @ApiModelProperty(value = "会员到期日志")
    private Date vipEndDate;

    @ApiModelProperty(value = "取货手机号")
    private String contactMobile;

    @ApiModelProperty(value = "取货地址")
    private String contactAddress;

    @ApiModelProperty(value = "小程序消息模板id")
    private String wxTemplateId;

    @ApiModelProperty(value = "是否支持配送")
    private Integer supportDelivery;

    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeStart() {
        return noticeStart;
    }

    public void setNoticeStart(String noticeStart) {
        this.noticeStart = noticeStart;
    }

    public String getNoticeEnd() {
        return noticeEnd;
    }

    public void setNoticeEnd(String noticeEnd) {
        this.noticeEnd = noticeEnd;
    }

    public Integer getNoticeOn() {
        return noticeOn;
    }

    public void setNoticeOn(Integer noticeOn) {
        this.noticeOn = noticeOn;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getWxacodeUrl() {
        return wxacodeUrl;
    }

    public void setWxacodeUrl(String wxacodeUrl) {
        this.wxacodeUrl = wxacodeUrl;
    }

    public String getWxacodePayUrl() {
        return wxacodePayUrl;
    }

    public void setWxacodePayUrl(String wxacodePayUrl) {
        this.wxacodePayUrl = wxacodePayUrl;
    }

    public Date getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(Date vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getWxTemplateId() {
        return wxTemplateId;
    }

    public void setWxTemplateId(String wxTemplateId) {
        this.wxTemplateId = wxTemplateId;
    }

    public Integer getSupportDelivery() {
        return supportDelivery;
    }

    public void setSupportDelivery(Integer supportDelivery) {
        this.supportDelivery = supportDelivery;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
    }
}