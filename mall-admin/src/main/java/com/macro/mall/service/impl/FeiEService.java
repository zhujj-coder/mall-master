package com.macro.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.dto.feiE.*;
import com.macro.mall.model.UmsPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class FeiEService {
    public static final String URL = "http://api.feieyun.cn/Api/Open/";//不需要修改


    @Autowired
    private RestTemplate template;
    public void addPrinter(UmsPrinter advertise){
        PrinterAddlist printerAddlist =new PrinterAddlist();
        printerAddlist.initDto();
        printerAddlist.setApiname("Open_printerAddlist");
        String content=advertise.getPrinterSn()+"#"
                +advertise.getPrinterKey()+"#"
                +advertise.getPrinterName()+"#"
                +advertise.getCardno();
        printerAddlist.setPrinterContent(content);

        request(printerAddlist);
    }

    private JSONObject request(FeiECommonDto printerAddlist) {
        log.info("飞鹅请求[{}]",printerAddlist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(objectToMap(printerAddlist),headers);
        ResponseEntity<String> jsonObjectStr = template.exchange(URL, HttpMethod.POST, entity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectStr.getBody());
        if(!jsonObject.getString("ret").equals("0")){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),jsonObject.getString("msg"));
        }
        log.info("飞鹅返回[{}]",jsonObject);
        return jsonObject;
    }

    public static MultiValueMap<String, String> objectToMap(Object obj) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        Class<?> tempClass = obj.getClass();
        List<Field> fieldList = new ArrayList<>() ;
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field :fieldList) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = null;
            try {
                value = (String) field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.add(fieldName, value);
        }

        return map;
    }
    public void deletePrinter(List<String> ids){
        PrinterDelList printerDelList = new PrinterDelList();
        printerDelList.initDto();
        printerDelList.setApiname("Open_printerDelList");
        AtomicReference<String> snlist = new AtomicReference<>("");
        ids.forEach(item->{
            snlist.set(snlist + item + "-");
        });
        String s = snlist.toString();
        printerDelList.setSnlist(s.substring(0,s.length()-1));
        JSONObject jsonObject = request(printerDelList);
    }
    public void updatePrinter(UmsPrinter advertise){
        PrinterEdit printerEdit =new PrinterEdit();
        printerEdit.initDto();
        printerEdit.setApiname("Open_printerEdit");
        printerEdit.setSn(advertise.getPrinterSn());
        printerEdit.setName(advertise.getPrinterName());
        printerEdit.setPhonenum(advertise.getCardno());
        JSONObject jsonObject = request(printerEdit);
    }
    public void selectPrinter(UmsPrinter advertise){
        QueryPrinterStatus queryPrinterStatus = new QueryPrinterStatus();
        queryPrinterStatus.initDto();
        queryPrinterStatus.setApiname("Open_queryPrinterStatus");
        queryPrinterStatus.setSn(advertise.getPrinterSn());
        JSONObject jsonObject = request(queryPrinterStatus);
//        返回打印机状态信息。共三种：
//        1、离线。
//        2、在线，工作状态正常。
//        3、在线，工作状态不正常。
//        备注：异常一般是无纸，离线的判断是打印机与服务器失去联系超过2分钟。
        String data = jsonObject.getString("data");
        Integer status=0;
        if(data.contains("离线")){

        }else if(data.contains("正常")){
            status=1;
        }else {
            status=2;
        }
        advertise.setPrinterStatus(status);
    }

}
