package com.slice.service;

import com.slice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
* @author zhangchuyuan
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword);
}
