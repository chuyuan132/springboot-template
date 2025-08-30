package com.slice.service;

import com.slice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slice.vo.user.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

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

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 请求
     * @return 用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户注销
     * @param request 请求
     * @return 是否成功
     */
    Boolean userLogOut(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     * @param user 用户对象
     * @return 脱敏对象
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取当前用户
     * @param request 请求
     * @return 用户信息
     */
    User getCurrentUser(HttpServletRequest request);

}
