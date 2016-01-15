-- 接着数据库版本v1.4后的升级脚本
ALTER TABLE `reply` ADD `best` INT  NOT NULL  DEFAULT '0'  COMMENT '采纳为最佳答案 0默认，1采纳'  AFTER `author_id`;
ALTER TABLE `reply` ADD `is_delete` INT  NULL  DEFAULT '0'  COMMENT '删除 0默认，1删除'  AFTER `best`;
ALTER TABLE `reply` ADD `isdelete` INT NOT NULL  DEFAULT '0'  COMMENT '是否删除0 默认 1删除';

CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `in_time` datetime NOT NULL,
  `topic_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

CREATE TABLE `label_topic_id` (
  `tid` varchar(32) NOT NULL,
  `lid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;