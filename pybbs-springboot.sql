
INSERT INTO `pybbs_permission` (`id`, `description`, `name`, `pid`, `url`)
VALUES
	(1,'仪表盘','index',0,NULL),
	(2,'话题','topic',0,NULL),
	(3,'回复','reply',0,NULL),
	(4,'用户','user',0,NULL),
	(5,'权限','security',0,NULL),
	(6,'角色','role',0,NULL),
	(7,'后台管理','admin:index',1,'/admin/index'),
	(8,'话题编辑','topic:edit',2,'/topic/*/edit'),
	(9,'话题删除','topic:delete',2,'/topic/*/delete'),
	(10,'回复编辑','reply:edit',3,'/reply/*/edit'),
	(11,'回复删除','reply:delete',3,'/reply/*/delete'),
	(12,'回复列表','reply:list',3,'/admin/reply/list'),
	(13,'用户列表','user:list',4,'/admin/user/list'),
	(14,'用户角色','user:role',4,'/admin/user/*/role'),
	(15,'用户删除','user:delete',4,'/admin/user/*/delete'),
	(16,'权限列表','permission:list',5,'/admin/permission/list'),
	(17,'权限添加','permission:add',5,'/admin/permission/add'),
	(18,'权限编辑','permission:edit',5,'/admin/permission/*/edit'),
	(19,'权限删除','permission:delete',5,'/admin/permission/*/delete'),
	(20,'角色列表','role:list',6,'/admin/role/list'),
	(21,'角色添加','role:add',6,'/admin/role/add'),
	(22,'角色编辑','role:edit',6,'/admin/role/*/edit'),
	(23,'角色删除','role:delete',6,'/admin/role/*/delete');

INSERT INTO `pybbs_role` (`id`, `description`, `name`)
VALUES
	(1,'超级管理员','admin'),
	(2,'版主','banzhu'),
	(3,'会员','user');

INSERT INTO `pybbs_role_permission` (`role_id`, `permission_id`)
VALUES
	(1,7),
	(2,7),
	(1,8),
	(2,8),
	(1,9),
	(2,9),
	(1,10),
	(2,10),
	(1,11),
	(2,11),
	(1,12),
	(2,12),
	(1,13),
	(1,14),
	(1,15),
	(1,16),
	(1,17),
	(1,18),
	(1,19),
	(1,20),
	(1,21),
	(1,22),
	(1,23);

INSERT INTO `pybbs_user` (`id`, `email`, `in_time`, `password`, `signature`, `url`, `username`, `avatar`)
VALUES
	(1,'liygheart@qq.com','2016-09-09 09:50:14','$2a$10$KkUG107R3ASTHfAHei.bweXWXgCa4cE1KhK.F0odzfE0r97aeeTXC','这家伙很懒，什么都没留下','http://tomoya.cn','tomoya','http://localhost:8080/static/images/avatar/default.png');

INSERT INTO `pybbs_user_role` (`user_id`, `role_id`)
VALUES
	(1,1);
