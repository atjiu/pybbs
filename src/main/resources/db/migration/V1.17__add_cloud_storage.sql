INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (75, NULL, '', '云存储', 0, NULL, NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (76, 'cloud_storage_platform', 'LOCAL', '云存储平台', 75, 'select', 'LOCAL,QINIU,OSS', 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (77, 'oss_key', '', '阿里云存储key', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (78, 'oss_secret', '', '阿里云存储secret', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (79, 'oss_bucket', '', '阿里云存储bucket', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (80, 'oss_end_point', 'http://oss-cn-hangzhou.aliyuncs.com', '阿里云存储上传地址（请根据文档来设置）', 75, 'url', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (81, 'qiniu_key', '', '七牛云存储key', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (82, 'qiniu_secret', '', '七牛云存储secret', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (83, 'qiniu_bucket', '', '七牛云存储bucket', 75, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (84, 'qiniu_domain', '', '七牛云存储访问域名(最后没有/)', 75, 'url', NULL, 0);
