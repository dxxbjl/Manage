/*
 Navicat Premium Data Transfer

 Source Server         : docker本机
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : crud

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 30/03/2021 13:32:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) COLLATE utf8_bin DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) COLLATE utf8_bin DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '菜单状态（Y显示 N隐藏）',
  `perms` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, '#', 'M', 'Y', '', '1', '2018-03-16 11:33:00', 'admin', '2021-03-28 20:25:29', '系统管理目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, '#', 'M', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, '/sysUser/listPage', 'C', 'Y', '1', '1', '2018-03-16 11:33:00', 'admin', '2021-03-29 12:51:20', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, '/sysRole/listPage', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, '/sysMenu/listPage', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (115, '系统接口', 3, 3, '/sysTool/swagger', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (116, '数据库监控', 3, 3, '/sysTool/druid', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统接口菜单');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `role_key` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ADMIN', '超级管理员', '', NULL, '', NULL);
INSERT INTO `sys_role` VALUES (2, '基本角色', 'USER', '基本角色', '', NULL, 'admin', '2021-03-26 22:44:48');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `FKf3mud4qoc7ayew8nml4plkevo` (`menu_id`),
  KEY `FKkeitxsgxwayackgqllio4ohn5` (`role_id`),
  CONSTRAINT `FKf3mud4qoc7ayew8nml4plkevo` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FKkeitxsgxwayackgqllio4ohn5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1, 100);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '账户',
  `pass_word` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `enabled` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否启用',
  `email` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) COLLATE utf8_bin DEFAULT '' COMMENT '手机号码',
  `sex` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '头像路径',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$XcigeMfToGQ2bqRToFtUi.sG1V.HhrJV6RBjji1yncXReSNNIPl1K', '1', '123', '123', '0', NULL, '', NULL, 'admin', '2021-03-29 10:26:05', '123');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
