UPDATE `system_config` set `value` = 'default' WHERE `key` = 'theme';
UPDATE `system_config` set `type` = 'password' WHERE `key` = 'oauth_github_client_secret';
UPDATE `system_config` set `description` = 'Github配置' WHERE `id` = 40;
UPDATE `system_config` set `description` = 'WebSocket' WHERE `id` = 45;
