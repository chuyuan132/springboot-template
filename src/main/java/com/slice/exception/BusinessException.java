package com.slice.exception;

import com.slice.enums.ErrorCode;
import lombok.Data;

/**
 * 业务异常类
 * @author zhangchuyuan
 */
@Data
public class BusinessException extends RuntimeException {

    private int code;

    public  BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }


}
