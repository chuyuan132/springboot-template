package com.slice.common;

import com.slice.enums.ErrorCode;

/**
 * 返回工具类
 * @author zhangchuyuan
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(ErrorCode.SUCCESS, data);
    }

    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }


    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, message);
    }
}
