/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50634
Source Host           : testwinbx.mysql.rds.aliyuncs.com:3306
Source Database       : citymanager

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-11-16 14:55:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
CREATE TABLE `{prefix}_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `code` varchar(64) DEFAULT '' COMMENT '编码',
  `global_code` varchar(1024) DEFAULT '' COMMENT '全局编码, 格式: 系统code.菜单code.thisCode',
  `value` varchar(100) DEFAULT NULL COMMENT '资源路径',
  `ajax_urls` varchar(1000) DEFAULT NULL COMMENT '后端接口地址，支持多个，中间用,隔开',
  `description` varchar(255) DEFAULT NULL COMMENT '资源介绍',
  `icon` varchar(32) DEFAULT NULL COMMENT '资源图标',
  `pid` bigint(20) DEFAULT '0' COMMENT '父级资源id',
  `seq` bigint(20) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态，0:无效 , 1:有效',
  `resource_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '资源类别, 0:无特别作用，1:菜单，2:其他',
  `belong_roles` varchar(255) DEFAULT NULL COMMENT '属于哪些角色拥有，中间用,隔开， NULL不限制',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_value` (`value`),
  KEY `idx_pid_code` (`pid`,`code`) USING BTREE,
  KEY `idx_seq` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源';

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `{prefix}_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `seq` bigint(20) NOT NULL DEFAULT '0' COMMENT '排序号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态，0:无效 , 1:有效',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dtype` varchar(255) DEFAULT '' COMMENT 'entity间有继承关系时，hibernate需要的type来区分',
  PRIMARY KEY (`id`),
  KEY `idx_seq` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
CREATE TABLE `{prefix}_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`),
  KEY `idx_role_resource_ids` (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `{prefix}_sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登陆名',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `opt_content` text COMMENT '内容',
  `client_ip` varchar(255) DEFAULT NULL COMMENT '客户端ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `{prefix}_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_name` varchar(64) NOT NULL COMMENT '登陆名',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态，0:无效 , 1:有效',
  `super_admin_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是超级管理员',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dtype` varchar(255) DEFAULT '' COMMENT 'entity间有继承关系时，hibernate需要的type来区分',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `{prefix}_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `idx_user_role_ids` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';
