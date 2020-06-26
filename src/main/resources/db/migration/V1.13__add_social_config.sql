
INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES
    (60, NULL, NULL, 'Gitee配置', 0, '', NULL, 0),
    (61, 'oauth_gitee_client_id', '', 'Gitee登录配置项AppId', 60, 'text', NULL, 0),
    (62, 'oauth_gitee_client_secret', '', 'Gitee登录配置项AppSecret', 60, 'password', NULL, 0),
    (63, 'oauth_gitee_callback_url', '', 'Gitee登录配置项回调地址', 60, 'url', NULL, 0),

    (64, NULL, NULL, '新浪微博配置', 0, '', NULL, 0),
    (65, 'oauth_weibo_client_id', '', '新浪微博登录配置项AppId', 64, 'text', NULL, 0),
    (66, 'oauth_weibo_client_secret', '', '新浪微博登录配置项AppSecret', 64, 'password', NULL, 0),
    (67, 'oauth_weibo_callback_url', '', '新浪微博登录配置项回调地址', 64, 'url', NULL, 0),

    (68, NULL, NULL, '开源中国配置', 0, '', NULL, 0),
    (69, 'oauth_oschina_client_id', '', '开源中国登录配置项AppId', 68, 'text', NULL, 0),
    (70, 'oauth_oschina_client_secret', '', '开源中国登录配置项AppSecret', 68, 'password', NULL, 0),
    (71, 'oauth_oschina_callback_url', '', '开源中国登录配置项回调地址', 68, 'url', NULL, 0);