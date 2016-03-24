# ************************************************************
# Sequel Pro SQL dump
# Version 4529
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 120.24.172.224 (MySQL 5.5.43-0ubuntu0.14.04.1)
# Database: jfinalbbs
# Generation Time: 2016-03-24 10:24:14 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table jfbbs_admin_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_admin_log`;

CREATE TABLE `jfbbs_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '后台用户id',
  `target_id` varchar(32) NOT NULL DEFAULT '' COMMENT '操作对象id',
  `source` varchar(255) NOT NULL DEFAULT '' COMMENT '来源：topic,reply等',
  `in_time` datetime NOT NULL,
  `action` varchar(45) NOT NULL DEFAULT '' COMMENT '动作：delete,edit等',
  `message` longtext COMMENT '记录必要的删除内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jfbbs_admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_admin_user`;

CREATE TABLE `jfbbs_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '后台用户名',
  `password` varchar(45) NOT NULL COMMENT '后台密码（明文）',
  `salt` varchar(45) NOT NULL DEFAULT '',
  `in_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jfbbs_admin_user` WRITE;
/*!40000 ALTER TABLE `jfbbs_admin_user` DISABLE KEYS */;

INSERT INTO `jfbbs_admin_user` (`id`, `username`, `password`, `salt`, `in_time`)
VALUES
  (1,'admin','5992105b8c73df33ea69780688d54106','4ab2f2d2cea2ac05b36faa2582d5ecf6','2016-03-24 18:05:01');

/*!40000 ALTER TABLE `jfbbs_admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_collect`;

CREATE TABLE `jfbbs_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '收藏话题id',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_donate
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_donate`;

CREATE TABLE `jfbbs_donate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `price` varchar(45) NOT NULL DEFAULT '',
  `in_time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jfbbs_label` WRITE;
/*!40000 ALTER TABLE `jfbbs_label` DISABLE KEYS */;

INSERT INTO `jfbbs_label` (`id`, `name`, `in_time`, `topic_count`, `img`, `description`)
VALUES
  (1,'Java','2015-10-13 22:38:27',0,NULL,NULL),
  (3,'软件更新','2015-10-13 22:39:23',0,NULL,NULL),
  (6,'Freemarker','2015-10-13 22:40:22',0,NULL,NULL),
  (7,'Jfinal','2015-10-13 22:40:50',0,NULL,NULL),
  (11,'jfinal社区','2015-10-15 18:10:24',0,NULL,NULL),
  (12,'友链','2015-10-15 18:10:51',0,NULL,NULL),
  (13,'微信','2015-10-15 18:14:38',0,NULL,NULL),
  (14,'工具分享','2015-10-15 18:15:00',0,NULL,NULL),
  (19,'编辑器','2015-10-15 18:15:01',0,NULL,NULL);

/*!40000 ALTER TABLE `jfbbs_label` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_label_topic_id
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_label_topic_id`;

CREATE TABLE `jfbbs_label_topic_id` (
  `tid` varchar(32) NOT NULL,
  `lid` int(11) NOT NULL,
  KEY `fk_label_id` (`lid`) USING BTREE,
  KEY `fk_topic_id` (`tid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jfbbs_link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_link`;

CREATE TABLE `jfbbs_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '友链名称',
  `url` varchar(255) NOT NULL COMMENT '友链地址',
  `display_index` int(11) NOT NULL COMMENT '友链排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

LOCK TABLES `jfbbs_link` WRITE;
/*!40000 ALTER TABLE `jfbbs_link` DISABLE KEYS */;

INSERT INTO `jfbbs_link` (`id`, `name`, `url`, `display_index`)
VALUES
  (1,'JFinalbbs','http://www.jfinalbbs.com/',1);

/*!40000 ALTER TABLE `jfbbs_link` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_message`;

CREATE TABLE `jfbbs_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) NOT NULL COMMENT '消息属于哪一个对话下的',
  `content` varchar(1000) NOT NULL DEFAULT '',
  `author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '消息发出者',
  `in_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jfbbs_mission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_mission`;

CREATE TABLE `jfbbs_mission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) NOT NULL COMMENT '签到随机获得积分',
  `author_id` varchar(32) NOT NULL COMMENT '用户id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `day` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_msg_contact
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_msg_contact`;

CREATE TABLE `jfbbs_msg_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
  `to_author_id` varchar(32) NOT NULL DEFAULT '' COMMENT '发送消息对象（包括接收，发送的人）id',
  `msg_count` int(11) NOT NULL COMMENT '当前会话下的消息数量',
  `last_msg_content` varchar(1000) NOT NULL DEFAULT '' COMMENT '当前会话的最后一条消息',
  `in_time` datetime NOT NULL,
  `last_msg_time` datetime DEFAULT NULL,
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除会话，0默认1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jfbbs_notification
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_notification`;

CREATE TABLE `jfbbs_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL COMMENT '消息内容',
  `read` int(11) NOT NULL COMMENT '是否已读：0默认 1已读',
  `from_author_id` varchar(32) NOT NULL COMMENT '来源用户id',
  `author_id` varchar(32) NOT NULL COMMENT '目标用户id',
  `target_id` varchar(255) DEFAULT NULL COMMENT '目标id',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `action` varchar(255) DEFAULT NULL COMMENT '通知动作',
  `source` varchar(32) DEFAULT NULL COMMENT '通知来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_permission`;

CREATE TABLE `jfbbs_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL COMMENT '父节点0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `jfbbs_permission` WRITE;
/*!40000 ALTER TABLE `jfbbs_permission` DISABLE KEYS */;

INSERT INTO `jfbbs_permission` (`id`, `name`, `description`, `pid`)
VALUES
  (1,'menu:index','菜单，首页',10),
  (2,'menu:section','菜单，板块',10),
  (3,'menu:label','菜单，标签',10),
  (4,'menu:topic','菜单，话题',10),
  (5,'menu:reply','菜单，回复',10),
  (6,'menu:user','菜单，用户',10),
  (7,'menu:link','菜单，友链',10),
  (8,'menu:mission','菜单，签到',10),
  (9,'menu:setting','菜单，设置',10),
  (10,'menu','菜单节点',0),
  (11,'topic','话题节点',0),
  (12,'reply','回复节点',0),
  (13,'section','板块节点',0),
  (14,'link','友链节点',0),
  (15,'label','标签节点',0),
  (16,'user','用户节点',0),
  (17,'mission','签到节点',0),
  (18,'setting','设置节点',0),
  (19,'index','首页节点',0),
  (20,'topic:edit','话题，编辑',11),
  (21,'topic:delete','话题，删除',11),
  (22,'reply:delete','回复删除',12),
  (23,'section:add','增加板块',13),
  (24,'section:edit','板块编辑',13),
  (25,'section:delete','板块删除',13),
  (26,'link:add','增加友链',14),
  (27,'link:edit','友链编辑',14),
  (28,'link:delete','友链删除',14),
  (29,'label:add','增加标签',15),
  (30,'label:delete','标签删除',15),
  (31,'label:edit','标签编辑',15),
  (32,'user:delete','删除用户',16),
  (34,'setting:permission','权限设置',18),
  (35,'setting:sysconfig','系统设置',18),
  (36,'index:topic','今日话题',19),
  (37,'index:reply','今日回复',19),
  (38,'index:user','今日注册用户',19),
  (39,'index:mission','今日签到',19),
  (40,'link:sort','友链排序',14),
  (41,'section:sort','板块排序',13),
  (42,'section:setDefault','设置首页默认加载的板块',13),
  (43,'setting:role','角色设置',18),
  (44,'setting:adminuser','后台用户设置',18),
  (49,'user:disabled','禁用用户',16),
  (50,'setting:modifypwd','修改密码',18),
  (51,'topic:top','话题置顶',11),
  (53,'topic:good','话题加精',11),
  (54,'topic:show_status','话题设置显示/不显示',11);

/*!40000 ALTER TABLE `jfbbs_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_reply`;

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



# Dump of table jfbbs_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_role`;

CREATE TABLE `jfbbs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `jfbbs_role` WRITE;
/*!40000 ALTER TABLE `jfbbs_role` DISABLE KEYS */;

INSERT INTO `jfbbs_role` (`id`, `name`, `description`)
VALUES
  (1,'admin','超级管理员'),
  (2,'banzhu','版主');

/*!40000 ALTER TABLE `jfbbs_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_role_permission`;

CREATE TABLE `jfbbs_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `fk_role_permission` (`rid`),
  KEY `fk_permission_role` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `jfbbs_role_permission` WRITE;
/*!40000 ALTER TABLE `jfbbs_role_permission` DISABLE KEYS */;

INSERT INTO `jfbbs_role_permission` (`rid`, `pid`)
VALUES
  (1,1),
  (1,2),
  (1,3),
  (1,4),
  (1,5),
  (1,6),
  (1,7),
  (1,8),
  (1,9),
  (1,20),
  (1,21),
  (1,22),
  (1,23),
  (1,24),
  (1,25),
  (1,41),
  (1,42),
  (1,26),
  (1,27),
  (1,28),
  (1,40),
  (1,29),
  (1,30),
  (1,31),
  (1,32),
  (1,49),
  (1,34),
  (1,35),
  (1,43),
  (1,44),
  (1,50),
  (1,36),
  (1,37),
  (1,38),
  (1,39),
  (2,1),
  (2,4),
  (2,5),
  (2,9),
  (2,21),
  (2,51),
  (2,53),
  (2,54),
  (2,22),
  (2,50),
  (2,36),
  (2,37);

/*!40000 ALTER TABLE `jfbbs_role_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_section
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_section`;

CREATE TABLE `jfbbs_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '板块名称',
  `tab` varchar(45) NOT NULL DEFAULT '' COMMENT '板块标签',
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
  (10,'二手','used',0,99,0),
  (11,'招聘','job',0,99,0),
  (12,'私活','privatejob',0,99,0);

/*!40000 ALTER TABLE `jfbbs_section` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_sys_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_sys_config`;

CREATE TABLE `jfbbs_sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) NOT NULL DEFAULT '',
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `jfbbs_sys_config` WRITE;
/*!40000 ALTER TABLE `jfbbs_sys_config` DISABLE KEYS */;

INSERT INTO `jfbbs_sys_config` (`id`, `key`, `value`)
VALUES
  (2,'emailPassword',''),
  (3,'emailSender','JFinalbbs'),
  (4,'emailSmtp',''),
  (5,'emailUsername',''),
  (6,'pageSize','20'),
  (7,'qq_appId',''),
  (8,'qq_appKey',''),
  (9,'qq_redirect_URI',''),
  (10,'sina_clientId',''),
  (11,'sina_clientSercret',''),
  (12,'siteTitle','JFinalbbs'),
  (14,'qq_meta',''),
  (15,'sina_meta',''),
  (16,'baidu_site_meta',''),
  (17,'google_site_meta',''),
  (18,'bing_site_meta',''),
  (19,'beian_name',''),
  (20,'sina_redirect_URI',''),
  (21,'tongji_js','');

/*!40000 ALTER TABLE `jfbbs_sys_config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_topic`;

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
  `top` int(11) NOT NULL DEFAULT '0' COMMENT '1置顶 0默认',
  `good` int(11) NOT NULL DEFAULT '0' COMMENT '1精华 0默认',
  `show_status` int(11) NOT NULL DEFAULT '1' COMMENT '1显示0不显示',
  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数量',
  `isdelete` int(11) NOT NULL DEFAULT '0' COMMENT '1删除0默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table jfbbs_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_user`;

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



# Dump of table jfbbs_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_user_role`;

CREATE TABLE `jfbbs_user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  KEY `fk_user_role` (`uid`),
  KEY `fk_role_user` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `jfbbs_user_role` WRITE;
/*!40000 ALTER TABLE `jfbbs_user_role` DISABLE KEYS */;

INSERT INTO `jfbbs_user_role` (`uid`, `rid`)
VALUES
  (1,1);

/*!40000 ALTER TABLE `jfbbs_user_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jfbbs_valicode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jfbbs_valicode`;

CREATE TABLE `jfbbs_valicode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '验证码',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `status` int(11) NOT NULL COMMENT '0未使用  1已使用',
  `type` varchar(45) NOT NULL COMMENT '验证码类型，比如：找回密码',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `target` varchar(255) NOT NULL COMMENT '发送目标，如邮箱等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
