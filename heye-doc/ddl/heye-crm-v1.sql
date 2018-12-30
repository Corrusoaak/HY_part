-- drop database heye_crm;
CREATE DATABASE IF NOT EXISTS heye;
use `heye`;

CREATE TABLE IF NOT EXISTS `hy_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_parent_id` int(11) NOT NULL DEFAULT 0,
  `range_type` tinyint(4) NOT NULL DEFAULT 0,
  `role_name` varchar(32) NOT NULL DEFAULT '',
  `role_desc` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `user_email` varchar(64) NOT NULL DEFAULT '',
  `user_phone_num` varchar(16) NOT NULL DEFAULT '',
  `user_telephone` varchar(16) NOT NULL DEFAULT '',
  `user_province` varchar(32) NOT NULL DEFAULT '',
  `user_city` varchar(32) NOT NULL DEFAULT '',
  `user_location` varchar(32) NOT NULL DEFAULT '',
  `user_detail_address` varchar(64) NOT NULL DEFAULT '',
  `user_zip_code` varchar(16) NOT NULL DEFAULT '',
  `user_identity` varchar(32) NOT NULL DEFAULT '',
  `user_picture` varchar(128) NOT NULL DEFAULT '',
  `user_birth_day` date NOT NULL DEFAULT "2017-01-01",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `user_name_index` (`user_name`),
  KEY `user_parent_name_index` (`user_parent_name`),
  KEY `user_location_index` (`user_location`),
  KEY `user_identity_index` (`user_identity`),
  KEY `user_name_passwd` (`user_name`,`user_password`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_customer` (
  `ctm_id` int(11) NOT NULL AUTO_INCREMENT,
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
  `ctm_location` varchar(32) NOT NULL DEFAULT '',
  `ctm_detail_address` varchar(64) NOT NULL DEFAULT '',
  `ctm_zip_code` varchar(16) NOT NULL DEFAULT '',
  `ctm_identity` varchar(32) NOT NULL DEFAULT '',
  `ctm_picture` varchar(128) NOT NULL DEFAULT '',
  `ctm_birth_day` date NOT NULL DEFAULT "2017-01-01",
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ctm_id`),
  KEY `ctm_name_index` (`ctm_name`),
  KEY `ctm_location_index` (`ctm_location`),
  KEY `ctm_identity_index` (`ctm_identity`),
  KEY `ctm_name_passwd` (`ctm_name`,`ctm_password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_right` (
  `right_id` int(11) NOT NULL AUTO_INCREMENT,
  `right_parent_id` int(11) NOT NULL DEFAULT 0,
  `right_name` varchar(32) NOT NULL DEFAULT '',
  `right_desc` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`right_id`),
  KEY `right_name` (`right_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_parent_id` int(11) NOT NULL DEFAULT 0,
  `group_name` varchar(32) NOT NULL DEFAULT '',
  `group_desc` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`),
  KEY `group_name` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `hy_group_role` (
  `gr_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`gr_id`),
  KEY `group_id` (`group_id`),
  FOREIGN KEY(`group_id`) REFERENCES hy_group(`group_id`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `hy_group_right` (
  `gri_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL DEFAULT 0,
  `right_id` int(11) NOT NULL DEFAULT 0,
  `right_type` tinyint(4) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`gri_id`),
  KEY `group_id` (`group_id`),
  KEY `group_right` (`group_id`, `right_id`),
  FOREIGN KEY(`group_id`) REFERENCES hy_group(`group_id`),
  FOREIGN KEY(`right_id`) REFERENCES hy_right(`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `hy_user_role` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ur_id`),
  KEY `user_id` (`user_id`),
  KEY `user_role` (`user_id`, `role_id`),
  FOREIGN KEY(`user_id`) REFERENCES hy_user(`user_id`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_user_right` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `right_id` int(11) NOT NULL DEFAULT 0,
  `right_type` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ur_id`),
  KEY `user_id` (`user_id`),
  KEY `user_right` (`user_id`,`right_id`),
  FOREIGN KEY(`user_id`) REFERENCES hy_user(`user_id`),
  FOREIGN KEY(`right_id`) REFERENCES hy_right(`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_user_group` (
  `ug_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `group_id` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ug_id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`group_id`),
  FOREIGN KEY(`user_id`) REFERENCES hy_user(`user_id`),
  FOREIGN KEY(`group_id`) REFERENCES hy_group(`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_role_right` (
  `rr_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `right_id` int(11) NOT NULL DEFAULT 0,
  `right_type` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rr_id`),
  KEY `role_id` (`role_id`),
  KEY `user_right` (`role_id`,`right_id`),
  FOREIGN KEY(`role_id`) REFERENCES hy_role(`role_id`),
  FOREIGN KEY(`right_id`) REFERENCES hy_right(`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_store` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_manager_id` int(11) NOT NULL DEFAULT 0,
  `store_phone_num` varchar(16) NOT NULL DEFAULT '',
  `store_telephone` varchar(16) NOT NULL DEFAULT '',
  `store_name` varchar(64) NOT NULL DEFAULT '',
  `store_position` varchar(128) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`store_id`),
  KEY `store_manager_id` (`store_manager_id`),
  FOREIGN KEY(`store_manager_id`) REFERENCES hy_user(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  --`store_id` int(11) NOT NULL DEFAULT 0,
  -- KEY `store_id` (`store_id`),
CREATE TABLE IF NOT EXISTS `hy_product` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(64) NOT NULL DEFAULT '',
  `pro_model` varchar(64) NOT NULL DEFAULT '',
  `pro_price` bigint(20) NOT NULL DEFAULT 0,
  `pro_bar_code` bigint(20) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pro_id`),
  KEY `pro_name` (`pro_name`),
  FOREIGN KEY(`store_id`) REFERENCES hy_store(`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `hy_consume` (
  `csm_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctm_id` int(11) NOT NULL DEFAULT 0,
  `pro_id` int(11) NOT NULL DEFAULT 0,
  `csm_money` bigint(20) NOT NULL DEFAULT 0,
  `csm_at` timestamp NOT NULL DEFAULT "2017-01-01",
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`csm_id`),
  KEY `ctm_id` (`ctm_id`),
  FOREIGN KEY(`ctm_id`) REFERENCES hy_customer(`ctm_id`),
  FOREIGN KEY(`pro_id`) REFERENCES hy_product(`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into hy_user(user_name, user_password) values("admin", "admin");
insert into hy_customer(ctm_name, ctm_manager_name, ctm_password, created_at) values("admin", "admin", "lishuming", "2018-04-05");
insert into hy_role(role_name, role_desc) values('test', 'test');
insert into hy_group(group_name, group_desc) values('test', 'test');
insert into hy_group_role(group_id, role_id) values(1, 1);

-- 短信