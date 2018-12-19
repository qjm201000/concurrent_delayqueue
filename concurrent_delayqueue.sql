/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : concurrent

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-12-19 11:09:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '0' COMMENT '订单状态：0待支付1已支付-1取消2已超时',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('2', '2018-12-18 15:38:15', '2');
INSERT INTO `t_order` VALUES ('3', '2018-12-18 15:38:17', '2');
INSERT INTO `t_order` VALUES ('4', '2018-12-18 15:38:17', '2');
INSERT INTO `t_order` VALUES ('5', '2018-12-19 01:43:58', '-1');
INSERT INTO `t_order` VALUES ('6', '2018-12-19 01:44:01', '1');
