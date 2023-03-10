INSERT INTO `system_config`(`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (85, NULL, '', 'Telegram', 0, NULL, NULL, 0),
       (86, 'tg_webhook_secret_token', '', '回调鉴权密钥(webhook)', 85, 'password', NULL, 0),
       (87, 'tg_access_token', '', '机器人接口令牌', 85, 'password', NULL, 0),
       (88, 'tg_to_chat_id', '', 'TG接收消息用户ID', 85, 'text', NULL, 0),
       (89, 'comment_need_examine', '0', '评论是否需要审核', 23, 'radio', NULL, 0),
       (90, NULL, NULL, '系统代理', 0, NULL, NULL, 0),
       (91, 'http_proxy', '', '代理地址(如：127.0.0.1)', 90, 'text', NULL, 1),
       (92, 'http_proxy_port', '', '代理端口(如：1087)', 90, 'number', NULL, 1);

ALTER TABLE `comment`
    ADD COLUMN `tg_message_id` int(11) NULL AFTER `up_ids`,
    ADD COLUMN `status`        bit(1) DEFAULT b'0' AFTER `tg_message_id`;

INSERT INTO `permission`(`id`, `name`, `value`, `pid`)
VALUES (56, '评论审核', 'comment:examine', 3);

INSERT INTO `role_permission`(`role_id`, `permission_id`)
VALUES (1, 56),
       (2, 56);

