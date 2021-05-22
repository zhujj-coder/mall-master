package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class UmsUploadFile implements Serializable {
    private Long id;

    private Long shareAdminId;

    private String mobileNo;

    private String wechatNo;

    private String cardNo;

    private String bankName;

    private String fileListIdCards;

    private String fileListBusiness;

    private String fileListPermit;

    @ApiModelProperty(value = "0:未创建，1 已创建")
    private Integer createStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShareAdminId() {
        return shareAdminId;
    }

    public void setShareAdminId(Long shareAdminId) {
        this.shareAdminId = shareAdminId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFileListIdCards() {
        return fileListIdCards;
    }

    public void setFileListIdCards(String fileListIdCards) {
        this.fileListIdCards = fileListIdCards;
    }

    public String getFileListBusiness() {
        return fileListBusiness;
    }

    public void setFileListBusiness(String fileListBusiness) {
        this.fileListBusiness = fileListBusiness;
    }

    public String getFileListPermit() {
        return fileListPermit;
    }

    public void setFileListPermit(String fileListPermit) {
        this.fileListPermit = fileListPermit;
    }

    public Integer getCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(Integer createStatus) {
        this.createStatus = createStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}