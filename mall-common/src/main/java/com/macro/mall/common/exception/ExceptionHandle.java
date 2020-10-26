/**
 * FileName: HandleException
 * Author:   KingDow
 * Date:     2018/4/24 0:21
 * Description: 异常处理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.macro.mall.common.exception;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author KingDow
 * @date 2018/4/24
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e 异常
     * @return 统一错误返回体
     */

    @ExceptionHandler(value = Exception.class)
    public static CommonResult exceptionHandler(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return CommonResult.failed(myException.getMessage());

        } else {
            log.error("系统异常-->>", e);
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR);
        }
    }
}

