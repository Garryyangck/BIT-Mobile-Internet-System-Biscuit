drop table if exists `product`;
create table if not exists `product`
(
    `id`            bigint         not null comment 'id',
    `seller_id`     bigint         not null comment '卖家id',
    `category_id`   bigint         not null comment '标签id',
    `category_name` varchar(256)   not null comment '标签名',
    `title`         varchar(256)   not null comment '标题',
    `detail`        varchar(256)   not null comment '商品详情',
    `picture`       json           not null comment '图片',                                   # 多张图片
    `fineness`      int            null comment '成色|枚举[ProductFinenessEnum]',
    `brand`         varchar(256)   null comment '品牌',
    `model`         varchar(256)   null comment '型号',
    `price`         decimal(20, 2) not null comment '价格',
    `location`      int            not null comment '地点|枚举[ProductLocationEnum]',
    `status`        int            not null default 0 comment '状态|枚举[ProductStatusEnum]', # 有没有卖出，因为我们默认每个商品只有一件
    `create_time`   datetime(3) comment '新增时间',
    `update_time`   datetime(3) comment '修改时间',
    primary key (`id`)
) default charset = utf8mb4 comment '商品';

drop table if exists `category`;
create table if not exists `category`
(
    `id`          bigint not null comment 'id',
    `name`        bigint not null comment '标签名',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`)
) default charset = utf8mb4 comment '标签';

drop table if exists `follow`;
create table if not exists `follow`
(
    `id`          bigint not null comment 'id',
    `from_id`     bigint not null comment '用户id',
    `to_id`       bigint not null comment '被关注者id',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`),
    unique key `from_id_to_id_unique` (`from_id`, `to_id`)
) default charset = utf8mb4 comment '关注';

drop table if exists `star`;
create table if not exists `star`
(
    `id`          bigint not null comment 'id',
    `user_id`     bigint not null comment '用户id',
    `product_id`  bigint not null comment '收藏商品id',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`),
    unique key `user_id_product_id_unique` (`user_id`, `product_id`)
) default charset = utf8mb4 comment '收藏';

drop table if exists `conversation`;
create table if not exists `conversation`
(
    `id`              bigint       not null comment 'id',
    `seller_id`       bigint       not null comment '卖家id',
    `buyer_id`        bigint       not null comment '买家id',
    `product_id`      bigint       not null comment '商品id',
    `product_picture` varchar(256) not null comment '商品图片', # 第一张
    `create_time`     datetime(3) comment '新增时间',
    `update_time`     datetime(3) comment '修改时间',
    primary key (`id`),
    unique key `seller_id_buyer_id_product_id_unique` (`seller_id`, `buyer_id`, `product_id`)
) default charset = utf8mb4 comment '聊天';

drop table if exists `message`;
create table if not exists `message`
(
    `id`          bigint       not null comment 'id',
    `from_id`     bigint       not null comment '发出者id',
    `to_id`       bigint       not null comment '接收者id',
    `product_id`  bigint       not null comment '商品id',
    `content`     varchar(256) not null comment '内容',
    `type`        int          not null comment '消息类型|枚举[MessageTypeEnum]',
    `is_read`     int          not null default 0 comment '是否已读，0-未读，1-已读',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`)
) default charset = utf8mb4 comment '消息';

# 先对数据进行分页，对每一页的商品进行筛选，筛选的结果(>=0)少于一半，则凑到一半
drop table if exists `preference`;
create table if not exists `preference`
(
    `id`          bigint not null comment 'id',
    `user_id`     bigint not null comment '用户id',
    `category_id` bigint not null comment '标签id',
    `number`      int    not null default 0 comment '偏好值',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '修改时间',
    primary key (`id`),
    unique key `user_id_category_id_unique` (`user_id`, `category_id`)
) default charset = utf8mb4 comment '偏好';
