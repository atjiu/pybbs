> 使用说明：请保留页面底部的 powered by [朋也社区](http://bbs.tomoya.cn)

## 使用技术

- jdk1.8
- tomcat8
- JFinal
- freemarker
- mysql
- bootstrap
- redis
- solr6.0.1

## 特性

- 社区兼容性（IE9+）
- 不用session,选用cookie,为了集群方便
- 大量使用了缓存（redis）减轻Mysql数据库的压力, 集群更加方便
- 不做本地账户,不会出现用户密码泄露问题（已支持: Github登录, 新浪微博登录）
- 权限配置简单,轻松管理用户
- 使用 [editor](https://github.com/lepture/editor) 作为 Markdown编辑器, 书写更方便, 还支持截图粘贴上传
- 使用solr来检索,速度更快,配置文件里可一键开关,方便使用

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

## 开启搜索功能

- 修改`config.properties` 里的solr相关配置

    ```
    # solr开关,true:开启,需要配置下面solr的相关参数,false:关闭
    solr.status=true
    # solr
    solr.url=http://localhost:8080/solr/pybbs
    solr.pageSize=20
    ```

- `git clone https://github.com/tomoya92/solr.git` 这个是配置好ik分词器的solr
- 启动solr服务
- 启动pybbs服务
- 打开首页，会发现出现一个搜索框，赶快试试吧

## 开启七牛上传

- 修改`config.properties` 里的qiniu相关配置

    ```
    # 上传类型(只能填qiniu或者local,如果选择qiniu的话,请完善七牛的相关配置信息): qiniu, local
    upload.type=local
    # 七牛
    qiniu.access_key=
    qiniu.secret_key=
    qiniu.url=
    qiniu.bucketname=
    ```
    
- 重启服务

## 第三方登录配置

- 修改`config.properties` 里的第三方登录相关配置

    ```
    # 第三方登录渠道,目前支持 Github, Weibo 如果此项不填,默认是Github登录
    # 选择渠道后,请配置好相关渠道的配置信息
    login.channel=
    # github login
    github.client_id=
    github.client_secret=
    # weibo login
    weibo.meta=
    weibo.appkey=
    weibo.appsecret=
    weibo.redirecturi=
    ```

- 重启服务

**要检索网站所有话题，需要配合权限部分给当前账户开启 索引所有话题(慎用) 的权限**

## 权限设置

`pybbs.sql`里默认配置了三个角色

- 超级管理员
- 版主
- 普通用户

通过 Github 登录的用户默认都是普通用户，在sql脚本里用户与角色配置管理表默认初始化了一条数据，即登录用户在pybbs_user表里的id为1的用户默认会是超级管理员权限

**如果想指定超级管理员权限给特定用户怎么办？**

只能手动修改数据库了，这个朋也想过做成可配置的，但一直没有好的思路或者实现方法，就只好麻烦手动修改下数据库了

只需要在 pybbs_user_role 里添加一条关联数据即可，比如想给用户id为7的用户设置超级管理员权限，就在 pybbs_user_role 表里添加一条数据rid填1，uid填7即可，如果没有生效，请将 redis 的数据清理一下

超级管理员账户设置里的权限菜单：

```
板块管理
回复管理
用户管理
角色管理
权限管理
索引所有话题(慎用)
删除所有索引
删除所有缓存
```

**如果你搭建起来的 朋也社区 没有这么多，那就是权限没配置好，如果比这多，那是不可能的 :-)**

## 碰到问题怎么办?

1. 到 [https://bbs.tomoya.cn](https://bbs.tomoya.cn) 上提问答
2. 在Github上提 [Issues](https://github.com/tomoya92/pybbs/issues)
3. 提问题的时候请将问题重现步骤描述清楚
4. 加QQ群：419343003

## 贡献

欢迎大家提pr

## License

MIT
