> 本项目是从 [pybbs](https://github.com/tomoya92/pybbs) fork 过来的
> 
> 使用说明：请保留页面底部的 *powered by yiiu.co* 感谢支持

在线地址: [yiiu.co](https://yiiu.co) | [文档地址](https://yiiu-co.github.io/yiiu/#/)

## 技术栈

- JDK8
- Spring-Boot1.5.8
- Spring-JPA
- Freemarker
- MySQL
- Bootstrap3
- Redis

## 特性

- 社区兼容性（IE9+）
- 页面自适应布局
- 本地登录，注册
- 登录时有验证码，尝试登录次数的限制
- 使用 `Spring-Boot` 开发
- 使用的 `Spring-JPA` 操作数据存储
- 使用 HQL 编写sql，可以轻松切换成 Hibernate 支持的数据库
- Redis做缓存支持分布式
- 一键切换上传方式，可选上传本地和上传到七牛

## 快速开始

#### 写在前面

1. 开发环境要在IDE里装上lombok插件，否则编译器会报错
2. 项目去掉了session，用户登录信息都存在redis里了，所以要先装好redis，默认配置的话直接启动即可，如果不装redis项目启动不会报错，但登录状态不会存储
3. 项目已经接入了elasticsearch，所以在启动前还要先安装一下es，版本5.6.0，下载地址：https://www.elastic.co/downloads/past-releases

*数据库里的表是项目启动时自动创建的，不要再问创建表的脚本在哪了*

- 创建数据库yiiu, 字符集utf8，如果想支持emoji，就要选择utf8mb4字符集（仅限使用MySQL数据库）
- `git clone https://github.com/yiiu-co/yiiu`
- 运行 `mvn spring-boot:run` 启动项目 (这一步系统会自动把表创建好)
- 将项目下的 `init.sql` 文件导入到数据库
- 前台页面访问 `http://localhost:8080`
- 后台页面访问 `http://localhost:8080/admin/login` 用户名: admin 密码: 123123

## 打包部署开发环境

- 创建数据库yiiu, 字符集utf8，如果想支持emoji，就要选择utf8mb4字符集（仅限使用MySQL数据库）
- 将项目里的application.yml文件复制一份，重新命名application-prod.yml，并修改里面的配置项
- 运行 `mvn clean compile package`
- 拷贝 `target/yiiu.jar` 到你想存放的地方
- 运行 `java -jar yiiu.jar --spring.profiles.active=prod > yiiu.log 2>&1 &` 项目就在后台运行了
- 将项目下的 `init.sql` 文件导入到数据库
- 关闭服务运行 `ps -ef | grep yiiu.jar | grep -v grep | cut -c 9-15 | xargs kill -s 9`
- 查看日志运行 `tail -200f yiiu.log`

#### 启动好后可能会报404错误，两个解决办法

1. 把pom.xml里的这段代码放开注释重新打包再启动即可
```
<resource>
  <directory>views</directory>
</resource>
```
2. 把源码里的views文件夹复制到打好的jar包文件夹里（跟jar包同级）

windows上启动脚本参见 [传送门](https://github.com/yiiu-co/yiiu/wiki/windows上的启动脚本)

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
- 如果不行，试着把yiiu也重启一下

## 关于主题

本项目配置的结构目录非常方便主题开发，如果你想适配一套自己喜欢的主题，可以按照 `views` 目录下的文件夹结构开发，然后修改一下配置文件里的 `site.theme` 的值即可打包部署了

后续我也会适配一些好看的主题放到组织 `yiiu-co` 里

## 配置邮箱

我只配置了QQ邮箱，按照下面配置方法配置是没有问题的

```
mail:
  host: smtp.qq.com # 如果是企业邮箱这里要改成 smtp.exmail.qq.com
  username: xxoo@qq.com # 你的QQ邮箱地址
  password: # 这里的密码是QQ邮箱的授权码
  port: 465
  properties:
    mail.smtp.auth: true
    mail.smtp.ssl.enable: true
    mail.smtp.starttls.enable: true
    mail.smtp.starttls.required: true
```

## 反馈

[issues](https://github.com/yiiu-co/yiiu/issues)

QQ群：`419343003`

*提问题的时候请将问题重现步骤描述清楚*

## 其它版本

- golang版：https://github.com/tomoya92/pybbs-go
- springboot版：https://github.com/yiiu-co/yiiu
- jfinal版：https://github.com/tomoya92/pybbs/tree/v2.3
- ssm版：https://github.com/ehuacui/ehuacui-bbs

## 贡献

欢迎大家提 issues 及 pr 

感谢以下朋友的pr

[@beldon](https://github.com/beldon) [@Teddy-Zhu](https://github.com/Teddy-Zhu)

## License

MIT
