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

