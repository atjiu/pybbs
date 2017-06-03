## 如何开始

### 开发环境部署

1. `git clone https://github.com/tomoya92/pybbs`
2. 使用`idea` or `eclipse`打开项目
3. 等待maven构建完成
4. 运行 `PybbsApplication.java` 类
5. 访问 `http://localhost:8080`

### 生产环境部署

1. 新建文件夹 `pybbs`
2. [下载最新jar包](https://github.com/tomoya92/pybbs/releases) 并放到 `pybbs` 文件夹下
3. [下载最新源码包](https://github.com/tomoya92/pybbs/releases) 放到除 `pybbs` 以外的任意目录下并解压
4. 拷贝源码包下的 `src/main/resources/application.yml` 文件到 `pybbs` 目录下
5. 修改 `application.yml` 文件里的配置项，可参考下面**配置文件说明修改**
6. 运行 `java -jar pybbs.jar` 
7. 访问 `http://localhost:8080`

## 配置文件说明

freemarker页面缓存开启关闭

```yml
spring:
  freemarker:
    cache: true ## true表示访问的页面开启缓存, false表示不开启缓存, 开启了缓存, 修改页面后会有一段时间不生效, 对流量小的网站, 建议关闭
```

数据库连接, 没什么好说的，配置成自己的数据库连接信息就可以了

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost/pybbs?useSSL=false&characterEncoding=utf8
    username: root
    password: 123123
  jpa:
    database: mysql ## 朋也社区用的是jpa操作数据库，默认使用mysql，理论上hibernate支持的数据库，pybbs都支持，我没有其它数据库，就没测试
    show-sql: true ## 日志文件里是否显示sql，生产环境关闭就可以了
```

站点信息配置

```yml
site:
  name: 朋也社区
  intro: <h5>属于Java语言的bbs</h5><p>在这里，您可以提问，回答，分享，诉说，这是个属于Java程序员的社区，欢迎您的加入！</p>
  baseUrl: http://localhost:8080/
  staticUrl: http://localhost:8080/static/images/upload/
  pageSize: 20
  uploadPath: ./static/images/upload/
  theme: default
  editor: wangeditor
  search: true
  attempts: 5
  attemptsWaitTime: 15
```

| 名称 | 说明 |
| --- | --- |
| name | 站点的名称 |
| intro | 末登录站点，显示的欢迎语 |
| baseUrl | 站点的访问域名，注意后面必须要带上 `/` |
| staticUrl | 站点的静态资源文件地址，配置规则 `baseUrl`+`static/images/upload/`注意后面必须要带上 `/` |
| pageSize | 站点列表分页的条数，修改所有的分页都会变 |
| uploadPath | **除非你知道怎么修改，否则不要修改它** |
| theme | 主题名称, 默认 `default` 如果你安装其它主题或者自己写主题，请不要跟已有主题名称重复 |
| editor | 可填 `wangeditor` `markdown` 新建/编辑话题以及回复用的编辑器，默认`wangeditor` |
| search | 是否开启搜索，目前搜索用的是模糊查询，效率会比较低 |
| attempts | 用户登录失败可尝试的次数，超过了，帐号会被锁定 `attemptsWaitTime` 时间， 默认5次 |
| attemptsWaitTime | 用户登录失败 `attempts` 次以后，帐号被锁定的时间，默认15, 单位分钟|

## 问题列表

### 上传图片显示问题

开发环境自己配置一下上传的地址 `uploadPath` 就可以了，要配置到项目编译文件目录 `target` 下，而且上传完了，重启一下服务

生产环境下是没问题的

