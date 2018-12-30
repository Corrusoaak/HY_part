-- drop database heye_crm;
CREATE DATABASE IF NOT EXISTS heye;
use `heye`;

-- 管理员权限表
CREATE TABLE IF NOT EXISTS `hy_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_parent_id` int(11) NOT NULL DEFAULT 0,
  `role_range_type` tinyint(4) NOT NULL DEFAULT 0,
  `role_state` tinyint(4) NOT NULL DEFAULT 0,
  `role_name` varchar(32) NOT NULL DEFAULT '',
  `role_desc` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 管理员表
CREATE TABLE IF NOT EXISTS `hy_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `user_parent_id` int(11) NOT NULL DEFAULT 0,
  `user_parent_name` varchar(32) NOT NULL DEFAULT '',
  `user_account_name` varchar(32) NOT NULL DEFAULT '',
  `user_name` varchar(32) NOT NULL DEFAULT '',
  `user_password` varchar(32) NOT NULL DEFAULT '',
  `user_sex` tinyint(4) NOT NULL DEFAULT '0',
  `user_state` tinyint(4) NOT NULL DEFAULT '0',
  `user_range_type` tinyint(4) NOT NULL DEFAULT '0',
  `user_email` varchar(64) NOT NULL DEFAULT '',
  `user_phone_num` varchar(16) NOT NULL DEFAULT '',
  `user_telephone` varchar(16) NOT NULL DEFAULT '',
  `user_province` varchar(32) NOT NULL DEFAULT '',
  `user_city` varchar(32) NOT NULL DEFAULT '',
  `user_location` varchar(32) NOT NULL DEFAULT '',
  `user_detail_address` varchar(64) NOT NULL DEFAULT '',
  `user_zip_code` varchar(16) NOT NULL DEFAULT '',
  `user_identity` varchar(32) NOT NULL DEFAULT '',
  `user_picture` varchar(1024) NOT NULL DEFAULT '',
  `user_birth_day` date NOT NULL DEFAULT "2017-01-01",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `user_name_index` (`user_name`),
  KEY `user_parent_name_index` (`user_parent_name`),
  KEY `user_province_index` (`user_province`),
  KEY `user_city_index` (`user_city`),
  KEY `user_location_index` (`user_location`),
  KEY `user_identity_index` (`user_identity`),
  KEY `user_name_passwd` (`user_name`,`user_password`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 店铺表
CREATE TABLE IF NOT EXISTS `hy_store` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_manager_id` int(11) NOT NULL DEFAULT 0,
  `store_phone_num` varchar(16) NOT NULL DEFAULT '',
  `store_telephone` varchar(16) NOT NULL DEFAULT '',
  `store_name` varchar(64) NOT NULL DEFAULT '',
  `store_state` tinyint(4) NOT NULL DEFAULT 0,
  `store_province` varchar(32) NOT NULL DEFAULT '',
  `store_city` varchar(32) NOT NULL DEFAULT '',
  `store_position` varchar(32) NOT NULL DEFAULT '',
  `store_address` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `g_latitude` geometry,
  `geohash` varchar(128) GENERATED ALWAYS AS (st_geohash(`geometry`,6)) VIRTUAL,
  PRIMARY KEY (`store_id`),
  KEY `store_manager_id` (`store_manager_id`),
  FOREIGN KEY(`store_manager_id`) REFERENCES hy_user(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户表
CREATE TABLE IF NOT EXISTS `hy_customer` (
  `ctm_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL DEFAULT 0,
  `ctm_account_name` varchar(32) NOT NULL DEFAULT '',
  `ctm_name` varchar(32) NOT NULL DEFAULT '',
  `ctm_password` varchar(32) NOT NULL DEFAULT '',
  `ctm_sex` tinyint(4) NOT NULL DEFAULT '0',
  `ctm_state` tinyint(4) NOT NULL DEFAULT '0',
  `ctm_email` varchar(64) NOT NULL DEFAULT '',
  `ctm_phone_num` varchar(16) NOT NULL DEFAULT '',
  `ctm_telephone` varchar(16) NOT NULL DEFAULT '',
  `ctm_manager_name` varchar(32) NOT NULL DEFAULT '',
  `ctm_province` varchar(32) NOT NULL DEFAULT '',
  `ctm_city` varchar(32) NOT NULL DEFAULT '',
  `ctm_location` varchar(32) NOT NULL DEFAULT '',
  `ctm_detail_address` varchar(64) NOT NULL DEFAULT '',
  `ctm_zip_code` varchar(16) NOT NULL DEFAULT '',
  `ctm_identity` varchar(32) NOT NULL DEFAULT '',
  `ctm_picture` varchar(1024) DEFAULT '',
  `ctm_birth_day` date NOT NULL DEFAULT "2017-01-01",
  `g_latitude` geometry,
  `geohash` varchar(128) GENERATED ALWAYS AS (st_geohash(`geometry`,6)) VIRTUAL,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ctm_id`),
  UNIQUE KEY `ctm_identity`,
  KEY `ctm_name_index` (`ctm_name`),
  KEY `ctm_province_index` (`ctm_province`),
  KEY `ctm_city_index` (`ctm_city`),
  KEY `ctm_location_index` (`ctm_location`),
  KEY `ctm_identity_index` (`ctm_identity`),
  KEY `ctm_name_passwd` (`ctm_name`,`ctm_password`),
  KEY `idx_nodes_geohash` (`geohash`),
  FOREIGN KEY(`store_id`) REFERENCES hy_store(`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 微信小程序关联表
CREATE TABLE IF NOT EXISTS `hy_weixin` (
  `weixin_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT '0',
  `open_id` varchar(32) NOT NULL DEFAULT "",
  `session_key` varchar(32) NOT NULL DEFAULT "",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`weixin_id`),
  KEY `ctm_id` (`ctm_id`),
  KEY `open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 权限表
CREATE TABLE IF NOT EXISTS `hy_right` (
  `right_id` int(11) NOT NULL AUTO_INCREMENT,
  `right_parent_id` int(11) NOT NULL DEFAULT 0,
  `right_state` tinyint(4) NOT NULL DEFAULT 0,
  `right_name` varchar(32) NOT NULL DEFAULT '',
  `right_desc` varchar(64) NOT NULL DEFAULT '',
  `right_module` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`right_id`),
  KEY `right_name` (`right_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色权限表
CREATE TABLE IF NOT EXISTS `hy_role_right` (
  `rr_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `right_id` int(11) NOT NULL DEFAULT 0,
  `right_type` tinyint(4) NOT NULL DEFAULT 0,
  `rr_state` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rr_id`),
  KEY `role_id` (`role_id`),
  KEY `user_right` (`role_id`,`right_id`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`),
  FOREIGN KEY(`right_id`) REFERENCES hy_right(`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 商品表
CREATE TABLE `hy_product` (
   `pro_id` int(11) NOT NULL AUTO_INCREMENT,
   `pro_name` varchar(64) NOT NULL DEFAULT '',
   `pro_model` varchar(64) NOT NULL DEFAULT '',
   `pro_desc` varchar(64) NOT NULL DEFAULT '',
   `pro_pictures` varchar(4096) NOT NULL DEFAULT '',
   `pro_bar_code` varchar(32) NOT NULL DEFAULT '',
   `pro_price` bigint(20) NOT NULL DEFAULT 0,
   `pro_state` tinyint(4) NOT NULL DEFAULT 0,
   `pro_display` tinyint(4) NOT NULL DEFAULT 0,
   `pro_unit_price` bigint(20) NOT NULL DEFAULT 0,
   `pro_type` varchar(64) NOT NULL DEFAULT '',
   `created_at` timestamp NOT NULL DEFAULT '2017-01-01 00:00:00',
   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY(`pro_id`),
   KEY `pro_name` (`pro_name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 投诉表
CREATE TABLE IF NOT EXISTS `hy_advise` (
  `advise_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT 0,
  `advise_type` tinyint(4) NOT NULL DEFAULT 0,
  `advise_state` tinyint(4) NOT NULL DEFAULT 0,
  `advise_desc` varchar(768) NOT NULL DEFAULT '',
  `advise_pictures` varchar(3072) NOT NULL DEFAULT '',
  `advise_replies` varchar(256) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`advise_id`),
  KEY `ctm_id` (`ctm_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 消费记录表
CREATE TABLE IF NOT EXISTS `hy_consume` (
  `csm_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT 0,
  `pro_id` int(11) NOT NULL DEFAULT 0,
  `csm_money` bigint(20) NOT NULL DEFAULT 0,
  `cms_state` tinyint(4) NOT NULL DEFAULT 0,
  `csm_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`csm_id`),
  KEY `ctm_id` (`ctm_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`),
  FOREIGN KEY(`pro_id`) REFERENCES hy_product(`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 售后表
CREATE TABLE IF NOT EXISTS `hy_track` (
  `track_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT 0,
  `csm_id` int(11) NOT NULL DEFAULT 0,
  `pro_id` int(11) NOT NULL DEFAULT 0,
  `track_state` tinyint(4) NOT NULL DEFAULT 0,
  `track_type` tinyint(4) NOT NULL DEFAULT 0,
  `track_pictures` varchar(3072) NOT NULL DEFAULT '',
  `track_desc` varchar(768) NOT NULL DEFAULT '',
  `track_replies` varchar(768) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`track_id`),
  KEY `ctm_id` (`ctm_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`),
  FOREIGN KEY(`pro_id`) REFERENCES hy_product(`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 回访表
CREATE TABLE IF NOT EXISTS `hy_revisit` (
  `revisit_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT 0,
  `pro_id` int(11) NOT NULL DEFAULT 0,
  `revisitd_state` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`revisit_id`),
  KEY `ctm_id` (`ctm_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`),
  FOREIGN KEY(`pro_id`) REFERENCES hy_product(`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_location` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `loc_state` tinyint(4) NOT NULL DEFAULT 0,
  `loc_province` varchar(16) NOT NULL DEFAULT '',
  `loc_city` varchar(64) NOT NULL DEFAULT '',
  `loc_location` varchar(128) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`loc_id`),
  KEY `loc_province` (`loc_province`),
  KEY `loc_city` (`loc_city`),
  KEY `loc_location` (`loc_location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_care` (
  `care_id` int(11) NOT NULL AUTO_INCREMENT,
  `send_state` tinyint(4) NOT NULL DEFAULT 0,
  `verify_state` tinyint(4) NOT NULL DEFAULT 0,
  `switch_state` tinyint(4) NOT NULL DEFAULT 0,
  `ctm_range_type` tinyint(4) NOT NULL DEFAULT 0,
  `ctm_sex` tinyint(4) NOT NULL DEFAULT 0,
  `fest_name` varchar(32) NOT NULL DEFAULT '',
  `fest_date` date,
  `send_time` varchar(16) NOT NULL DEFAULT '',
  `send_desc` varchar(256) NOT NULL DEFAULT '',
  `age_range` varchar(16) NOT NULL DEFAULT '',
  `consume_range` varchar(16) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`care_id`),
  KEY `send_state` (`send_state`),
  KEY `verify_state` (`verify_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 分析表
CREATE TABLE IF NOT EXISTS `hy_analyze` (
  `ana_id` int(11) NOT NULL AUTO_INCREMENT,
  `agg_level` tinyint(4) NOT NULL DEFAULT 0,
  `ana_type` int(11) NOT NULL DEFAULT 0,
  `ana_name` varchar(32) NOT NULL DEFAULT '',
  `label_name` varchar(32) NOT NULL DEFAULT '',
  `label_value` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ana_id`),
  KEY `created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 跟单表
CREATE TABLE IF NOT EXISTS `hy_follow` (
  `follow_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11),
  `follow_state` tinyint(4) NOT NULL DEFAULT 0,
  `order_id` varchar(64) NOT NULL DEFAULT "",
  `express_id` varchar(64) NOT NULL DEFAULT "",
  `express_company` varchar(64) NOT NULL DEFAULT "",
  `store_telephone` varchar(16) NOT NULL DEFAULT "",
  `store_manager` varchar(16) NOT NULL DEFAULT "",
  `store_detail_address` varchar(64) NOT NULL DEFAULT "",
  `deliver_house` varchar(64) NOT NULL DEFAULT "",
  `deliver_id` varchar(64) NOT NULL DEFAULT "",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`follow_id`),
  KEY `order_id` (`order_id`),
  KEY `express_id` (`express_id`),
  KEY `deliver_id` (`deliver_id`),
  KEY `store_telephone` (`store_telephone`),
  KEY `store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 回访问题表
CREATE TABLE IF NOT EXISTS `hy_revisit_question` (
  `quest_id` int(11) NOT NULL AUTO_INCREMENT,
  `quest_state` tinyint(4) NOT NULL DEFAULT 0,
  `quest_type` tinyint(4) NOT NULL DEFAULT 0,
  `quest_desc` varchar(256) NOT NULL DEFAULT "",
  `quest_option` varchar(256) NOT NULL DEFAULT "",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 回访问卷表
CREATE TABLE IF NOT EXISTS `hy_revisit_naire` (
  `naire_id` int(11) NOT NULL AUTO_INCREMENT,
  `naire_state` tinyint(4) NOT NULL DEFAULT 0,
  `naire_type` tinyint(4) NOT NULL DEFAULT 0,
  `naire_name` varchar(256) NOT NULL DEFAULT "",
  `naire_list` varchar(1024) NOT NULL DEFAULT "",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`naire_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 回访问卷、问题映射表
CREATE TABLE IF NOT EXISTS `hy_revisit_naire_quest` (
  `nq_id` int(11) NOT NULL AUTO_INCREMENT,
  `naire_id` int(11) NOT NULL,
  `quest_id` int(11) NOT NULL,
  `nq_state` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`nq_id`),
  FOREIGN KEY(`naire_id`) REFERENCES hy_revisit_naire(`naire_id`),
  FOREIGN KEY(`quest_id`) REFERENCES hy_revisit_question(`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 回访回复表
CREATE TABLE IF NOT EXISTS `hy_revisit_answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL,
  `naire_id` int(11) NOT NULL,
  `quest_id` int(11) NOT NULL,
  `answer_state` tinyint(4) NOT NULL DEFAULT 0,
  `answer_content` varchar(1024) NOT NULL DEFAULT "",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`answer_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`),
  FOREIGN KEY(`naire_id`) REFERENCES hy_revisit_naire(`naire_id`),
  FOREIGN KEY(`quest_id`) REFERENCES hy_revisit_question(`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert hy_care(fest_name, send_time) values("客户生日", "09:00");

insert into hy_role(role_id, role_parent_id, role_name, role_desc, role_state) values(1, 1, '超级管理员', '超级管理员', 1);
insert into hy_role(role_id, role_parent_id, role_name, role_desc, role_state) values(2, 1, '省经理', '', 1);
insert into hy_role(role_id, role_parent_id, role_name, role_desc, role_state) values(3, 2, '地区经理', '', 1);
insert into hy_role(role_id, role_parent_id, role_name, role_desc, role_state) values(4, 3, '区域经理', '', 1);
insert into hy_role(role_id, role_parent_id, role_name, role_desc, role_state) values(5, 4, '店长', '', 1);

insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(1, 'SUPER_RIGHT', 'SUPER_RIGHT', '', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(2, '客户管理模块', '客户管理权限', '查看管理客户信息', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(3, '客户分析模块', '客户分析权限', '查看客户分析', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(4, '客户回访模块', '客户回访权限', '查看客户回访', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(5, '售后记录模块', '售后记录权限', '回复、查看售后记录', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(6, '投诉建议模块', '投诉建议权限', '查看投诉建议', 1);
insert into hy_right(right_id, right_module, right_name, right_desc, right_state) values(7, '客户关怀模块', '客户关怀权限', '查看客户关怀', 1);

insert into hy_role_right(role_id, right_id) values(1, 1);

insert into hy_user(user_name, user_password, user_province, user_city, user_location, role_id, user_state) values("admin", "admin", '浙江', '杭州', '滨江区', 1, 1);

insert into hy_store(store_manager_id, store_phone_num, store_telephone, store_name, store_province, store_city, store_position, store_address, store_state) values(1, '18210287328', '18210287328', 'test', '浙江', '杭州', '滨江', 'xxx', 1);

insert into hy_customer(ctm_name, ctm_manager_name, ctm_password, store_id, created_at, ctm_state) values("admin", "admin", "lishuming", 1, "2018-04-05", 1);

insert into hy_product(pro_name, pro_model, pro_price, pro_bar_code, pro_state) values('test_pro', '1*2*3', 1000, 'xxxx', 1);

insert into hy_advise(ctm_id, advise_type, advise_state, advise_desc, created_at) values(1, 1, 1, '这个产品有点小瑕疵：使用不方便', '2018-05-03');

insert into hy_track(ctm_id, pro_id, track_type, track_state, track_desc, created_at) values(1, 1, 1, 1, '这个产品有点小瑕疵：使用不方便', '2018-05-03');
