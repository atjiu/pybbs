INSERT INTO `permission` (`id`, `name`, `value`, `pid`)
VALUES
	(50, '敏感词', 'sensitive_word', 0),
	(51, '敏感词列表', 'sensitive_word:list', 50),
	(52, '敏感词添加', 'sensitive_word:add', 50),
	(53, '敏感词编辑', 'sensitive_word:edit', 50),
	(54, '敏感词删除', 'sensitive_word:delete', 50),
	(55, '敏感词导入', 'sensitive_word:import', 50);

INSERT INTO `role_permission` (`role_id`, `permission_id`)
VALUES
	(1, 51),
	(1, 52),
	(1, 53),
	(1, 54),
	(1, 55);

