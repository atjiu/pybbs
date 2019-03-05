-- code表增加mobile字段

ALTER TABLE `code`
	ALTER `user_id` DROP DEFAULT;
ALTER TABLE `code`
	CHANGE COLUMN `user_id` `user_id` INT(11) NULL AFTER `id`,
	CHANGE COLUMN `email` `email` VARCHAR(255) NULL DEFAULT NULL AFTER `expire_time`,
	ADD COLUMN `mobile` VARCHAR(255) NULL DEFAULT NULL AFTER `email`;
ALTER TABLE `user`
  ADD COLUMN `mobile` VARCHAR(255) NULL DEFAULT NULL AFTER `email`;
ALTER TABLE `system_config`
	CHANGE COLUMN `type` `type` VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE `system_config`
	CHANGE COLUMN `value` `value` VARCHAR(255) NULL DEFAULT '';

-- 添加阿里云短信配置

INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`) VALUES (49, NULL, NULL, '短信配置', 0, NULL, NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`) VALUES (50, 'sms_access_key_id', '', '创建短信连接的key', 49, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`) VALUES (51, 'sms_secret', '', '创建短信连接的密钥', 49, 'password', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`) VALUES (52, 'sms_sign_name', '', '短信签名，在阿里云申请的签名', 49, 'text', NULL, 0);
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`) VALUES (53, 'sms_template_code', '', '短信模板Code，在阿里云申请的模板Code', 49, 'text', NULL, 0);
