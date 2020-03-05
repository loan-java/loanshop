SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS  `app_switch`;
CREATE TABLE `app_switch` (
  `id` bigint(20) DEFAULT NULL COMMENT '主键',
  `app_package_name` varchar(64) DEFAULT NULL COMMENT '应用包名',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `version` varchar(64) DEFAULT NULL COMMENT '版本号',
  `status` tinyint(4) DEFAULT NULL COMMENT '是否开启0：关闭，1：开启',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `bank`;
CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `applyUrl` varchar(1023) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `applyNum` int(11) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `bankcard`;
CREATE TABLE `bankcard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `bankId` int(11) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `goUrl` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `bankinfo`;
CREATE TABLE `bankinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(60) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `bankId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bankId` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `banner`;
CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `goUrl` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `seq` int(1) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `pos` int(1) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `loan_id` bigint(20) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `channel_manage`;
CREATE TABLE `channel_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channelUrl` varchar(128) DEFAULT NULL,
  `channelCode` varchar(32) DEFAULT NULL,
  `channelName` varchar(63) DEFAULT NULL,
  `state` int(1) DEFAULT '0',
  `remark` varchar(128) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `checkRegisterUrl` varchar(255) DEFAULT NULL,
  `deductStatus` tinyint(4) DEFAULT '0' COMMENT '是否扣量，0不扣量，1扣量',
  `deductBase` int(11) DEFAULT '100' COMMENT '基数',
  `deductRatio` decimal(10,5) DEFAULT NULL COMMENT '比例',
  `deductDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `channelCode` (`channelCode`),
  UNIQUE KEY `channel_manage_channelCode_unique` (`channelCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `history_log_channel`;
CREATE TABLE `history_log_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel` varchar(63) DEFAULT NULL,
  `clickNum` int(11) DEFAULT NULL,
  `regNum` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `app_open_num` int(11) DEFAULT NULL,
  `clickdownnum` int(11) DEFAULT NULL,
  `uv_num` int(11) DEFAULT NULL,
  `loan_uv_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `hotloan`;
CREATE TABLE `hotloan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `loanIds` varchar(255) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `lean_push`;
CREATE TABLE `lean_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `alert` varchar(255) DEFAULT NULL,
  `openUrl` varchar(255) DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL,
  `sentNum` int(11) DEFAULT NULL,
  `failedNum` int(11) DEFAULT NULL,
  `openNum` int(11) DEFAULT NULL,
  `sound` varchar(63) DEFAULT NULL,
  `appId` varchar(255) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `loan`;
CREATE TABLE `loan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `state` int(1) NOT NULL DEFAULT '1',
  `tags` varchar(127) DEFAULT NULL COMMENT 'HIGH：贷款额度高，LOW：贷款利率低，FAST：放款速度快',
  `minLoan` int(11) DEFAULT NULL,
  `maxLoan` int(11) DEFAULT NULL,
  `rateRange` varchar(127) DEFAULT NULL,
  `periodRange` varchar(31) DEFAULT NULL,
  `applyUrl` varchar(1023) DEFAULT NULL,
  `applyNum` int(11) DEFAULT NULL,
  `badge` varchar(15) DEFAULT NULL COMMENT 'RECOMMENDED：爆款，HOT：推荐，OPTIMIZATION：优选',
  `applyUrl2` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `applyInfo` varchar(2047) DEFAULT '申请条件\n1、  \n\n申请材料\n1、  \n\n审核说明\n审核方式：\n放款时间：\n还款方式：\n',
  `logo` varchar(255) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `weights` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '类型：1,2,3,4',
  `managelink` text,
  `manageaccount` varchar(32) DEFAULT NULL,
  `managepassword` varchar(32) DEFAULT NULL,
  `market` varchar(32) DEFAULT NULL,
  `maxuv` varchar(32) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `big_type_position` int(11) DEFAULT NULL,
  `big_type` int(11) DEFAULT NULL,
  `type_tag` varchar(255) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  `settlement_type` varchar(45) DEFAULT NULL,
  `unit_price` varchar(45) DEFAULT NULL,
  `conversion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `loan_banner`;
CREATE TABLE `loan_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `os` tinyint(4) NOT NULL COMMENT '0:IOS,1:android,2:h5',
  `type` tinyint(4) DEFAULT '0' COMMENT '0:banner',
  `name` varchar(32) DEFAULT NULL,
  `img_path` varchar(256) DEFAULT NULL,
  `img_url` varchar(256) DEFAULT NULL,
  `state` tinyint(4) DEFAULT '0' COMMENT '0:在线，1：离线',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_app_login`;
CREATE TABLE `log_app_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel` varchar(256) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `type` varchar(256) DEFAULT NULL,
  `mac` varchar(256) DEFAULT NULL,
  `createDat` datetime DEFAULT NULL,
  `updateDat` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_aso`;
CREATE TABLE `log_aso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idfa` varchar(128) DEFAULT NULL,
  `appId` varchar(128) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `device` varchar(128) DEFAULT NULL,
  `os` varchar(64) DEFAULT NULL,
  `keyword` varchar(256) DEFAULT NULL,
  `channel` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_channel`;
CREATE TABLE `log_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel` varchar(63) DEFAULT NULL,
  `clickNum` int(11) DEFAULT NULL,
  `regNum` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  `app_open_num` int(11) DEFAULT NULL,
  `clickdownnum` int(11) DEFAULT NULL,
  `uv_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `log_channel_today_vist`;
CREATE TABLE `log_channel_today_vist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel` varchar(256) DEFAULT NULL,
  `pv` int(11) DEFAULT NULL,
  `uv` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_channel_visit`;
CREATE TABLE `log_channel_visit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel` varchar(64) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_loan_vist`;
CREATE TABLE `log_loan_vist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loan_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ip` varchar(256) DEFAULT NULL,
  `channel` varchar(64) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `log_visit`;
CREATE TABLE `log_visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel` varchar(63) DEFAULT NULL,
  `deviceId` int(11) DEFAULT NULL,
  `dayTimes` int(3) DEFAULT '1',
  `mobile` varchar(32) DEFAULT NULL,
  `loanId` int(3) DEFAULT NULL,
  `ip` varchar(31) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `log_visit_source`;
CREATE TABLE `log_visit_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(32) DEFAULT NULL,
  `channel` varchar(32) DEFAULT NULL,
  `app_name` text,
  `app_version` text,
  `app_code_name` text,
  `user_agent` text,
  `referrer` text,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `logdevice`;
CREATE TABLE `logdevice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `bundle_id` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `logloan`;
CREATE TABLE `logloan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `relId` int(11) DEFAULT NULL,
  `refer` varchar(255) DEFAULT NULL,
  `today_pv` int(11) DEFAULT NULL,
  `pv` int(11) DEFAULT '1',
  `today_uv` int(11) DEFAULT NULL,
  `uv` int(11) DEFAULT '1',
  `note` varchar(255) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `logroute`;
CREATE TABLE `logroute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) DEFAULT NULL,
  `path` varchar(127) DEFAULT NULL,
  `prePath` varchar(127) DEFAULT NULL,
  `query` varchar(1023) DEFAULT NULL,
  `deviceId` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `seqId` int(11) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `loguploadimg`;
CREATE TABLE `loguploadimg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `dest` varchar(255) DEFAULT NULL,
  `originalname` varchar(255) DEFAULT NULL,
  `width` int(6) DEFAULT NULL,
  `height` int(6) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `logupsert`;
CREATE TABLE `logupsert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tabName` varchar(63) DEFAULT NULL,
  `formData` text,
  `editId` int(11) DEFAULT NULL,
  `byUid` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `loguser`;
CREATE TABLE `loguser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(15) DEFAULT NULL,
  `msg` varchar(63) DEFAULT NULL,
  `deviceId` int(11) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `m_log_device`;
CREATE TABLE `m_log_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(5) DEFAULT NULL,
  `uuid` varchar(63) DEFAULT NULL,
  `ua` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `m_user_app_account`;
CREATE TABLE `m_user_app_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(63) DEFAULT NULL,
  `team_name` varchar(255) DEFAULT NULL,
  `team_id` varchar(63) DEFAULT NULL,
  `p8_file` varchar(255) DEFAULT NULL,
  `key_id` varchar(63) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `market`;
CREATE TABLE `market` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `market_name` varchar(64) DEFAULT NULL,
  `channel_code` varchar(32) DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='定时任务';

DROP TABLE IF EXISTS  `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

DROP TABLE IF EXISTS  `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

DROP TABLE IF EXISTS  `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='部门管理';

DROP TABLE IF EXISTS  `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

DROP TABLE IF EXISTS  `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统日志';

DROP TABLE IF EXISTS  `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

DROP TABLE IF EXISTS  `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文件上传';

DROP TABLE IF EXISTS  `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道ID',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色';

DROP TABLE IF EXISTS  `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

DROP TABLE IF EXISTS  `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

DROP TABLE IF EXISTS  `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `channel_id` bigint(20) DEFAULT NULL,
  `loan_ids` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统用户';

DROP TABLE IF EXISTS  `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

DROP TABLE IF EXISTS  `t_note`;
CREATE TABLE `t_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `goUrl` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `tb_token`;
CREATE TABLE `tb_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

DROP TABLE IF EXISTS  `tb_version_config`;
CREATE TABLE `tb_version_config` (
  `id` bigint(20) DEFAULT NULL,
  `channel` varchar(32) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '1:iod,2:android',
  `version` varchar(32) DEFAULT NULL,
  `content` text,
  `is_force` tinyint(4) DEFAULT NULL,
  `link` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(15) DEFAULT NULL,
  `vcode` varchar(15) DEFAULT NULL,
  `vtime` bigint(14) DEFAULT NULL,
  `psw` varchar(255) DEFAULT NULL,
  `nickname` varchar(63) DEFAULT NULL,
  `email` varchar(127) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `wx_id` varchar(255) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `ip` varchar(128) DEFAULT NULL,
  `last_login_ip` varchar(128) DEFAULT NULL,
  `last_login_time` timestamp NULL DEFAULT NULL,
  `last_login_os` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `userapp`;
CREATE TABLE `userapp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(31) DEFAULT NULL,
  `state` int(1) DEFAULT '0',
  `bundle_id` varchar(63) DEFAULT NULL,
  `app_ver` varchar(15) DEFAULT NULL,
  `build_ver` int(6) DEFAULT NULL,
  `init_url` varchar(255) DEFAULT NULL,
  `h5_link` varchar(255) DEFAULT NULL,
  `app_account_id` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

