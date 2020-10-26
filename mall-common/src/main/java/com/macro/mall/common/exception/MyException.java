package com.macro.mall.common.exception;

import com.macro.mall.common.enums.ExceptionEnum;
import org.springframework.util.StringUtils;

public class MyException extends RuntimeException {
    private Integer code;
    private String message;

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public MyException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public MyException(Integer code) {
        super(ExceptionEnum.getMessage(code));
        this.code = code;
        String message = ExceptionEnum.getMessage(code);
        if (StringUtils.isEmpty(message)) {
            this.message = message;
        }
    }

    public MyException(int code) {
        super(ExceptionEnum.getMessage(code));
        this.code = code;
        String message = ExceptionEnum.getMessage(code);
        if (StringUtils.isEmpty(message)) {
            this.message = message;
        }
    }

    public Integer getCode() {
        return code;
    }
}
