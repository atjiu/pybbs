INSERT INTO `yiiu_permission`
VALUES (1, '仪表盘', 'index', 0, NULL), (2, '话题', 'topic', 0, NULL), (3, '回复', 'reply', 0, NULL),
  (4, '用户', 'user', 0, NULL), (5, '权限', 'security', 0, NULL), (6, '角色', 'role', 0, NULL),
  (7, '后台管理', 'admin:index', 1, '/admin/index'), (8, '话题编辑', 'topic:edit', 2, '/admin/topic/*/edit'),
  (9, '话题删除', 'topic:delete', 2, '/admin/topic/*/delete'), (10, '回复编辑', 'reply:edit', 3, '/admin/reply/*/edit'),
  (11, '回复删除', 'reply:delete', 3, '/admin/reply/*/delete'), (12, '回复列表', 'reply:list', 3, '/admin/reply/list'),
  (13, '用户列表', 'user:list', 4, '/admin/user/list'), (14, '用户角色', 'user:role', 4, '/admin/user/*/role'),
  (15, '用户删除', 'user:delete', 4, '/admin/user/*/delete'), (16, '权限列表', 'permission:list', 5, '/admin/permission/list'),
  (17, '权限添加', 'permission:add', 5, '/admin/permission/add'),
  (18, '权限编辑', 'permission:edit', 5, '/admin/permission/*/edit'),
  (19, '权限删除', 'permission:delete', 5, '/admin/permission/*/delete'), (20, '角色列表', 'role:list', 6, '/admin/role/list'),
  (21, '角色添加', 'role:add', 6, '/admin/role/add'), (22, '角色编辑', 'role:edit', 6, '/admin/role/*/edit'),
  (23, '角色删除', 'role:delete', 6, '/admin/role/*/delete'), (24, '索引', 'index', 0, ''),
  (25, '索引全部话题', 'index:all', 24, '/admin/indexed/indexAll'),
  (26, '删除所有索引', 'index:deleteAll', 24, '/admin/indexed/deleteAll'), (27, '话题置顶', 'topic:top', 2, '/admin/topic/*/top'),
  (28, '话题加精', 'topic:good', 2, '/admin/topic/*/good'), (29, '版块', 'topic:good', 0, NULL),
  (30, '版块列表', 'node:list', 29, '/admin/node/list'), (31, '添加版块', 'node:add', 29, '/admin/node/add'),
  (32, '编辑版块', 'node:edit', 29, '/admin/node/*/edit'),
  (33, '删除版块', 'node:delete', 29, '/admin/node/*/delete'), (34, '标签', 'label', 0, ''),
  (35, '标签列表', 'label:list', 34, '/admin/label/list'), (36, '标签编辑', 'label:edit', 34, '/admin/label/*/edit'),
  (37, '标签更新', 'label:update', 34, '/admin/label/update'), (38, '标签删除', 'label:delete', 34, '/admin/label/*/delete'),
  (39, '后台话题列表', 'topic:list', 2, '/admin/topic/list'), (40, '话题锁定', 'topic:lock', 2, '/admin/topic/*/lock');

INSERT INTO `yiiu_role` VALUES (1, '超级管理员', 'admin'), (2, '版主', 'banzhu'), (3, '会员', 'user');

INSERT INTO `yiiu_role_permission`
VALUES (1, 7), (2, 7), (1, 8), (2, 8), (1, 9), (2, 9), (1, 10), (2, 10), (1, 11), (2, 11), (1, 12), (2, 12), (1, 13),
  (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20), (1, 21), (1, 22), (1, 23), (1, 25), (1, 26), (1, 27),
  (2, 27), (1, 28), (2, 28), (1, 30), (1, 31), (1, 32), (1, 33), (1, 35), (1, 36), (1, 37), (1, 38), (1, 39), (2, 39),
  (1, 40), (2, 40), (3, 8);

INSERT INTO `yiiu_node` VALUES (1, '分享'), (4, '博客'), (5, '招聘'), (3, '新闻'), (2, '问答');

INSERT INTO `yiiu_user` (`id`, `avatar`, `block`, `email`, `in_time`, `password`, `signature`, `token`, `url`, `username`, `attempts`, `attempts_time`, `score`, `space_size`)
VALUES
  (1, 'http://localhost:8080/static/images/upload/avatar/default.png', FALSE, 'liygheart@qq.com', '2016-09-09 09:50:14',
      '$2a$10$KkUG107R3ASTHfAHei.bweXWXgCa4cE1KhK.F0odzfE0r97aeeTXC', 'hello world',
      'd20b9a5c-8693-41a6-8943-ddb2cb78eebd', 'https://tomoya92.github.io', 'tomoya', 0, '2017-06-21 23:11:24', 2000,
   50);

INSERT INTO `yiiu_user_role` VALUES (1, 1);

