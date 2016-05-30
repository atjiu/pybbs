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
- 大量使用了缓存（ehcache）减轻服务器压力（后续还会改成redis作为缓存,集群更加方便）
- 不做本地账户,不会出现用户密码泄露问题（目前就Github登录,后续可以陆续增加其他第三方登录）
- 权限配置简单,轻松管理用户
- 使用textarea作为编辑器,配置快捷键,书写更方便,ie11+/chrome/firefox/safari 还支持截图粘贴上传

## 如何开始

#### 服务器环境部署

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

- `git clone https://github.com/liygheart/pybbs.git`
- 推荐使用 IDEA 打开项目
- 运行`AppConfig.java`类里的main方法
- 浏览器访问`http://localhost:8080/`

## 碰到问题怎么办?

1. 到 [http://bbs.tomoya.cn](http://bbs.tomoya.cn) 上提问答
2. 在Github上提 [Issues](https://github.com/liygheart/pybbs/issues)
3. 提问题的时候请将问题重现步骤描述清楚

## 贡献

欢迎大家提pr

## License

MIT
