create database if not exists my_db;

use my_db;

create table if not exists user (
    id bigint auto_increment comment 'id',
    user_account varchar(256) not null comment '用户账号',
    user_password varchar(256) not null comment '用户密码',
    union_id varchar(256) default '' null comment '微信开发平台id',
    user_name varchar(256) default '' null comment '用户名',
    user_avatar varchar(256) default '' null comment '用户头像',
    user_profile varchar(256) default '' null  comment '用户简介',
    phone varchar(10) default '' null comment '手机号',
    email varchar(20) default '' null comment '邮箱',
    user_role varchar(20) default '' null comment '角色: 管理员:admin  禁用: ban',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete tinyint default 0 comment '是否删除 0 不删除 1 删除',
    primary key(id),
    UNIQUE INDEX idx_user_account (user_account)
) comment '用户' collate utf8mb4_general_ci