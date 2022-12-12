ALTER TABLE `topic`
    ADD COLUMN `style` VARCHAR(50) NULL DEFAULT 'MD' AFTER `user_id`;

ALTER TABLE `comment`
    ADD COLUMN `style` VARCHAR(50) NULL DEFAULT 'MD' AFTER `id`;

UPDATE `topic`
set `style` = "MD"
where `style` is null;

UPDATE `comment`
set `style` = "MD"
where `style` is null;

INSERT INTO `system_config` (`key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES ('content_style', 'MD', '发帖或者回复的输入框语法风格', '23', 'select', 'RICH,MD', '1');