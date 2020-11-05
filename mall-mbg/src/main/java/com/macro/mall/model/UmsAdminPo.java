package com.macro.mall.model;

public class UmsAdminPo  extends UmsAdmin{
    //    加两个字段  商户相关
    private String mchId;
    private String mchKey;

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
}