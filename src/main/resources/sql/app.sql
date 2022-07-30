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

 Date: 30/07/2022 22:30:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_oauth
-- ----------------------------
DROP TABLE IF EXISTS `app_oauth`;
CREATE TABLE `app_oauth`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'open_id',
  `app_type` int(0) NULL DEFAULT NULL COMMENT '类型 1 微信',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户头像图片',
  `gender` int(0) NULL DEFAULT 1 COMMENT '性别：1时是男性，值为2时是女性，值为0时是未知',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0 可用, 1 禁用, 2 注销',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
