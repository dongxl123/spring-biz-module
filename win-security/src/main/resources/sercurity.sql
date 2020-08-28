/*
 Navicat Premium Data Transfer

 Source Server         : test_baoxian
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : testwinbx.mysql.rds.aliyuncs.com:3306
 Source Schema         : security_center

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 27/08/2020 18:27:06
*/
-- ----------------------------
-- Table structure for application
-- ----------------------------
CREATE TABLE `application`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编码',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用描述',
  `icon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用图标',
  `status` int(5) NOT NULL DEFAULT 1 COMMENT '状态，0:停用 , 1:开启',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app_name`(`app_name`) USING BTREE,
  INDEX `idx_app_code`(`app_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用注册表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
CREATE TABLE `resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '编码',
  `global_code` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '全局编码, 格式: 系统code.菜单code.thisCode',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  `ajax_urls` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端接口地址，支持多个，中间用,隔开',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源介绍',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源图标',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '父级资源id',
  `seq` bigint(20) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态，0:无效 , 1:有效',
  `resource_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '资源类别, 0:无特别作用，1:菜单，2:其他',
  `belong_roles` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属于哪些角色拥有，中间用,隔开， NULL不限制',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `ext_attribute` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展属性',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_value`(`value`) USING BTREE,
  INDEX `idx_pid_code`(`pid`, `code`) USING BTREE,
  INDEX `idx_seq`(`seq`) USING BTREE,
  INDEX `idx_app_code`(`app_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `seq` bigint(20) NOT NULL DEFAULT 0 COMMENT '排序号',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态，0:无效 , 1:有效',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'entity间有继承关系时，hibernate需要的type来区分',
  `ext_attribute` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展属性',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_seq`(`seq`) USING BTREE,
  INDEX `idx_app_code`(`app_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
CREATE TABLE `role_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_resource_ids`(`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
CREATE TABLE `system_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆名',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `opt_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `client_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端ip',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app_code`(`app_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
CREATE TABLE `system_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `app_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编码',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆名',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态，0:无效 , 1:有效',
  `super_admin_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是超级管理员',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'entity间有继承关系时，hibernate需要的type来区分',
  `ext_attribute` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展属性',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_mobile`(`mobile`) USING BTREE,
  INDEX `idx_user_name`(`user_name`) USING BTREE,
  INDEX `idx_app_code`(`app_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_role_ids`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Compact;
