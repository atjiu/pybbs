/*
 Navicat MySQL Data Transfer

 Source Server         : 45.62.98.174
 Source Server Version : 50541
 Source Host           : 45.62.98.174
 Source Database       : jfinalbbs

 Target Server Version : 50541
 File Encoding         : utf-8

 Date: 04/11/2015 15:24:14 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `mission`
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `score` int(11) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `notification`
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `read` int(11) DEFAULT NULL COMMENT '是否已读：0默认 1已读',
  `from_author_id` varchar(32) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  `tid` varchar(32) DEFAULT NULL,
  `rid` varchar(32) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `tid` varchar(32) DEFAULT NULL,
  `content` longtext,
  `in_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `quote` varchar(32) DEFAULT NULL COMMENT '0：未引用 非0：引用楼层id',
  `quote_content` longtext,
  `quote_author_nickname` varchar(50) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `topic`
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `title` varchar(255) DEFAULT NULL,
  `content` longtext,
  `in_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `view` int(11) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  `reposted` int(11) DEFAULT NULL COMMENT '1：转载 0：原创',
  `original_url` varchar(255) DEFAULT NULL COMMENT '原文连接',
  `tab` varchar(45) DEFAULT NULL,
  `top` int(11) DEFAULT NULL COMMENT '1置顶 0默认',
  `good` int(11) DEFAULT NULL COMMENT '1精华 0默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `thirdlogin_type` varchar(10) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `mission` date DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TOKEN_UNIQUE` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
