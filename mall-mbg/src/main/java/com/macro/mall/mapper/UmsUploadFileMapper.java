package com.macro.mall.mapper;

import com.macro.mall.model.UmsUploadFile;
import com.macro.mall.model.UmsUploadFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsUploadFileMapper {
    long countByExample(UmsUploadFileExample example);

    int deleteByExample(UmsUploadFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsUploadFile record);

    int insertSelective(UmsUploadFile record);

    List<UmsUploadFile> selectByExample(UmsUploadFileExample example);

    UmsUploadFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsUploadFile record, @Param("example") UmsUploadFileExample example);

    int updateByExample(@Param("record") UmsUploadFile record, @Param("example") UmsUploadFileExample example);

    int updateByPrimaryKeySelective(UmsUploadFile record);

    int updateByPrimaryKey(UmsUploadFile record);
}