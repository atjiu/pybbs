update system_config set `key` = 'websocket_url', description = "websocket服务的连接地址" where `key` = "websocket_host";
delete from system_config where `key` = "websocket_port";
