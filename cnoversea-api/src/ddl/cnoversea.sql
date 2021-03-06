
DROP DATABASE IF EXISTS `cnoversea_api`;
CREATE DATABASE `cnoversea_api`;

USE `cnoversea_api`;

DROP TABLE IF EXISTS `cnos_user`;
CREATE TABLE `cnos_user` (
  `uuid`         varchar(32) NOT NULL                                                       COMMENT '用户UUID',
  `uid`          varchar(32) NOT NULL                                                       COMMENT '用户登录ID',
  `uname`        varchar(32) NOT NULL                                                       COMMENT '用户名',
  `email`        varchar(64) NOT NULL                                                       COMMENT '用户邮箱',
  `gender`       tinyint(4)                                                                 COMMENT '用户性别 1:male, 0:female',
  `mobile`       varchar(32)                                                                COMMENT '用户手机号',
  `pass`         varchar(32) NOT NULL                                                       COMMENT '用户密码',
  `status`       tinyint(4)  NOT NULL DEFAULT '-1'                                          COMMENT '是否生效 1:生效 0:无效 -1:未激活',
  `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY (`uid`),
  UNIQUE KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `cnos_task`;
CREATE TABLE `cnos_task` (
  `tid`          bigint(20)   NOT NULL AUTO_INCREMENT                                        COMMENT '任务ID',
  `uuid`         varchar(32)  NOT NULL                                                       COMMENT '用户UUID',

  `type`         varchar(32)  NOT NULL                                                       COMMENT '行程安全类型 自己定',
  `traffic_type` varchar(32)  NOT NULL                                                       COMMENT '出行方式类型 自己定',
  `scale_limit`  tinyint(4)   NOT NULL DEFAULT 10                                            COMMENT '人数限制',
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

DROP TABLE IF EXISTS `cnos_postcode_lnglat_map`;
CREATE TABLE `cnos_postcode_lnglat_map` (
  `mid`          bigint(20)  NOT NULL AUTO_INCREMENT                                        COMMENT '映射 ID',
  `postcode`     varchar(32)  NOT NULL                                                      COMMENT '邮编',
  `longtitude`   varchar(32)  NOT NULL                                                      COMMENT '经度',
  `latitude`     varchar(32)  NOT NULL                                                      COMMENT '维度',

  `status`       tinyint(4)  NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`mid`),
  UNIQUE KEY (`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮编和经纬度映射表';

DROP TABLE IF EXISTS `cnos_news`;
CREATE TABLE `cnos_news` (
  `nid`          bigint(20)   NOT NULL AUTO_INCREMENT                                        COMMENT '新闻ID',

  `title`        varchar(128) NOT NULL                                                       COMMENT '标题',
  `author`       varchar(32)  NOT NULL                                                       COMMENT '作者',
  `location`     varchar(128) NOT NULL                                                       COMMENT '地点',
  `keywords`     varchar(256) NOT NULL                                                       COMMENT '关键字 分号分割',

  `content`      mediumtext                                                                  COMMENT '新闻内容',

  `status`       tinyint(4)   NOT NULL DEFAULT '1'                                           COMMENT '是否生效 1:生效 0:无效',
  `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间',
  `modify_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻表';
