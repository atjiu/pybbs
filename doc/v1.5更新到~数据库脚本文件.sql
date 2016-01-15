ALTER TABLE `section`
	ADD COLUMN `default_show` INT(11) NOT NULL DEFAULT '0' COMMENT '默认显示模块 0默认，1显示' AFTER `display_index`;