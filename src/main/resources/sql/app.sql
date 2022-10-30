/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : crud

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 16/08/2022 15:36:34
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_ad
-- ----------------------------
DROP TABLE IF EXISTS `app_ad`;
CREATE TABLE `app_ad`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `ad_title` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '广告标题',
  `ad_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告宣传图片',
  `ad_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '活动内容',
  `enabled` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否启用 Y 启用 N 禁用',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` int(0) NULL DEFAULT 0 COMMENT '逻辑删除 0 否、1 是',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_ad
-- ----------------------------

-- ----------------------------
-- Table structure for app_oauth
-- ----------------------------
DROP TABLE IF EXISTS `app_oauth`;
CREATE TABLE `app_oauth`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_secret` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '秘钥',
  `app_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型：wx微信、phone手机、password密码',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(63) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '用户头像图片',
  `gender` int(0) NULL DEFAULT 1 COMMENT '性别：1时是男性，值为2时是女性，值为0时是未知',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0 可用, 1 禁用, 2 注销',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_user
-- ----------------------------

-- ----------------------------
-- Table structure for app_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `app_sms_code`;
CREATE TABLE `app_sms_code`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
    `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '验证码',
    `dead_line` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
    `usable` int DEFAULT '1' COMMENT '是否有效，1-无效，2-有效',
    `sended` int DEFAULT '1' COMMENT '是否已发送，1-未发送，2-已发送',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '短信创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '短信验证码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_sms_code
-- ----------------------------

-- ----------------------------
-- Table structure for app_notice
-- ----------------------------
DROP TABLE IF EXISTS `app_notice`;
CREATE TABLE `app_notice`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标题',
    `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公告状态（0正常 1关闭）',
    `notice_content` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '内容',
    `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `deleted` int(0) NULL DEFAULT 0 COMMENT '逻辑删除 0 否、1 是',
    `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_notice
-- ----------------------------

-- ----------------------------
-- Table structure for app_category
-- ----------------------------
DROP TABLE IF EXISTS `app_category`;
CREATE TABLE `app_category`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
    `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
    `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父分类ID',
    `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `deleted` int(0) NULL DEFAULT 0 COMMENT '逻辑删除 0 否、1 是',
    `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_category
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
