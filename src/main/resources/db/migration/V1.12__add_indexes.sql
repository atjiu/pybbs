ALTER TABLE `comment` ADD INDEX `comment_in_time` (`in_time`);
ALTER TABLE `permission` ADD INDEX `permission_pid` (`pid`);
ALTER TABLE `sensitive_word` ADD INDEX `sensitive_word` (`word`);
ALTER TABLE `system_config` ADD INDEX `system_config_key` (`key`);
ALTER TABLE `tag` ADD INDEX `tag_in_time` (`in_time`);
ALTER TABLE `topic` ADD INDEX `topic_in_time` (`in_time`);
ALTER TABLE `user` ADD INDEX `user_email` (`email`);
ALTER TABLE `user` ADD INDEX `user_in_time` (`in_time`);
