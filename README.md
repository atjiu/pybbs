> 非商业使用：请保留页面底部的 powered by [朋也社区](http://bbs.tomoya.cn)

> 商业使用：请联系[朋也](https://github.com/liygheart)

## 使用技术

- JFinal
- freemarker
- mysql
- bootstrap
- ehcache

## 特性

- 社区兼容性（IE9+）
- 不用session,选用cookie,为了集群方便
- 大量使用了缓存（redis）减轻Mysql数据库的压力, 集群更加方便
- 不做本地账户,不会出现用户密码泄露问题（目前就Github登录,后续可以陆续增加其他第三方登录）
- 权限配置简单,轻松管理用户
- 使用textarea作为编辑器,配置快捷键,书写更方便,ie11+/chrome/firefox/safari 还支持截图粘贴上传

## 如何开始

#### 服务器环境部署

- 安装redis
- `git clone https://github.com/liygheart/pybbs.git`
- 将`pybbs.sql`脚本在mysql数据库里运行，创建pybbs数据库
- 修改`src/main/resources`下的`config.properties`文件里的配置信息
- 使用maven命令:`mvn clean package`,等待编译打包,成功后打开target文件夹,会有一个pybbs.war
- 重命名 `pybbs.war` 为 `ROOT.war`
- [下载tomcat](http://tomcat.apache.org),解压
- 删除 tomcat/webapp 下的所有文件/文件夹
- 将war包放到`tomcat/webapp`下,启动tomcat(`./bin/startup.sh`)
- 浏览器访问`http://localhost:8080/`

#### 开发环境部署

- 安装redis
- `git clone https://github.com/liygheart/pybbs.git`
- 推荐使用 IDEA 打开项目
- 运行`AppConfig.java`类里的main方法
- 浏览器访问`http://localhost:8080/`

## 权限设置

`pybbs.sql`里默认配置了三个角色

- 超级管理员
- 版主
- 普通用户

通过 Github 登录的用户默认都是普通用户，在sql脚本里用户与角色配置管理表默认初始化了一条数据，即登录用户在pybbs_user表里的id为1的用户默认会是超级管理员权限

**如果想指定超级管理员权限给特定用户怎么办？**

只能手动修改数据库了，这个朋也想过做成可配置的，但一直没有好的思路或者实现方法，就只好麻烦手动修改下数据库了

只需要在 pybbs_user_role 里添加一条关联数据即可，比如想给用户id为7的用户设置超级管理员权限，就在 pybbs_user_role 表里添加一条数据rid填1，uid填7即可，如果没有生效，请将 redis 的数据清理一下

## 碰到问题怎么办?

1. 到 [http://bbs.tomoya.cn](http://bbs.tomoya.cn) 上提问答
2. 在Github上提 [Issues](https://github.com/liygheart/pybbs/issues)
3. 提问题的时候请将问题重现步骤描述清楚

## 贡献

欢迎大家提pr

## License

AGPL
