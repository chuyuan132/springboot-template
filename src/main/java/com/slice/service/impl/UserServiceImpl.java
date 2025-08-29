package com.slice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slice.entity.User;
import com.slice.enums.ErrorCode;
import com.slice.exception.BusinessException;
import com.slice.exception.ThrowUtils;
import com.slice.service.UserService;
import com.slice.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 用户服务实现
* @author zhangchuyuan
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    public static final String SALT = "yupi";


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
}




