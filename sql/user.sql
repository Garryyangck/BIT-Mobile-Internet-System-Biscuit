drop table if exists `user`;
create table if not exists `user`
(
    `id`              bigint        not null comment 'id',
    `user_account`    varchar(256)  not null comment '账号',
    `user_password`   varchar(512)  not null comment '密码',
    `user_name`       varchar(256)  null comment '用户昵称',
    `user_avatar`     varchar(1024) null comment '用户头像',
    `user_profile`    varchar(512)  null comment '用户简介',
    `user_signature`  varchar(512)  null comment '用户签名',
    `user_experience` bigint        not null default 0 comment '用户经验值',
    `user_level`      int           not null default 1 comment '用户等级',
    `user_role`       int           not null default 0 comment '用户角色|枚举[UserRoleEnum]',
    `create_time`     datetime(3) comment '新增时间',
    `update_time`     datetime(3) comment '修改时间',
    primary key (`id`),
    unique key `user_account_unique` (`user_account`)
) default charset = utf8mb4 comment '用户';

drop table if exists `token`;
create table if not exists `token`
(
    `id`          bigint       not null comment 'id',
    `user_id`     bigint       not null comment '用户id',
    `jwt`         varchar(256) not null comment 'JWT',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`)
) default charset = utf8mb4 comment '用户';