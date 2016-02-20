# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.7.10)
# Database: jfinalbbs
# Generation Time: 2016-01-19 01:48:21 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table jfbbs_admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_admin_user`;

CREATE TABLE `jfbbs_admin_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '后台用户名',
  `password` varchar(45) NOT NULL COMMENT '后台密码(123456)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `jfbbs_admin_user` WRITE;
/*!40000 ALTER TABLE `jfbbs_admin_user` DISABLE KEYS */;

INSERT INTO `jfbbs_admin_user` (`id`, `username`, `password`)
VALUES
	(1,'admin','e10adc3949ba59abbe56e057f20f883e');

/*!40000 ALTER TABLE `jfbbs_admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_collect
# ------------------------------------------------------------

CREATE TABLE `jfbbs_collect` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '收藏话题id',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_label
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_label`;

CREATE TABLE `jfbbs_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `in_time` datetime NOT NULL,
  `topic_count` int(11) NOT NULL DEFAULT '0',
  `img` varchar(255) DEFAULT NULL COMMENT '标签图片',
  `description` varchar(500) DEFAULT NULL COMMENT '标签描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

LOCK TABLES `jfbbs_label` WRITE;
/*!40000 ALTER TABLE `jfbbs_label` DISABLE KEYS */;

INSERT INTO `jfbbs_label` (`id`, `name`, `in_time`, `topic_count`, `img`, `description`)
VALUES
  (1, 'Java', '2015-10-13 22:38:27', 0, NULL, NULL),
  (3, '软件更新', '2015-10-13 22:39:23', 0, NULL, NULL),
  (6, 'Freemarker', '2015-10-13 22:40:22', 0, NULL, NULL),
  (7, 'Jfinal', '2015-10-13 22:40:50', 0, NULL, NULL),
  (11, 'jfinal社区', '2015-10-15 18:10:24', 0, NULL, NULL),
  (12, '友链', '2015-10-15 18:10:51', 0, NULL, NULL),
  (13, '微信', '2015-10-15 18:14:38', 0, NULL, NULL);

/*!40000 ALTER TABLE `jfbbs_label` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_label_topic_id
# ------------------------------------------------------------

CREATE TABLE `jfbbs_label_topic_id` (
  `tid` varchar(32) NOT NULL,
  `lid` int(11) NOT NULL,
  KEY `fk_label_id` (`lid`),
  KEY `fk_topic_id` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_link`;

CREATE TABLE `jfbbs_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '友链名称',
  `url` varchar(255) NOT NULL COMMENT '友链地址',
  `display_index` int(11) NOT NULL COMMENT '友链排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `jfbbs_link` WRITE;
/*!40000 ALTER TABLE `jfbbs_link` DISABLE KEYS */;

INSERT INTO `jfbbs_link` (`id`, `name`, `url`, `display_index`)
VALUES
	(1,'JFinalbbs','http://jfinalbbs.com/',1);

/*!40000 ALTER TABLE `jfbbs_link` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_message
# ------------------------------------------------------------

CREATE TABLE `jfbbs_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) NOT NULL COMMENT '消息属于哪一个对话下的',
  `content` varchar(1000) NOT NULL DEFAULT '',
  `author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '消息发出者',
  `in_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


# Dump of table jfbbs_mission
# ------------------------------------------------------------

CREATE TABLE `jfbbs_mission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `score` int(11) NOT NULL COMMENT '签到随机获得积分',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `day` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


# Dump of table jfbbs_msg_contact
# ------------------------------------------------------------

CREATE TABLE `jfbbs_msg_contact` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
  `to_author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '发送消息对象（包括接收，发送的人）id',
  `msg_count` int(11) NOT NULL COMMENT '当前会话下的消息数量',
  `last_msg_content` varchar(1000) NOT NULL DEFAULT '' COMMENT '当前会话的最后一条消息',
  `in_time` datetime NOT NULL,
  `last_msg_time` datetime DEFAULT NULL,
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除会话，0默认1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



# Dump of table jfbbs_notification
# ------------------------------------------------------------

CREATE TABLE `jfbbs_notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL COMMENT '消息内容',
  `read` int(11) NOT NULL COMMENT '是否已读：0默认 1已读',
  `from_author_id` varchar(32) NOT NULL COMMENT '来源用户id',
  `author_id` varchar(32) NOT NULL COMMENT '目标用户id',
  `target_id` varchar(255) DEFAULT NULL COMMENT '目标id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `action` varchar(255) DEFAULT NULL COMMENT '通知动作',
  `source` varchar(32) DEFAULT NULL COMMENT '通知来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1844 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_reply
# ------------------------------------------------------------

CREATE TABLE `jfbbs_reply` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `tid` varchar(32) NOT NULL COMMENT '话题id',
  `content` longtext NOT NULL COMMENT '回复内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `author_id` varchar(32) NOT NULL COMMENT '当前回复用户id',
  `best` int(11) NOT NULL DEFAULT '0' COMMENT '采纳为最佳答案 0默认，1采纳',
  `isdelete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除0 默认 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_section
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_section`;

CREATE TABLE `jfbbs_section` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '板块名称',
  `tab` varchar(45) NOT NULL COMMENT '板块标签',
  `show_status` int(11) NOT NULL DEFAULT '1' COMMENT '是否显示，0不显示1显示',
  `display_index` int(11) NOT NULL COMMENT '板块排序',
  `default_show` int(11) NOT NULL DEFAULT '0' COMMENT '默认显示板块 0默认，1显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `jfbbs_section` WRITE;
/*!40000 ALTER TABLE `jfbbs_section` DISABLE KEYS */;

INSERT INTO `jfbbs_section` (`id`, `name`, `tab`, `show_status`, `display_index`, `default_show`)
VALUES
	(1,'问答','ask',1,4,0),
	(2,'博客','blog',1,5,0),
	(7,'资讯','news',1,2,0),
	(8,'灌水','gs',1,1,0),
	(9,'分享','share',1,3,1),
	(10,'二手','used',1,99,0),
	(11,'招聘','job',1,99,0),
	(12,'私活','privatejob',1,99,0);

/*!40000 ALTER TABLE `jfbbs_section` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_topic
# ------------------------------------------------------------

CREATE TABLE `jfbbs_topic` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `s_id` int(11) NOT NULL COMMENT '版块id',
  `title` varchar(255) NOT NULL COMMENT '话题标题',
  `content` longtext NOT NULL COMMENT '话题内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_reply_time` datetime DEFAULT NULL COMMENT '最后回复话题时间，用于排序',
  `last_reply_author_id` varchar(32) DEFAULT NULL COMMENT '最后回复话题的用户id',
  `view` int(11) NOT NULL COMMENT '浏览量',
  `author_id` varchar(32) NOT NULL COMMENT '话题作者id',
  `reposted` int(11) NOT NULL DEFAULT '0' COMMENT '1：转载 0：原创',
  `original_url` varchar(255) DEFAULT NULL COMMENT '原文连接',
  `top` int(11) NOT NULL DEFAULT '0' COMMENT '1置顶 0默认',
  `good` int(11) NOT NULL DEFAULT '0' COMMENT '1精华 0默认',
  `show_status` int(11) NOT NULL DEFAULT '1' COMMENT '1显示0不显示',
  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '话题回复数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


# Dump of table jfbbs_sys_config
# ------------------------------------------------------------

CREATE TABLE `jfbbs_sys_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(45) NOT NULL DEFAULT '',
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


LOCK TABLES `jfbbs_sys_config` WRITE;
/*!40000 ALTER TABLE `jfbbs_sys_config` DISABLE KEYS */;

INSERT INTO `jfbbs_sys_config` (`id`, `key`, `value`)
VALUES
  (1, 'baseUrl', 'http://localhost:8080/jfinalbbs'),
  (2, 'emailPassword', ''),
  (3, 'emailSender', 'JFinalbbs'),
  (4, 'emailSmtp', ''),
  (5, 'emailUsername', ''),
  (6, 'pageSize', '20'),
  (7, 'qq_appId', ''),
  (8, 'qq_appKey', ''),
  (9, 'qq_redirect_URI', ''),
  (10, 'sina_clientId', ''),
  (11, 'sina_clientSercret', ''),
  (12, 'siteTitle', 'JFinalbbs'),
  (14, 'qq_meta', ''),
  (15, 'sina_meta', ''),
  (16, 'baidu_site_meta', ''),
  (17, 'google_site_meta', ''),
  (18, 'bing_site_meta', ''),
  (19, 'beian_name', ''),
  (20, 'sina_redirect_URI', '');


/*!40000 ALTER TABLE `jfbbs_sys_config` ENABLE KEYS */;
UNLOCK TABLES;

# Dump of table jfbbs_user
# ------------------------------------------------------------

CREATE TABLE `jfbbs_user` (
  `id` varchar(32) NOT NULL,
  `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `score` int(11) NOT NULL COMMENT '积分',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '用户唯一标识，用于客户端和网页存入cookie',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `mission` date NOT NULL COMMENT '签到日期',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `email` varchar(255) DEFAULT '' COMMENT '邮箱',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `url` varchar(255) DEFAULT NULL COMMENT '个人主页',
  `signature` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `qq_open_id` varchar(255) DEFAULT NULL COMMENT 'qq登录唯一标识',
  `qq_avatar` varchar(255) DEFAULT NULL COMMENT 'qq头像',
  `qq_nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'qq昵称',
  `sina_open_id` varchar(255) DEFAULT NULL COMMENT '新浪微博登录唯一标识',
  `sina_avatar` varchar(255) DEFAULT NULL COMMENT '新浪微博头像',
  `sina_nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '新浪微博昵称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `TOKEN_UNIQUE` (`token`),
  UNIQUE KEY `EMAIL_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_valicode
# ------------------------------------------------------------

CREATE TABLE `jfbbs_valicode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '验证码',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `status` int(11) NOT NULL COMMENT '0未使用  1已使用',
  `type` varchar(45) NOT NULL COMMENT '验证码类型，比如：找回密码',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `target` varchar(255) NOT NULL COMMENT '发送目标，如邮箱等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
