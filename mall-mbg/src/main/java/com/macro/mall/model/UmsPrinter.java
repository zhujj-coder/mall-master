package com.macro.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class UmsPrinter implements Serializable {
    private Long id;

    private Long adminId;

    @ApiModelProperty(value = "打印机名称")
    private String printerName;

    @ApiModelProperty(value = "打印机sn")
    private String printerSn;

    @ApiModelProperty(value = "0真人语音（大） 1真人语音（中） 2真人语音（小） 3 嘀嘀声 4 静音")
    private Integer printerVoiceType;

    @ApiModelProperty(value = "0 表示离线1 表示在线正常2 表示在线异常")
    private Integer printerStatus;

    @ApiModelProperty(value = "统一打印标题")
    private String title;

    @ApiModelProperty(value = "二维码地址")
    private String printerQrUrl;

    @ApiModelProperty(value = "手机号")
    private String cardno;

    @ApiModelProperty(value = "打印份数")
    private Integer copies;

    @ApiModelProperty(value = "二维码地址")
    private String printerQrTitle;

    @ApiModelProperty(value = "1:芯烨 2：飞鹅")
    private Integer printerFactory;

    @ApiModelProperty(value = "打印机识别号，飞鹅有")
    private String printerKey;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterSn() {
        return printerSn;
    }

    public void setPrinterSn(String printerSn) {
        this.printerSn = printerSn;
    }

    public Integer getPrinterVoiceType() {
        return printerVoiceType;
    }

    public void setPrinterVoiceType(Integer printerVoiceType) {
        this.printerVoiceType = printerVoiceType;
    }

    public Integer getPrinterStatus() {
        return printerStatus;
    }

    public void setPrinterStatus(Integer printerStatus) {
        this.printerStatus = printerStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrinterQrUrl() {
        return printerQrUrl;
    }

    public void setPrinterQrUrl(String printerQrUrl) {
        this.printerQrUrl = printerQrUrl;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public String getPrinterQrTitle() {
        return printerQrTitle;
    }

    public void setPrinterQrTitle(String printerQrTitle) {
        this.printerQrTitle = printerQrTitle;
    }

    public Integer getPrinterFactory() {
        return printerFactory;
    }

    public void setPrinterFactory(Integer printerFactory) {
        this.printerFactory = printerFactory;
    }

    public String getPrinterKey() {
        return printerKey;
    }

    public void setPrinterKey(String printerKey) {
        this.printerKey = printerKey;
    }
}