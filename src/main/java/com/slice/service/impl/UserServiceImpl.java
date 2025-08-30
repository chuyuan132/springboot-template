package com.slice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slice.constant.UserConstant;
import com.slice.entity.User;
import com.slice.enums.ErrorCode;
import com.slice.exception.BusinessException;
import com.slice.exception.ThrowUtils;
import com.slice.service.UserService;
import com.slice.mapper.UserMapper;
import com.slice.vo.user.LoginUserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 用户服务实现
* @author zhangchuyuan
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    public static final String SALT = "SLICE_ZCY";


    @Resource
    private UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword) {
        synchronized (userAccount.intern()) {
            if(userAccount.length() < 4) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能少于4位");
            }
            if(userPassword.length() < 8) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能少于8位");
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_account", userAccount);
            Long count = userMapper.selectCount(queryWrapper);
            if(count > 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户已存在");
            }
            String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(newPassword);
            int saveResult = userMapper.insert(user);
            if(saveResult <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户注册失败");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能少于4位");
        }
        if(userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能少于8位");
        }
        String encrpytPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("user_password", encrpytPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户不存在或密码错误");
        }
        // todo: 此处可能存在session覆盖问题
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return getLoginUserVO(user);
    }

    @Override
    public Boolean userLogOut(HttpServletRequest request) {
        if(request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"未登录");
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User)userObj;
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "未登录");
        }
        return currentUser;
    }
}




