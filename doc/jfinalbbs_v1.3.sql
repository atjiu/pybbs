# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.24)
# Database: jfinalbbs
# Generation Time: 2015-06-30 10:05:33 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '后台用户名',
  `password` varchar(45) NOT NULL COMMENT '后台密码（明文）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;

INSERT INTO `admin_user` (`id`, `username`, `password`)
VALUES
	(1,'admin','123456');

/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `code`;

CREATE TABLE `code` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '验证码',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `status` int(11) NOT NULL COMMENT '0未使用  1已使用',
  `type` varchar(45) NOT NULL COMMENT '验证码类型，比如：找回密码',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `target` varchar(255) NOT NULL COMMENT '发送目标，如邮箱等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '收藏话题id',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '友链名称',
  `url` varchar(255) NOT NULL COMMENT '友链地址',
  `display_index` int(11) NOT NULL COMMENT '友链排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;

INSERT INTO `link` (`id`, `name`, `url`, `display_index`)
VALUES
	(1,'JFinal社区','http://jfbbs.tomoya.cn/',1);

/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table mission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mission`;

CREATE TABLE `mission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `score` int(11) NOT NULL COMMENT '签到随机获得积分',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `day` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table notification
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL COMMENT '消息内容',
  `read` int(11) NOT NULL COMMENT '是否已读：0默认 1已读',
  `from_author_id` varchar(32) NOT NULL COMMENT '来源用户id',
  `author_id` varchar(32) NOT NULL COMMENT '目标用户id',
  `tid` varchar(32) DEFAULT NULL COMMENT '话题id',
  `rid` varchar(32) DEFAULT NULL COMMENT '回复id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `tid` varchar(32) NOT NULL COMMENT '话题id',
  `content` longtext NOT NULL COMMENT '回复内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `quote` varchar(32) NOT NULL COMMENT '0：未引用 非0：引用楼层id',
  `quote_content` longtext COMMENT '引用内容',
  `quote_author_nickname` varchar(50) DEFAULT NULL COMMENT '引用回复用户id',
  `author_id` varchar(32) NOT NULL COMMENT '当前回复用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table section
# ------------------------------------------------------------

DROP TABLE IF EXISTS `section`;

CREATE TABLE `section` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '版块名称',
  `tab` varchar(45) NOT NULL COMMENT '版块标签',
  `show_status` int(11) NOT NULL DEFAULT '1' COMMENT '是否显示，0不显示1显示',
  `display_index` int(11) NOT NULL COMMENT '版块排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;

INSERT INTO `section` (`id`, `name`, `tab`, `show_status`, `display_index`)
VALUES
	(1,'问答','ask',1,3),
	(2,'博客','blog',1,4),
	(7,'资讯','news',1,2),
	(8,'灌水','gs',1,1);

/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `s_id` int(11) NOT NULL COMMENT '版块id',
  `title` varchar(255) NOT NULL COMMENT '话题标题',
  `content` longtext NOT NULL COMMENT '话题内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `view` int(11) NOT NULL COMMENT '浏览量',
  `author_id` varchar(32) NOT NULL COMMENT '话题作者id',
  `reposted` int(11) NOT NULL DEFAULT '0' COMMENT '1：转载 0：原创',
  `original_url` varchar(255) DEFAULT NULL COMMENT '原文连接',
  `top` int(11) NOT NULL DEFAULT '0' COMMENT '1置顶 0默认',
  `good` int(11) NOT NULL DEFAULT '0' COMMENT '1精华 0默认',
  `show_status` int(11) NOT NULL DEFAULT '1' COMMENT '1显示0不显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `gender` varchar(10) NOT NULL COMMENT '性别',
  `score` int(11) NOT NULL COMMENT '积分',
  `thirdlogin_type` varchar(10) NOT NULL COMMENT '第三方标识',
  `token` varchar(255) NOT NULL COMMENT '用户唯一标识，用于客户端和网页存入cookie',
  `expire_time` datetime NOT NULL COMMENT '登录过期时间',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `mission` date NOT NULL COMMENT '签到日期',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `open_id` varchar(255) NOT NULL COMMENT '第三方登录返回唯一标识值',
  `url` varchar(255) DEFAULT NULL COMMENT '个人主页',
  `signature` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `TOKEN_UNIQUE` (`token`),
  UNIQUE KEY `EMAIL_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
