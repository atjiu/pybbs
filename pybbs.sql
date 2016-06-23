# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.10)
# Database: pybbs
# Generation Time: 2016-05-30 07:38:43 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table pybbs_collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_collect`;

CREATE TABLE `pybbs_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `in_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table pybbs_notification
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_notification`;

CREATE TABLE `pybbs_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `read` tinyint(1) NOT NULL COMMENT '是否已读：0默认 1已读',
  `author` varchar(50) NOT NULL DEFAULT '' COMMENT '发起通知用户昵称',
  `target_author` varchar(50) NOT NULL COMMENT '要通知用户的昵称',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `action` varchar(255) NOT NULL DEFAULT '' COMMENT '通知动作',
  `tid` int(11) NOT NULL COMMENT '话题id',
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table pybbs_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_permission`;

CREATE TABLE `pybbs_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '权限名称',
  `url` varchar(255) DEFAULT NULL COMMENT '授权路径',
  `description` varchar(255) NOT NULL COMMENT '权限描述',
  `pid` int(11) NOT NULL COMMENT '父节点0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `pybbs_permission` WRITE;
/*!40000 ALTER TABLE `pybbs_permission` DISABLE KEYS */;

INSERT INTO `pybbs_permission` (`id`, `name`, `url`, `description`, `pid`)
VALUES
  (56, 'system', '', '系统设置', 0),
  (57, 'topic', '', '话题节点', 0),
  (58, 'reply', '', '回复节点', 0),
  (59, 'system:users', '/manage/users', '用户列表', 56),
  (60, 'system:roles', '/manage/roles', '角色列表', 56),
  (61, 'system:permissions', '/manage/permissions', '权限列表', 56),
  (62, 'system:userrole', '/manage/userrole', '用户角色关联', 56),
  (63, 'system:rolepermission', '/manage/rolepermission', '角色权限关联', 56),
  (64, 'system:addpermission', '/manage/addpermission', '添加权限', 56),
  (65, 'system:editpermission', '/manage/editpermission', '编辑权限', 56),
  (66, 'system:addrole', '/manage/addrole', '添加角色', 56),
  (67, 'system:addrole', '/manage/addrole', '添加角色', 56),
  (68, 'system:deleteuser', '/manage/deleteuser', '删除用户', 56),
  (69, 'system:deleterole', '/manage/deleterole', '删除角色', 56),
  (70, 'system:deletepermission', '/manage/deletepermission', '删除权限', 56),
  (71, 'topic:delete', '/t/delete', '删除话题', 57),
  (73, 'reply:delete', '/r/delete', '删除回复', 58),
  (74, 'reply:edit', '/r/edit', '编辑回复', 58),
  (75, 'topic:edit', '/t/edit', '话题编辑', 57),
  (76, 'topic:appendedit', '/t/appendedit', '追加编辑', 57),
  (77, 'topic:top', '/t/top', '话题置顶', 57),
  (78, 'topic:good', '/t/good', '话题加精', 57),
  (79, 'system:clearcache', '/clear', '删除所有缓存', 56),
  (80, 'section', '', '板块节点', 0),
  (81, 'section:list', '/section/list', '板块列表', 80),
  (82, 'section:changeshowstatus', '/section/changeshowstatus', '改变板块显示状态', 80),
  (83, 'section:delete', '/section/delete', '删除板块', 80),
  (84, 'section:add', '/section/add', '添加板块', 80),
  (85, 'section:edit', '/section/edit', '编辑板块', 80),
  (86, 'reply:list', '/r/list', '回复列表', 58),
  (87, 'system:solr', '/solr', '索引所有话题(慎用)', 56),
  (88, 'system:deleteallindex', '/deleteallindex', '删除所有索引', 56),
  (89, 'system:userblock', '/manage/userblock', '禁用用户', 56);


/*!40000 ALTER TABLE `pybbs_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pybbs_reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_reply`;

CREATE TABLE `pybbs_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL COMMENT '话题id',
  `content` longtext NOT NULL COMMENT '回复内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `author` varchar(32) NOT NULL COMMENT '当前回复用户id',
  `isdelete` tinyint(1) NOT NULL COMMENT '是否删除0 默认 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table pybbs_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_role`;

CREATE TABLE `pybbs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `pybbs_role` WRITE;
/*!40000 ALTER TABLE `pybbs_role` DISABLE KEYS */;

INSERT INTO `pybbs_role` (`id`, `name`, `description`)
VALUES
	(1,'admin','超级管理员'),
	(2,'banzhu','版主'),
	(3,'user','普通用户');

/*!40000 ALTER TABLE `pybbs_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pybbs_role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_role_permission`;

CREATE TABLE `pybbs_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `fk_role_permission` (`rid`),
  KEY `fk_permission_role` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `pybbs_role_permission` WRITE;
/*!40000 ALTER TABLE `pybbs_role_permission` DISABLE KEYS */;

INSERT INTO `pybbs_role_permission` (`rid`, `pid`)
VALUES
  (2, 71),
  (2, 75),
  (2, 76),
  (2, 77),
  (2, 78),
  (2, 73),
  (2, 74),
  (1, 59),
  (1, 60),
  (1, 61),
  (1, 62),
  (1, 63),
  (1, 64),
  (1, 65),
  (1, 66),
  (1, 67),
  (1, 68),
  (1, 69),
  (1, 70),
  (1, 79),
  (1, 87),
  (1, 88),
  (1, 89),
  (1, 71),
  (1, 75),
  (1, 76),
  (1, 77),
  (1, 78),
  (1, 73),
  (1, 74),
  (1, 86),
  (1, 81),
  (1, 82),
  (1, 83),
  (1, 84),
  (1, 85);

/*!40000 ALTER TABLE `pybbs_role_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pybbs_section
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_section`;

CREATE TABLE `pybbs_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '板块名称',
  `tab` varchar(45) NOT NULL DEFAULT '' COMMENT '板块标签',
  `show_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示，0不显示1显示',
  `display_index` int(11) NOT NULL COMMENT '板块排序',
  `default_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认显示板块 0默认，1显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tabunique` (`tab`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `pybbs_section` WRITE;
/*!40000 ALTER TABLE `pybbs_section` DISABLE KEYS */;

INSERT INTO `pybbs_section` (`id`, `name`, `tab`, `show_status`, `display_index`, `default_show`)
VALUES
	(1,'问答','ask',1,4,0),
	(2,'博客','blog',1,5,0),
	(3,'资讯','news',1,2,0),
	(4,'分享','share',1,3,1),
	(5,'二手','used',0,99,0),
	(6,'招聘','job',0,99,0),
	(7,'私活','privatejob',0,99,0);

/*!40000 ALTER TABLE `pybbs_section` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pybbs_topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_topic`;

CREATE TABLE `pybbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tab` varchar(50) NOT NULL COMMENT '版块标识',
  `title` varchar(128) NOT NULL COMMENT '话题标题',
  `content` longtext COMMENT '话题内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_reply_time` datetime DEFAULT NULL COMMENT '最后回复话题时间，用于排序',
  `last_reply_author` varchar(50) DEFAULT '' COMMENT '最后回复话题的用户id',
  `view` int(11) NOT NULL COMMENT '浏览量',
  `author` varchar(50) NOT NULL COMMENT '话题作者id',
  `top` tinyint(1) NOT NULL COMMENT '1置顶 0默认',
  `good` tinyint(1) NOT NULL COMMENT '1精华 0默认',
  `show_status` tinyint(1) NOT NULL COMMENT '1显示0不显示',
  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数量',
  `isdelete` tinyint(1) NOT NULL COMMENT '1删除0默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table pybbs_topic_append
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_topic_append`;

CREATE TABLE `pybbs_topic_append` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `in_time` datetime NOT NULL,
  `isdelete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table pybbs_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_user`;

CREATE TABLE `pybbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `score` int(11) NOT NULL COMMENT '积分',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `url` varchar(255) DEFAULT NULL COMMENT '个人主页',
  `signature` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `third_id` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方账户id',
  `access_token` varchar(45) NOT NULL,
  `receive_msg` tinyint(1) NOT NULL COMMENT '邮箱是否接收社区消息',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `expire_time` datetime NOT NULL,
  `channel` varchar(50) NOT NULL,
  `isblock` tinyint(1) NOT NULL COMMENT '禁用0默认 1禁用',
  `third_access_token` varchar(50) DEFAULT NULL COMMENT '第三方登录获取的access_token',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NICKNAME_UNIQUE` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

# Dump of table pybbs_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pybbs_user_role`;

CREATE TABLE `pybbs_user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  KEY `fk_user_role` (`uid`),
  KEY `fk_role_user` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `pybbs_user_role` WRITE;
/*!40000 ALTER TABLE `pybbs_user_role` DISABLE KEYS */;

INSERT INTO `pybbs_user_role` (`uid`, `rid`)
VALUES
	(1,1);

/*!40000 ALTER TABLE `pybbs_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
