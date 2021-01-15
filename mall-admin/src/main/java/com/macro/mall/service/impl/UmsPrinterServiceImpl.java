package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.common.enums.ExceptionEnum;
import com.macro.mall.common.exception.MyException;
import com.macro.mall.mapper.UmsPrinterMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.service.UmsPrinterService;
import lombok.extern.slf4j.Slf4j;
import net.xpyun.platform.opensdk.service.PrintService;
import net.xpyun.platform.opensdk.util.HashSignUtil;
import net.xpyun.platform.opensdk.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页广告管理Service实现类
 * Created by macro on 2018/11/7.
 */
@Service
@Slf4j
public class UmsPrinterServiceImpl implements UmsPrinterService {
    @Autowired
    private UmsPrinterMapper umsPrinterMapper;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private FeiEService feiEService;
    final private String USER ="zjjhot@163.com";
    final private String UserKEY ="d8dc615805a24fbb908f524110be83b5";
    @Override
    public int create(UmsPrinter advertise) {
//        添加打印机
        Integer printerFactory = advertise.getPrinterFactory();
        if(printerFactory==1){
            addPrinter(advertise);
        }else if(printerFactory==2){
            feiEService.addPrinter(advertise);
        }else{
            log.info("打印机类型有误！[{}]",advertise.getPrinterFactory());
            throw new MyException(ExceptionEnum.PRINTER_TYPE_ERROR);
        }

        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        advertise.setAdminId(currentAdmin.getId());
        return umsPrinterMapper.insert(advertise);
    }

    private void addPrinter(UmsPrinter advertise) {
        PrintService printService = new PrintService();
        AddPrinterRequest restRequest = new AddPrinterRequest();
        initRequest(restRequest);
        AddPrinterRequestItem[] items = new AddPrinterRequestItem[1];
        AddPrinterRequestItem item = new AddPrinterRequestItem();
        item.setName(advertise.getPrinterName());
        item.setSn(advertise.getPrinterSn());
        item.setCardno(advertise.getCardno());
        items[0]=item;
//
        restRequest.setItems(items);
        log.info("请求[{}]",restRequest);
        ObjectRestResponse<PrinterResult> printerResultObjectRestResponse = printService.addPrinters(restRequest);
        log.info("返回[{}]",printerResultObjectRestResponse);
        handResponse(printerResultObjectRestResponse);
    }

    private void handResponse(ObjectRestResponse printerResultObjectRestResponse) {
        if(printerResultObjectRestResponse.getCode()!=0){
            throw new MyException(ExceptionEnum.UNKNOWN_ERROR.getCode(),printerResultObjectRestResponse.getMsg());
        }
    }
    private void initRequest(RestRequest request) {
        request.setUser(USER);
        request.setTimestamp( String.valueOf(System.currentTimeMillis()/1000));
        request.setSign(HashSignUtil.sign(USER+UserKEY+request.getTimestamp()));
    }

    @Override
    public int delete(List<Long> ids) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        UmsPrinterExample example =new UmsPrinterExample();
        example.or().andIdIn(ids)
                .andAdminIdEqualTo(currentAdmin.getId());
        List<UmsPrinter> umsPrinters = umsPrinterMapper.selectByExample(example);
        List<String>  ids1 = new ArrayList<>();
        List<String>  ids2 = new ArrayList<>();
        umsPrinters.forEach(item->{
            if(item.getPrinterFactory()==1){
                ids1.add(item.getPrinterSn());
            }else if(item.getPrinterFactory()==2){
                ids2.add(item.getPrinterSn());
            }
        });
        if(ids1.size()>0){
            deletePrinter(ids1);
        }
        if(ids2.size()>0){
            feiEService.deletePrinter(ids2);
        }
        return umsPrinterMapper.deleteByExample(example);
    }

    private void deletePrinter(List<String> ids) {
        PrintService printService = new PrintService();
        DelPrinterRequest restRequest = new DelPrinterRequest();
        initRequest(restRequest);
        String[] arr = new String[ids.size()];
        restRequest.setSnlist(ids.toArray(arr));
        log.info("请求[{}]",restRequest);
        ObjectRestResponse<PrinterResult> printerResultObjectRestResponse = printService.delPrinters(restRequest);
        log.info("返回[{}]",printerResultObjectRestResponse);
        handResponse(printerResultObjectRestResponse);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        UmsPrinter record = new UmsPrinter();
//        record.setId(id);
//        record.setStatus(status);
        UmsPrinterExample example = new UmsPrinterExample();
        example.or()
                .andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return umsPrinterMapper.updateByExampleSelective(record,example);
    }

    @Override
    public UmsPrinter getItem(Long id) {
        return umsPrinterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, UmsPrinter advertise) {
        advertise.setId(id);
        UmsPrinter umsPrinter = umsPrinterMapper.selectByPrimaryKey(id);
        Integer printerFactory = umsPrinter.getPrinterFactory();
//        更新打印机
        if(printerFactory==1){
            updatePrinter(advertise);
        }else if(printerFactory==2){
            feiEService.updatePrinter(advertise);
        }else{
            log.info("打印机类型有误！[{}]",printerFactory);
            throw new MyException(ExceptionEnum.PRINTER_TYPE_ERROR);
        }

        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();

        UmsPrinterExample example = new UmsPrinterExample();
        example.or()
                .andIdEqualTo(id)
                .andAdminIdEqualTo(currentAdmin.getId());
        return umsPrinterMapper.updateByExampleSelective(advertise,example);
    }

    private void updatePrinter(UmsPrinter advertise) {
        PrintService printService = new PrintService();
        UpdPrinterRequest restRequest = new UpdPrinterRequest();
        initRequest(restRequest);
        restRequest.setName(advertise.getPrinterName());
        restRequest.setSn(advertise.getPrinterSn());
        restRequest.setCardno(advertise.getCardno());
//
        log.info("请求[{}]",restRequest);
        ObjectRestResponse<Boolean> printerResultObjectRestResponse = printService.updPrinter(restRequest);
        log.info("返回[{}]",printerResultObjectRestResponse);
        handResponse(printerResultObjectRestResponse);
    }

    @Override
    public List<UmsPrinter> list(Integer pageSize, Integer pageNum, Integer printerFactory) {
        UmsAdmin currentAdmin = umsAdminService.getCurrentAdmin();
        PageHelper.startPage(pageNum, pageSize);
        UmsPrinterExample example = new UmsPrinterExample();
        UmsPrinterExample.Criteria criteria = example.createCriteria();
        criteria.andAdminIdEqualTo(currentAdmin.getId())
                .andPrinterFactoryEqualTo(printerFactory);
        List<UmsPrinter> umsPrinters = umsPrinterMapper.selectByExample(example);
        for (UmsPrinter umsPrinter:umsPrinters) {
            if(printerFactory==1){
                selectPrinter(umsPrinter);
            }else if(printerFactory==2){
                feiEService.selectPrinter(umsPrinter);
            }else{
                log.info("打印机类型有误！[{}]",printerFactory);
                throw new MyException(ExceptionEnum.PRINTER_TYPE_ERROR);
            }
        }
        return umsPrinters;
    }

    private void selectPrinter(UmsPrinter umsPrinters) {
        PrintService printService = new PrintService();
        PrinterRequest restRequest = new PrinterRequest();
        initRequest(restRequest);
        restRequest.setSn(umsPrinters.getPrinterSn());
//
        log.info("请求[{}]",restRequest);
        ObjectRestResponse<Integer> printerResultObjectRestResponse = printService.queryPrinterStatus(restRequest);
        log.info("返回[{}]",printerResultObjectRestResponse);
        handResponse(printerResultObjectRestResponse);
        umsPrinters.setPrinterStatus(printerResultObjectRestResponse.getData());
    }
}
