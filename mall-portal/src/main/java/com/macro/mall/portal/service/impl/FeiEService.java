package com.macro.mall.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.OmsOrderItemMapper;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.OmsOrderItemExample;
import com.macro.mall.model.UmsPrinter;
import com.macro.mall.portal.domain.feiE.FeiECommonDto;
import com.macro.mall.portal.domain.feiE.PrintMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class FeiEService {
    public static final String URL = "http://api.feieyun.cn/Api/Open/";//不需要修改


    @Autowired
    private RestTemplate template;
    @Resource
    private OmsOrderItemMapper omsOrderItemMapper;
    public void printMsg(OmsOrder omsOrder, UmsPrinter item){
        PrintMsg printMsg =new PrintMsg();
        printMsg.initDto();
        printMsg.setApiname("Open_printMsg");
        printMsg.setSn(item.getPrinterSn());
        printMsg.setTimes(item.getCopies().toString());
        StringBuilder sb = new StringBuilder();
        sb.append("<CB>"+item.getTitle()+"</CB><BR>");
        String items =getItems(omsOrder);
        sb.append(items)
                .append("备注:"+omsOrder.getNote()+"<BR>")
                .append("合计：￥"+omsOrder.getTotalAmount()+"<BR>")
                .append(""+"<BR>")
                .append("抵扣: ￥-"+(omsOrder.getIntegrationAmount()==null?"0":omsOrder.getIntegrationAmount().toString())+"<BR>")
                .append("实付: ￥"+omsOrder.getPayAmount()+"<BR>")
                .append(""+omsOrder.getReceiverProvince()==null?"":omsOrder.getReceiverProvince() + omsOrder.getReceiverCity()==null?"":omsOrder.getReceiverCity() + omsOrder.getReceiverRegion()==null?"": omsOrder.getReceiverRegion()
                        +"  "+omsOrder.getReceiverPhone()+"  "+omsOrder.getReceiverName()+"<BR>");
        if(item.getPrinterQrUrl()!=null&&item.getPrinterQrUrl().length()>0){
            sb.append(item.getPrinterQrTitle()+"<BR>")
                    .append("<QR>"+item.getPrinterQrUrl()+"</QR>");
        }
        printMsg.setContent(sb.toString());
        request(printMsg);
    }

    private String getItems(OmsOrder omsOrder) {
        StringBuilder sb = new StringBuilder("--------------------------------<BR>");
        OmsOrderItemExample exampleItem = new OmsOrderItemExample();
        exampleItem.or().andOrderSnEqualTo(omsOrder.getOrderSn());
        List<OmsOrderItem> omsOrderItems = omsOrderItemMapper.selectByExample(exampleItem);

        omsOrderItems.forEach(item2->{
            sb.append(item2.getProductName())
                    .append("  x")
                    .append(item2.getProductQuantity())
                    .append("  ￥")
                    .append(item2.getProductPrice().toString())
                    .append("<BR>");
        });
        sb.append("--------------------------------<BR>");
        return sb.toString();
    }

    private JSONObject request(FeiECommonDto printerAddlist) {
        log.info("飞鹅请求[{}]",printerAddlist);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(objectToMap(printerAddlist),headers);
        ResponseEntity<String> jsonObjectStr = template.exchange(URL, HttpMethod.POST, entity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectStr.getBody());
        if(!jsonObject.getString("ret").equals("0")){
            log.error("异常！！");
//            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),jsonObject.getString("msg"));
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



}
