DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号',
  `address` varchar(512) DEFAULT NULL COMMENT '地址',
  `type` int(1) NOT NULL COMMENT '类型  1管理员 2普通用户',
  `status` int(1) NOT NULL COMMENT '状态',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_account` (`account`),
  KEY `index_account_password` (`account`,`password`),
  KEY `index_gmt_modified` (`gmt_modified`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人',
  `operator_type` int(11) DEFAULT NULL COMMENT '操作类型',
  `module` int(11) DEFAULT NULL COMMENT '主模块',
  `action` int(11) DEFAULT NULL COMMENT '行为',
  `biz_id` bigint(20) DEFAULT NULL COMMENT '业务id',
  `biz_code` varchar(64) DEFAULT NULL COMMENT '业务code',
  `msg` varchar(128) DEFAULT NULL COMMENT '描述',
  `param_data` varchar(2048) DEFAULT NULL COMMENT '参数数据',
  `result_data` varchar(2048) DEFAULT NULL COMMENT '结果数据',
  `cost` bigint(11) DEFAULT NULL COMMENT '消耗时间',
  `location` int(11) DEFAULT NULL COMMENT '存储类型',
  `level` int(11) DEFAULT NULL COMMENT '日志级别',
  `stack_trace` text COMMENT '错误堆栈',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;


INSERT INTO test.user (account, password, username, sex, age, mobile, address, type, status, gmt_create, gmt_modified)
VALUES ('liyuan', '1234', '李远', 1, 27, '18758595684', '浙江杭州', 1, 1, '2016-05-02 14:11:19', '2016-05-02 14:11:41');
INSERT INTO test.user (account, password, username, sex, age, mobile, address, type, status, gmt_create, gmt_modified)
VALUES ('zhangsan', '1234', '张三', 1, 28, null, null, 1, 1, '2016-05-02 14:14:08', null);
INSERT INTO test.user (account, password, username, sex, age, mobile, address, type, status, gmt_create, gmt_modified)
VALUES ('xiaohua', '1234', '小花', 2, 25, null, null, 1, 1, '2016-05-02 14:17:32', null);