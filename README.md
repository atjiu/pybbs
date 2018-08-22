> 使用说明：请保留页面底部的 *powered by yiiu.co* 感谢支持

在线地址: [yiiu.co](https://yiiu.co) | [文档地址](https://tomoya92.github.io/pybbs/#/)

## 技术栈

- JDK8
- Spring-Boot
- <del>Spring-JPA</del>
- MyBatis
- Spring-Security
- Freemarker
- MySQL
- Bootstrap3
- Redis
- Elasticsearch
- netty-socketio ([详见博客](https://tomoya92.github.io/2018/08/20/spring-boot-netty-socketio/))
- jquery.pjax ([详见博客](https://tomoya92.github.io/2018/08/21/spring-boot-freemarker-pjax/))

## 特性

- 社区兼容性（IE9+）
- 页面自适应布局
- 本地登录，注册, 第三方登录（Github)
- 登录时有验证码，尝试登录次数的限制
- 使用 `Spring-Boot` 开发
- <del>使用的 `Spring-JPA` 操作数据存储</del>
- 为了性能换用MyBatis操作数据库
- <del>使用 HQL 编写sql，可以轻松切换成 Hibernate 支持的数据库</del>
- 目前sql语句只支持MySQL，如果要切换数据库，需要手动修改代码 
- Redis做缓存支持分布式
- 一键切换上传方式，可选上传本地和上传到七牛
- Elasticsearch做分词搜索
- socketio的接入实现了通知从服务端主动推送，省去了ajax的轮询
- 利用pjax实现页面无刷新访问

## 快速开始

启动视频：https://youtu.be/Q4sXXEbEhdI

#### 写在前面

1. 开发环境要在IDE里装上lombok插件，否则编译器会报错
2. 项目去掉了session，用户登录信息都存在redis里了，所以要先装好redis，默认配置的话直接启动即可，如果不装redis项目启动不会报错，但登录状态不会存储
3. 项目已经接入了elasticsearch，所以在启动前还要先安装一下es，版本5.6.0，下载地址：https://www.elastic.co/downloads/past-releases

- 创建数据库pybbs, 字符集utf8，如果想支持emoji，就要选择utf8mb4字符集（仅限使用MySQL数据库）
- `git clone https://github.com/tomoya92/pybbs`
- 运行 `mvn spring-boot:run` 启动项目 (这一步系统会自动把表创建好)
- 将项目下的 `init.sql` 文件导入到数据库
- 前台页面访问 `http://localhost:8080`
- 后台页面访问 `http://localhost:8080/admin/login` 用户名: admin 密码: 123123

## 打包部署开发环境

- 创建数据库pybbs, 字符集utf8，如果想支持emoji，就要选择utf8mb4字符集（仅限使用MySQL数据库）
- 将项目里的application.yml文件复制一份，重新命名application-prod.yml，并修改里面的配置项
- 运行 `mvn clean compile package`
- 拷贝 `target/pybbs.jar` 到你想存放的地方
- 运行 `java -jar pybbs.jar --spring.profiles.active=prod > pybbs.log 2>&1 &` 项目就在后台运行了
- 将项目下的 `init.sql` 文件导入到数据库
- 关闭服务运行 `ps -ef | grep pybbs.jar | grep -v grep | cut -c 9-15 | xargs kill -s 9`
- 查看日志运行 `tail -200f pybbs.log`

#### 启动好后可能会报404错误，两个解决办法

1. 把pom.xml里的这段代码放开注释重新打包再启动即可
```
<resource>
  <directory>views</directory>
</resource>
```
2. 把源码里的views文件夹复制到打好的jar包文件夹里（跟jar包同级）

## 添加emoji支持（仅MySQL数据库）

- 创建数据库时选择 `utf8mb4` 字符集
- 添加下面这段配置到 `/etc/mysql/mysql.conf.d/mysqld.conf` 里的 `[mysqld]` 下，保存重启Mysql服务
```
[mysqld]
character-set-client-handshake = FALSE
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
init_connect='SET NAMES utf8mb4'
```
- 如果不行，试着把pybbs也重启一下

## 关于主题

本项目配置的结构目录非常方便主题开发，如果你想适配一套自己喜欢的主题，可以按照 `views` 目录下的文件夹结构开发，然后修改一下配置文件里的 `site.theme` 的值即可打包部署了

## 反馈

[issues](https://github.com/tomoya92/pybbs/issues)

QQ群：`419343003`

*提问题的时候请将问题重现步骤描述清楚*

## 其它版本

- golang版：https://github.com/tomoya92/pybbs-go
- springboot版：https://github.com/tomoya92/pybbs
- jfinal版：https://github.com/tomoya92/pybbs/tree/v2.3
- ssm版：https://github.com/ehuacui/ehuacui-bbs

## 贡献

欢迎大家提 issues 及 pr 

感谢以下朋友的pr

[@beldon](https://github.com/beldon) [@Teddy-Zhu](https://github.com/Teddy-Zhu)

## 捐赠

![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png)
![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png)

**如果觉得这个项目对你有帮助，欢迎捐赠！**

## License

MIT
