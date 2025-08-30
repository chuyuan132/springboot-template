package com.slice.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUserVO implements Serializable {
    private static final long serialVersionUID = 907369066593094232L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 角色： 1 普通 2 管理员 3 禁用
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
