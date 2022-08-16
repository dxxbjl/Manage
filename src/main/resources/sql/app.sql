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

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_ad
-- ----------------------------
DROP TABLE IF EXISTS `app_ad`;
CREATE TABLE `app_ad`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `ad_title` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '广告标题',
  `ad_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告宣传图片',
  `ad_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '活动内容',
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
INSERT INTO `app_ad` VALUES (18, '测试轮播图', 'http://127.0.0.1:9000/crud/jflM9PZAnU.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20220814%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220814T153015Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=49218512c3d4676e5b03e61f6099f160c86458f3167002de1f0deb5f6996b001', '', 'Y', 'admin', '2022-08-14 23:26:30', 'admin', '2022-08-14 23:30:16', 0, '');
INSERT INTO `app_ad` VALUES (19, '测试轮播图', 'http://127.0.0.1:9000/crud/DdXfdqoCqC.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20220814%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220814T153019Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=556153ec74386c22774a040f29da2e383e90c26555bc41ee738be2b97acd2aef', '', 'Y', 'admin', '2022-08-14 23:26:33', 'admin', '2022-08-14 23:30:20', 0, '');

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
INSERT INTO `app_oauth` VALUES (3, 'ofB8h5Y_jLacnUYkjj_XLI-EEqlg', 'wx', 3);

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户头像图片',
  `gender` int(0) NULL DEFAULT 1 COMMENT '性别：1时是男性，值为2时是女性，值为0时是未知',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0 可用, 1 禁用, 2 注销',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES (3, '', '', 1, NULL, NULL, NULL, 0, '2022-08-14 23:57:59');

SET FOREIGN_KEY_CHECKS = 1;
