### 友情提醒: 使用jfinalbbs的代码搭建自己的社区网站,不能删除页面底部的 powered by [JFinalbbs](http://jfinalbbs.com) ,如果嫌位置不好看,可以在页面其他显眼的地方加上提供者:[JFinalbbs](http://jfinalbbs.com), 谢谢配合

# 如何开始

- clone代码,maven编译打成war包, 或者直接[下载war包](https://github.com/liygheart/jfinalbbs/releases)
- 将`doc/最新版SQL.sql`脚本在mysql数据库里运行，创建jfinalbbs数据库
- [下载tomcat](http://tomcat.apache.org),解压
- 将war包放到`tomcat/webapp`下,启动tomcat(`./bin/startup.sh`)
- 修改`tomcat/webapp/jfinalbbs/WEB-INF/classes/config.properties`文件的配置(就一个数据库的jdbc连接信息)
- 重启tomcat
- 打开浏览器,输入`http://localhost:8080/jfinalbbs`回车
- 后台访问路径`http://localhost:8080/jfinalbbs/admin/index`

# 碰到问题怎么办?

1. 加QQ群 419343003 ,阅读群公告后可以提问题
2. 在Github上提 Issues
3. 提问题的时候请将问题重现步骤描述清楚,对于截个图什么也不说,发一句话就了事的,我会看心情回答(你懂的!)

# 贡献代码

目前jfinalbbs就一个`master`分支,就朋也一个人在开发维护,如果想分享自己拓展的功能等,可以给朋也的`master`分支推送`pull request`

# 2016年2月17日 V2.1更新内容

1. 增加私信
2. 增加标签
3. pc，手机合并（页面自适应,手机查看的,可以[点击体验](http://jfinalbbs.com)）
4. 界面美化，使用了bootstrap-material-design
5. 编辑器升级2.0
6. 其他页面布局优化
7. 系统配置写入数据库

## 数据库更新

- `jfbbs_label` 增加几个字段,对照`doc/最新SQL.sql`里的`jfbbs_label`表的建表语句增加上就好
- `jfbbs_notification` 表结构及数据发生变化,执行下面sql脚本来更新

**注意,执行下面MySQL脚本之前先备份数据库**

```
ALTER TABLE `jfbbs_notification` ADD `action` VARCHAR(255)  NULL  DEFAULT NULL  COMMENT '通知动作'  AFTER `in_time`;
ALTER TABLE `jfbbs_notification` ADD `source` VARCHAR(32)  NULL  DEFAULT NULL  COMMENT '通知来源'  AFTER `action`;
ALTER TABLE `jfbbs_notification` CHANGE `tid` `target_id` VARCHAR(255)  CHARACTER SET utf8  COLLATE utf8_general_ci  NULL  DEFAULT NULL  COMMENT '目标id';
update jfbbs_notification set source = 'topic';
update jfbbs_notification set source = 'message' where message = '给你发了一条私信';
update jfbbs_notification set action = message;
update jfbbs_notification n set n.message = (select t.title from jfbbs_topic t where t.id = n.target_id) where n.source = 'topic';
update jfbbs_notification set target_id = concat(target_id, '#', rid) where rid is not null;
delete from jfbbs_notification where message = '';
ALTER TABLE `jfbbs_notification` DROP `rid`;
```

**更新体验[传送门](http://jfinalbbs.com)**

其他版本更新日志参见 `doc/更新日志.md`

![](http://i11.tietuku.com/6a33169a7659d90a.jpg)

以上