
DROP DATABASE IF EXISTS `cnoversea_api`;
CREATE DATABASE `cnoversea_api`;

USE `cnoversea_api`;

DROP TABLE IF EXISTS `cnos_user`;
CREATE TABLE `cnos_user` (
  `uuid`         varchar(32) NOT NULL                                                       COMMENT '用户UUID',
  `uid`          varchar(32) NOT NULL                                                       COMMENT '用户登录ID',
  `uname`        varchar(32) NOT NULL                                                       COMMENT '用户名',
  `email`        varchar(64)                                                                COMMENT '用户邮箱',
  `gender`       tinyint(4)                                                                 COMMENT '用户性别 1:male, 0:female',
  `mobile`       varchar(32)                                                                COMMENT '用户手机号',
  `pass`         varchar(32) NOT NULL                                                       COMMENT '用户密码',
  `status`       tinyint(4)  NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `cnos_task`;
CREATE TABLE `cnos_task` (
  `tid`          bigint(20)   NOT NULL AUTO_INCREMENT                                        COMMENT '任务ID',
  `uuid`         varchar(32)  NOT NULL                                                       COMMENT '用户UUID',

  `type`         varchar(32)  NOT NULL                                                       COMMENT '行程类型 自己定',
  `title`        varchar(128) NOT NULL                                                       COMMENT '行程标题',
  `start_time`   datetime     NOT NULL                                                       COMMENT '出发时间',
  `description`  mediumtext                                                                  COMMENT '行程描述',

  `start`        varchar(32)  NOT NULL                                                       COMMENT '出发地',
  `start_code`   varchar(32)  NOT NULL                                                       COMMENT '出发地邮编',
  `target`       varchar(32)  NOT NULL                                                       COMMENT '目的地',
  `target_code`  varchar(32)  NOT NULL                                                       COMMENT '目的地邮编',
  `through`      mediumtext                                                                  COMMENT '途经,多个之间可以用分号分割',
  `through_code` mediumtext                                                                  COMMENT '途经邮编,多个之间可以用分号分割',

  `status`       tinyint(4)   NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`tid`),
  INDEX (`uuid`),
  INDEX (`target`),
  INDEX (`start_time`),
  INDEX (`start`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

DROP TABLE IF EXISTS `cnos_partner`;
CREATE TABLE `cnos_partner` (
  `tid`          bigint(20)  NOT NULL                                                       COMMENT '任务ID',
  `uuid`         varchar(32) NOT NULL                                                       COMMENT '发布用户UUID',
  `partner_uuid` varchar(32) NOT NULL                                                       COMMENT '同行人用户UUID',

  `message`      mediumtext                                                                 COMMENT '同行留言',

  `status`       tinyint(4)  NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  KEY (`tid`),
  UNIQUE KEY (`tid`,`partner_uuid`),
  INDEX (`uuid`),
  INDEX (`partner_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='同行人表';

DROP TABLE IF EXISTS `cnos_message`;
CREATE TABLE `cnos_message` (
  `mid`          bigint(20)  NOT NULL AUTO_INCREMENT                                        COMMENT '消息ID',
  `tid`          bigint(20)  NOT NULL                                                       COMMENT '任务ID',
  `uuid`         varchar(32) NOT NULL                                                       COMMENT '发消息用户UUID',

  `message`      mediumtext  NOT NULL                                                       COMMENT '消息',

  `status`       tinyint(4)  NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`mid`),
  KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';
