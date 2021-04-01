-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by jenysun at 01-04-2021 22:43.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.


-- BEGIN TABLE friend
DROP TABLE IF EXISTS friend;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `friend_id` varchar(20) DEFAULT NULL COMMENT '好友id',
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立时间',
  `type_id` int(11) DEFAULT NULL COMMENT '好友分组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Inserting 3 rows into friend
-- Insert batch #1
INSERT INTO friend (id, user_id, friend_id, build_time, type_id) VALUES
(1, '1', '2', '2021-03-18 14:21:52', 1),
(2, '2', '1', '2021-03-19 01:13:10', 1),
(3, '3', '1', '2021-03-19 01:23:56', 1);

-- END TABLE friend

-- BEGIN TABLE friend_apply
DROP TABLE IF EXISTS friend_apply;
CREATE TABLE `friend_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` varchar(20) NOT NULL COMMENT '申请人userId',
  `to_user_id` varchar(20) NOT NULL COMMENT '被申请人userId',
  `remark` varchar(255) DEFAULT NULL COMMENT '申请原因',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `status` int(11) NOT NULL COMMENT '申请状态：0待处理，1同意，2拒绝',
  `friend_type_id` int(11) NOT NULL COMMENT '好友分组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table friend_apply contains no data. No inserts have been genrated.
-- Inserting 0 rows into friend_apply


-- END TABLE friend_apply

-- BEGIN TABLE friend_message
DROP TABLE IF EXISTS friend_message;
CREATE TABLE `friend_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` varchar(20) DEFAULT NULL COMMENT '发消息的人的id',
  `to_user_id` varchar(20) DEFAULT NULL COMMENT '收消息的人的id',
  `content` varchar(2000) DEFAULT NULL COMMENT '消息内容',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `is_read` int(11) DEFAULT NULL COMMENT '是否已读，1是0否',
  `is_del` int(11) DEFAULT NULL COMMENT '是否删除，1是0否',
  `is_back` int(11) DEFAULT NULL COMMENT '是否撤回，1是0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Inserting 16 rows into friend_message
-- Insert batch #1
INSERT INTO friend_message (id, from_user_id, to_user_id, content, send_time, is_read, is_del, is_back) VALUES
(1, '1', '2', 'ada', '2021-04-01 16:11:35', 0, 0, 0),
(2, '1', '2', '123', '2021-04-01 16:13:28', 0, 0, 0),
(3, '1', '2', '123123', '2021-04-01 21:11:50', 0, 0, 0),
(4, '1', '2', '231231', '2021-04-01 21:12:07', 0, 0, 0),
(5, '1', '2', '12312312', '2021-04-01 21:12:09', 0, 0, 0),
(6, '1', '2', '123123', '2021-04-01 21:13:24', 0, 0, 0),
(7, '1', '2', 'adsasdasd', '2021-04-01 21:13:26', 0, 0, 0),
(8, '1', '2', 'adasdasdas', '2021-04-01 21:13:27', 0, 0, 0),
(9, '1', '2', 'ddasda', '2021-04-01 21:13:29', 0, 0, 0),
(10, '2', '1', 'adasdadsazxc', '2021-04-01 21:13:34', 0, 0, 0),
(11, '2', '1', 'eeqweqw', '2021-04-01 21:13:36', 0, 0, 0),
(12, '1', '2', 'asdasdasda', '2021-04-01 21:13:39', 0, 0, 0),
(13, '1', '2', 'asdasda', '2021-04-01 21:13:40', 0, 0, 0),
(14, '2', '1', '131131231', '2021-04-01 21:13:45', 0, 0, 0),
(15, '1', '2', 'adasdas', '2021-04-01 21:13:49', 0, 0, 0),
(16, '1', '2', '0102301230123', '2021-04-01 21:14:27', 0, 0, 0);

-- END TABLE friend_message

-- BEGIN TABLE friend_type
DROP TABLE IF EXISTS friend_type;
CREATE TABLE `friend_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `type_name` varchar(255) DEFAULT NULL COMMENT '分组名',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_default` int(11) DEFAULT '0' COMMENT '是否为默认分组：1为默认，0为不是默认分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Inserting 1 row into friend_type
-- Insert batch #1
INSERT INTO friend_type (id, type_name, user_id, build_time, is_default) VALUES
(1, '家人', '1', '2021-03-18 14:22:20', 1);

-- END TABLE friend_type

-- BEGIN TABLE group_message
DROP TABLE IF EXISTS group_message;
CREATE TABLE `group_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `group_id` int(11) DEFAULT NULL COMMENT '群id',
  `content` varchar(2000) DEFAULT NULL COMMENT '群消息内容',
  `is_del` int(11) DEFAULT NULL COMMENT '是否删除，1是 0否',
  `is_read` int(11) DEFAULT NULL COMMENT '是否已读，1是，0否。',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_back` int(11) DEFAULT NULL COMMENT '是否撤回，1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table group_message contains no data. No inserts have been genrated.
-- Inserting 0 rows into group_message


-- END TABLE group_message

-- BEGIN TABLE group_user
DROP TABLE IF EXISTS group_user;
CREATE TABLE `group_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `group_id` int(11) DEFAULT NULL COMMENT '群id',
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入群时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Inserting 3 rows into group_user
-- Insert batch #1
INSERT INTO group_user (id, user_id, group_id, join_time) VALUES
(1, '1', 1, '2021-03-19 01:12:14'),
(2, '2', 1, '2021-03-19 01:12:20'),
(3, '3', 1, '2021-03-19 01:12:29');

-- END TABLE group_user

-- BEGIN TABLE t_group
DROP TABLE IF EXISTS t_group;
CREATE TABLE `t_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `group_num` varchar(255) DEFAULT NULL COMMENT '群号',
  `group_name` varchar(255) DEFAULT NULL COMMENT '群名称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '群logo',
  `user_id` varchar(20) DEFAULT NULL COMMENT '群主userId',
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '群创建时间',
  `description` varchar(200) DEFAULT NULL COMMENT '群简介',
  `status` int(1) DEFAULT '1' COMMENT '群状态：1为正常，0为禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Inserting 1 row into t_group
-- Insert batch #1
INSERT INTO t_group (id, group_num, group_name, avatar, user_id, build_time, description, status) VALUES
(1, '1', 'java技术交流群', NULL, '1', '2021-03-19 01:10:55', 'java技术交流', 1);

-- END TABLE t_group

-- BEGIN TABLE t_user
DROP TABLE IF EXISTS t_user;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(20) DEFAULT NULL COMMENT '帐号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` int(11) DEFAULT '0' COMMENT '性别：0为男，1为女',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `role_code` int(11) DEFAULT '1' COMMENT '角色code：1为用户，2为管理员',
  `user_status` int(11) DEFAULT '1' COMMENT '用户状态：1为正常，0为禁止登录，2为禁止聊天',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Inserting 3 rows into t_user
-- Insert batch #1
INSERT INTO t_user (id, user_name, password, nick_name, gender, avatar, email, phone, role_code, user_status, sign) VALUES
(1, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 1, 'images/boy-01.png', '975530674@qq.com', '13797052042', 2, 1, NULL),
(2, 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '李四', 1, 'images/boy-02.png', '975530674@qq.com', '13797052042', 2, 1, NULL),
(3, 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '王五', 1, 'images/boy-03.png', '975530674@qq.com', '13797052042', 2, 1, NULL);

-- END TABLE t_user

