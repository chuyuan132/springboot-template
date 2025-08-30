package com.slice.controller;

import com.slice.annotation.AuthCheck;
import com.slice.common.BaseResponse;
import com.slice.common.ResultUtils;
import com.slice.dao.user.UserLoginRequest;
import com.slice.dao.user.UserRegisterRequest;
import com.slice.enums.ErrorCode;
import com.slice.exception.BusinessException;
import com.slice.exception.ThrowUtils;
import com.slice.service.UserService;
import com.slice.vo.user.LoginUserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务控制层
 * @author zhangchuyuan
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userPassword = userRegisterRequest.getUserPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        if(StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword);
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userPassword = userLoginRequest.getUserPassword();
        String userAccount = userLoginRequest.getUserAccount();
        if(StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO result = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(result);
    }
}
