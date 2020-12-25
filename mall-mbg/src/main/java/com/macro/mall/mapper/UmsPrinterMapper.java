package com.macro.mall.mapper;

import com.macro.mall.model.UmsPrinter;
import com.macro.mall.model.UmsPrinterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsPrinterMapper {
    long countByExample(UmsPrinterExample example);

    int deleteByExample(UmsPrinterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsPrinter record);

    int insertSelective(UmsPrinter record);

    List<UmsPrinter> selectByExample(UmsPrinterExample example);

    UmsPrinter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsPrinter record, @Param("example") UmsPrinterExample example);

    int updateByExample(@Param("record") UmsPrinter record, @Param("example") UmsPrinterExample example);

    int updateByPrimaryKeySelective(UmsPrinter record);

    int updateByPrimaryKey(UmsPrinter record);
}