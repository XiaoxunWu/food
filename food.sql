/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : food

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 02/01/2023 16:37:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_food
-- ----------------------------
DROP TABLE IF EXISTS `t_food`;
CREATE TABLE `t_food` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `food_name` varchar(500) DEFAULT NULL COMMENT '食品名称',
  `food_lan` varchar(500) DEFAULT NULL COMMENT '食品产地',
  `food_ting` varchar(500) DEFAULT NULL COMMENT '食品所属用户',
  `food_date` varchar(500) DEFAULT NULL COMMENT '食品生产日期开始时间',
  `food_long` varchar(500) DEFAULT NULL COMMENT '食品有效期',
  `food_price` varchar(500) DEFAULT NULL COMMENT '食品金额',
  `food_text` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='食物';

-- ----------------------------
-- Records of t_food
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_name` varchar(500) DEFAULT NULL COMMENT '标题',
  `notice_text` varchar(500) DEFAULT NULL COMMENT '内容',
  `notice_date` varchar(500) DEFAULT NULL COMMENT '提醒时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='公告';

-- ----------------------------
-- Records of t_notice
-- ----------------------------
BEGIN;
INSERT INTO `t_notice` VALUES (1, '食品过期提醒', '石榴5月份过期，请大家留意', '2023-01-02', '123');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(500) DEFAULT NULL COMMENT '用户名',
  `password` varchar(500) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(500) DEFAULT NULL COMMENT '姓名',
  `user_sex` varchar(500) DEFAULT NULL COMMENT '性别:男/女',
  `user_phone` varchar(500) DEFAULT NULL COMMENT '手机',
  `user_text` varchar(500) DEFAULT NULL COMMENT '备注',
  `user_type` varchar(500) DEFAULT NULL COMMENT '类型:管理员/普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, 'admin', '123456', '管理', '男', '13588888888', 'hello', 'admin');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`content` varchar(255) DEFAULT NULL,
`user_id` int(11) DEFAULT NULL,
`create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`food_name` varchar(255) DEFAULT NULL,
`status` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
ALTER TABLE t_user ADD user_id INT;
