package com.slice.exception;

import com.slice.enums.ErrorCode;
import org.jetbrains.annotations.Contract;

/**
 * 抛异常工具类
 * @author zhangchuyuan
 */
public class ThrowUtils {

    @Contract("true, _ -> fail")
    public static void throwIf(Boolean condition, RuntimeException runtimeException) {
        if(condition) {
            throw runtimeException;
        }

    }

    @Contract("true, _ -> fail")
    public static void throwIf(Boolean condition, ErrorCode errorCode) {
        if(condition) {
            throw new BusinessException(errorCode);
        }
    }

    @Contract("true, _,_ -> fail")
    public static void throwIf(Boolean condition, ErrorCode errorCode, String message) {
        if(condition) {
            throw new BusinessException(errorCode, message);
        }
    }

    @Contract("true, _,_ -> fail")
    public static void throwIf(Boolean condition, int code, String message) {
        if(condition) {
            throw new BusinessException(code, message);
        }
    }

}
