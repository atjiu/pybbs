ALTER TABLE `user` ADD `active` BIT(1)  NOT NULL  DEFAULT b'0'  AFTER `email_notification`;
UPDATE `user` set `active` = TRUE WHERE `email` IS NOT NULL;
