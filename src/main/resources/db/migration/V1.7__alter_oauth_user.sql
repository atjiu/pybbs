ALTER TABLE `oauth_user` CHANGE `oauth_id` `oauth_id` INT(11)  NULL;

ALTER TABLE `oauth_user` ADD `refresh_token` VARCHAR(255) NULL  DEFAULT NULL  AFTER `user_id`;
ALTER TABLE `oauth_user` ADD `union_id` VARCHAR(255) NULL  DEFAULT NULL  AFTER `refresh_token`;
ALTER TABLE `oauth_user` ADD `expires_in` VARCHAR(255) NULL  DEFAULT NULL  AFTER `union_id`;

INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES
	(54, NULL, NULL, 'WeChat配置', 0, '', NULL, 0),
	(55, 'oauth_wechat_client_id', '', 'WeChat登录配置项AppId', 54, 'text', NULL, 0),
	(56, 'oauth_wechat_client_secret', '', 'WeChat登录配置项AppSecret', 54, 'password', NULL, 0),
	(57, 'oauth_wechat_callback_url', '', 'WeChat登录配置项回调地址', 54, 'url', NULL, 0);
