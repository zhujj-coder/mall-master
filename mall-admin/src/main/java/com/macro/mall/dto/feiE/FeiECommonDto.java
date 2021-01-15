package com.macro.mall.dto.feiE;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

@Data
public class FeiECommonDto {
    private String user ;
    private String stime;
    private String sig;
    private String apiname;

    public void initDto(){
        this.user="zjjhot@163.com";
        this.stime=String.valueOf(System.currentTimeMillis()/1000);
        this.sig=signature(this.user,"jHUkuNGhHkwHEG2e",this.stime);
    }

    //生成签名字符串
    private static String signature(String USER,String UKEY,String STIME){
        String s = DigestUtils.sha1Hex(USER+UKEY+STIME);
        return s;
    }
}
