package com.macro.mall.service;

import com.macro.mall.model.UmsPrinter;

import java.util.List;

/**
 * 首页广告管理Service
 * Created by macro on 2018/11/7.
 */
public interface UmsPrinterService {
    /**
     * 添加广告
     */
    int create(UmsPrinter advertise);

    /**
     * 批量删除广告
     */
    int delete(List<Long> ids);

    /**
     * 修改上、下线状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 获取广告详情
     */
    UmsPrinter getItem(Long id);

    /**
     * 更新广告
     */
    int update(Long id, UmsPrinter advertise);

    /**
     * 分页查询广告
     */
    List<UmsPrinter> list(Integer pageSize, Integer pageNum);
}
