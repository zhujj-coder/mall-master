package com.macro.mall.dao;

import com.macro.mall.dto.UmsMemberCount;
import org.apache.ibatis.annotations.Param;


/**
 */
public interface UmsMemberDao {

    UmsMemberCount getMemberCount(@Param("adminId") Long adminId);
}
