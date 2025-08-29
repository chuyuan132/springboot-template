package com.slice.common;

import com.slice.enums.ErrorCode;
import lombok.Data;

/**
 * 通用返回类
 * @author zhangchuyuan
 */
@Data
public class BaseResponse<T> {
    private int code;

    private String message;

    private T data;


    BaseResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }



    BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

}
