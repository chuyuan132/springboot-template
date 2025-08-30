package com.slice.aop;

import com.slice.annotation.AuthCheck;
import com.slice.constant.UserConstant;
import com.slice.entity.User;
import com.slice.enums.ErrorCode;
import com.slice.enums.UserRoleEnums;
import com.slice.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthInterceptor {

    @Around("@annotation(authCheck)")
    public Object aroundInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User)userObj;
        // 不需要权限，放行
        if(mustRole == null) {
            return joinPoint.proceed();
        }
        UserRoleEnums mustRoleEnum = UserRoleEnums.getEnumByValue(mustRole);
        UserRoleEnums userRoleEnums = UserRoleEnums.getEnumByValue(currentUser.getUserRole());
        // 未知权限 - 抛错误
        if(mustRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        if(UserRoleEnums.BAN.equals(mustRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        if(UserRoleEnums.ADMIN.equals(mustRoleEnum)) {
            if(!UserRoleEnums.ADMIN.equals(userRoleEnums)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }

        return joinPoint.proceed();
    }
}
