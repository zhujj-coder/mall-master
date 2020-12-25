package com.macro.mall.portal.util;

import net.xpyun.platform.opensdk.service.PrintService;
import net.xpyun.platform.opensdk.vo.PrintRequest;

public class PrinterUtil {
    public static void main(String[] args) {
        PrintService printService = new PrintService();
        PrintRequest po = new PrintRequest();
        po.setSn("编号");
        po.setUser("开发者 ID");
        po.setTimestamp("开发者 ID");
        po.setSign("sign");
//
        po.setMode(1);//打印份数
        po.setMode(1);
        po.setMoney(1D);
        StringBuilder content =new StringBuilder();
        po.setContent(content.toString());
        printService.print(po);
    }
}
