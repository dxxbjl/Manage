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

 Date: 01/04/2021 11:27:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `package_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '包名称',
  `class_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '方法名称',
  `args_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '参数名',
  `args_value` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '参数值',
  `exception_name` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '异常类型',
  `err_msg` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `stack_trace` text COLLATE utf8_bin COMMENT '异常堆栈信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统日志';

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
) ENGINE=InnoDB AUTO_INCREMENT=3009 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, '#', 'M', 'Y', '', '1', '2018-03-16 11:33:00', 'admin', '2021-03-28 20:25:29', '系统管理目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, '#', 'M', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, '/sysUser/listPage', 'C', 'Y', '', '1', '2018-03-16 11:33:00', 'admin', '2021-03-29 12:51:20', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, '/sysRole/listPage', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, '/sysMenu/listPage', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (115, '系统接口', 3, 3, '/sysTool/swagger', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (116, '数据库监控', 3, 3, '/sysTool/druid', 'C', 'Y', '', '1', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (1005, '添加', 100, 1, '#', 'F', 'Y', 'user:add', 'admin', '2021-03-31 16:55:04', 'admin', '2021-03-31 16:57:49', '添加用户');
INSERT INTO `sys_menu` VALUES (1006, '编辑', 100, 2, '#', 'F', 'Y', 'user:edit', 'admin', '2021-03-31 17:01:35', 'admin', '2021-03-31 17:01:35', NULL);
INSERT INTO `sys_menu` VALUES (1007, '删除', 100, 3, '#', 'F', 'Y', 'user:del', 'admin', '2021-03-31 17:01:57', 'admin', '2021-03-31 17:01:57', NULL);
INSERT INTO `sys_menu` VALUES (2005, '添加', 101, 1, '#', 'F', 'Y', 'role:add', 'admin', '2021-03-31 16:55:04', 'admin', '2021-03-31 16:57:49', '添加用户');
INSERT INTO `sys_menu` VALUES (2006, '编辑', 101, 2, '#', 'F', 'Y', 'role:edit', 'admin', '2021-03-31 17:01:35', 'admin', '2021-03-31 17:01:35', NULL);
INSERT INTO `sys_menu` VALUES (2007, '删除', 101, 3, '#', 'F', 'Y', 'role:del', 'admin', '2021-03-31 17:01:57', 'admin', '2021-03-31 17:01:57', NULL);
INSERT INTO `sys_menu` VALUES (3005, '添加', 102, 1, '#', 'F', 'Y', 'menu:add', 'admin', '2021-03-31 16:55:04', 'admin', '2021-03-31 16:57:49', '添加用户');
INSERT INTO `sys_menu` VALUES (3006, '编辑', 102, 2, '#', 'F', 'Y', 'menu:edit', 'admin', '2021-03-31 17:01:35', 'admin', '2021-03-31 17:01:35', NULL);
INSERT INTO `sys_menu` VALUES (3007, '删除', 102, 3, '#', 'F', 'Y', 'menu:del', 'admin', '2021-03-31 17:01:57', 'admin', '2021-03-31 17:01:57', NULL);
INSERT INTO `sys_menu` VALUES (3008, '异常日志', 1, 4, '/sysLog/listPage', 'C', 'Y', 'log:list', 'admin', '2021-04-01 11:01:52', 'admin', '2021-04-01 11:02:02', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ADMIN', '超级管理员', '', NULL, 'admin', '2021-03-31 20:46:12');
INSERT INTO `sys_role` VALUES (2, '基本角色', 'USER', '基本角色', '', NULL, 'admin', '2021-03-31 20:46:57');
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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (142, 1, 100);
INSERT INTO `sys_role_menu` VALUES (143, 1, 101);
INSERT INTO `sys_role_menu` VALUES (144, 1, 102);
INSERT INTO `sys_role_menu` VALUES (145, 1, 115);
INSERT INTO `sys_role_menu` VALUES (146, 1, 116);
INSERT INTO `sys_role_menu` VALUES (147, 1, 1005);
INSERT INTO `sys_role_menu` VALUES (148, 1, 1006);
INSERT INTO `sys_role_menu` VALUES (149, 1, 1007);
INSERT INTO `sys_role_menu` VALUES (150, 1, 2005);
INSERT INTO `sys_role_menu` VALUES (151, 1, 2006);
INSERT INTO `sys_role_menu` VALUES (152, 1, 2007);
INSERT INTO `sys_role_menu` VALUES (153, 1, 3005);
INSERT INTO `sys_role_menu` VALUES (154, 1, 3006);
INSERT INTO `sys_role_menu` VALUES (155, 1, 3007);
INSERT INTO `sys_role_menu` VALUES (156, 1, 1);
INSERT INTO `sys_role_menu` VALUES (157, 1, 3);
INSERT INTO `sys_role_menu` VALUES (158, 2, 1);
INSERT INTO `sys_role_menu` VALUES (159, 2, 100);
INSERT INTO `sys_role_menu` VALUES (160, 2, 1005);
INSERT INTO `sys_role_menu` VALUES (161, 2, 1006);
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
  `avatar` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '头像路径',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$YajcgOCR3KE76BkuTVBTvuWOrmtzh0MyY87m7jAbA5idFYZ8XJQbO', '1', '616505453@qq.com', '17515087128', '0', 'https://platform-application.oss-cn-beijing.aliyuncs.com/img/def/1617164595541-821b4344-5d94-4b29.png', '', NULL, 'admin', '2021-03-31 22:06:15', '11111');
INSERT INTO `sys_user` VALUES (24, 'test', '$2a$10$Nc4UVI6CD5jJeovA/aNjHOXE..Gn6Tu3b4kjjqJdWku2Lnudvicyq', '1', '123', '123', '0', 'https://platform-application.oss-cn-beijing.aliyuncs.com/img/def/1617194953511-4fa23d1f-e5f7-4bd2.png', 'admin', '2021-03-30 20:25:07', 'admin', '2021-03-31 20:49:16', '123');
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (7, 1, 1);
INSERT INTO `sys_user_role` VALUES (9, 24, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
