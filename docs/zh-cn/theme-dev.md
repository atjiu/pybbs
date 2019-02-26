**开发主题，首先要会使用freemarker模板引擎**

必要说明：

- 主题开发好了，需要放在 `src/main/resources/templates/theme` 下，系统启动会自动读取主题目录，然后在后台就可以选择你想要的主题了
- 如果你不是开发环境，那需要将开发好的或者下载的主题文件夹放在 `pybbs.jar` 所在目录下的 `templates/theme` 文件夹下，如果这两个文件夹没有，需要你创建好
- 开发主题肯定需要引入样式，建议使用 `cdnjs.com` 的外链，但也有一些对系统内的样式，这个样式文件需要放在 `pybbs.jar` 目录所在文件夹下的 `templates/static/theme/css` 文件夹下，js同理

## 主题目录

主题必备目录有以下几个

```
simple
├── comment
├── tag
├── topic
└── user
```

必备的文件有如下

```
simple
├── comment
│   └── edit.ftl
├── error.ftl
├── index.ftl
├── login.ftl
├── notifications.ftl
├── register.ftl
├── search.ftl
├── tag
│   ├── tag.ftl
│   └── tags.ftl
├── top100.ftl
├── topic
│   ├── create.ftl
│   ├── detail.ftl
│   └── edit.ftl
└── user
    ├── collects.ftl
    ├── comments.ftl
    ├── profile.ftl
    ├── settings.ftl
    └── topics.ftl
```

**注意，上面列出来的文件夹和文件都是必备的**

如果你习惯封装，可以跟default主题里一样，把通用的代码块封装成component，放在一个文件夹里（比如 default主题里的组件文件夹就是 components 当然这个不是必须的）

## 全局对象

通俗点说就是在所有页面里都能取到的数据的对象，有如下几个


| 对象名 | 描述                                                                                                  |
| ------ | ----------------------------------------------------------------------------------------------------- |
| model  | 这个对象就是项目中类 BaseModel 的对象，所以使用这个对象可以调用BaseModel类中的所有方法                |
| _user  | 这个对象是用户在登录或者注册成功后，将当前用户的对象存放在Session中的对象，只有在用户登录后才能获取到 |
| site   | 这个对象就是后台系统配置中的那一堆东西                                                                |
| i18n   | 如果开发主题里要适配上国际化，可以使用这个对象来获取一些国际化菜单的不同语言的不同值                  |

### model对象内的方法

```java
String formatDate(Date date);
String formatContent(String content);
Set<String> getUpIds(String upIds);
boolean isEmpty(String txt);
```

### site对象中的属性

在页面里取site里的属性方法 `${site.name!}`

属性的类型全都是 String，可根据自己需要将值转成 int 或者 boolean

| 属性                         | 描述                                                                                          |
| ---------------------------- | --------------------------------------------------------------------------------------------- |
| admin_remember_me_max_age    | 登录后台记住我功能记住时间，单位：天                                                          |
| base_url                     | 网站部署后访问的域名，注意这个后面没有 "/"                                                    |
| comment_layer                | 评论盖楼形式显示                                                                              |
| cookie_domain                | 存cookie时用到的域名，要与网站部署后访问的域名一致                                            |
| cookie_max_age               | cookie有效期，单位秒，默认1周                                                                 |
| cookie_name                  | 存cookie时用到的名称                                                                          |
| intro                        | 站点介绍                                                                                      |
| name                         | 站点名称                                                                                      |
| page_size                    | 分页每页条数                                                                                  |
| topic_view_increase_interval | 同一个用户浏览同一个话题多长时间算一次浏览量，默认10分钟，单位秒（只有当配置了redis才会生效） |
| theme                        | 系统主题                                                                                      |
| mail_host                    | 邮箱的smtp服务器地址                                                                          |
| mail_password                | 发送邮件的邮箱密码                                                                            |
| mail_username                | 发送邮件的邮箱地址                                                                            |
| static_url                   | 静态文件访问地址，主要用于上传图片的访问，注意最后有个"/"                                     |
| upload_avatar_size_limit     | 上传头像文件大小，单位MB，默认2MB                                                             |
| upload_path                  | 上传文件的路径，注意最后有个"/"                                                               |
| create_comment_score         | 发布评论奖励的积分                                                                            |
| create_topic_score           | 创建话题奖励的积分                                                                            |
| delete_comment_score         | 删除评论要被扣除的积分                                                                        |
| delete_topic_score           | 删除话题要被扣除的积分                                                                        |
| up_comment_score             | 点赞评论奖励评论作者的积分                                                                    |
| up_topic_score               | 点赞话题奖励话题作者的积分                                                                    |
| redis_host                   | redis服务host地址                                                                             |
| redis_port                   | redis服务端口（默认: 6379）                                                                   |
| redis_password               | redis服务密码                                                                                 |
| redis_timeout                | 网站连接redis服务超时时间，单位毫秒                                                           |
| redis_database               | 网站连接redis服务的哪个数据库，默认0号数据库，取值范围0-15                                    |
| redis_ssl                    | redis服务是否开启认证连接                                                                     |
| elasticsearch_host           | elasticsearch服务的地址                                                                       |
| elasticsearch_port           | elasticsearch服务的http端口                                                                   |
| elasticsearch_index          | 索引的名字                                                                                    |
| search                       | 是否开启搜索功能（如果开启，需要额外启动一个ES服务，并填好ES相关的配置）                      |
| oauth_github_client_id       | Github登录配置项ClientId                                                                      |
| oauth_github_client_secret   | Github登录配置项ClientSecret                                                                  |
| oauth_github_callback_url    | Github登录配置项回调地址                                                                      |
| websocket                    | 是否开启websocket功能                                                                         |
| websocket_host               | websocket服务的主机名，这个跟cookie的域名设置成一样的就可以了                                 |
| websocket_port               | websocket服务的端口，不能跟论坛服务端口一样，其它随便设置                                     |

### i18n对象中的属性

在页面中获取的方法是 `${i18n.getMessage("index")}`

| 属性                    | 描述         |
| ----------------------- | ------------ |
| index                   | 首页         |
| tag                     | 标签         |
| search                  | 搜索         |
| login                   | 登录         |
| github_login            | Github登录   |
| register                | 注册         |
| notification            | 通知         |
| setting                 | 设置         |
| logout                  | 登出         |
| welcome                 | 欢迎您       |
| admin.dashboard         | 仪表盘       |
| admin.topics            | 话题列表     |
| admin.comments          | 评论列表     |
| admin.tags              | 标签列表     |
| admin.users             | 用户列表     |
| admin.permission_config | 权限中心     |
| admin.admin_users       | 后台用户列表 |
| admin.roles             | 角色列表     |
| admin.permissions       | 权限列表     |
| admin.system_config     | 系统设置     |

## Freemarker自定义标签

pybbs提供了如下几个自定义标签

| 标签名             | 描述           | 参数                       | 返回的对象(类型)                         |
| ------------------ | -------------- | -------------------------- | ---------------------------------------- |
| tag_topics         | 话题列表       | pageNo, tab                | page(Page<Map<String, Object>>)          |
| tag_other_topic    | 作者其它话题   | userId, topicId, limit     | topics(List<Topic>)                      |
| tag_notifications  | 通知列表       | userId, read, limit        | notifications(List<Map<String, Object>>) |
| tag_score          | 积分排行       | limit                      | users(List<User>)                        |
| tag_search         | 搜索结果列表   | keyword, pageNo            | page(Page<Map<String, Object>>)          |
| tag_tags           | 标签列表       | pageNo, pageSize           | page(Page<Tag>)                          |
| tag_user_topics    | 用户的话题列表 | username, pageNo, pageSize | topics(Page<Map<String, Object>>)        |
| tag_user_comments  | 用户的评论列表 | username, pageNo, pageSize | comments(Page<Map<String, Object>>)      |
| tag_user_collects  | 用户的收藏列表 | username, pageNo, pageSize | collects(Page<Map<String, Object>>)      |
| tag_topic_comments | 话题的评论列表 | topicId                    | comments(List<CommentsByTopic>)          |

在标签返回对象里有一些不是定义的model里的对象，而是Map封装的对象，这些map里都有啥呢？

### 标签 tag_topics 对象中的Map包含的字段

- Topic t.*: Topic对象里的所有字段
- username: 用户名
- avatar: 用户头像

### 标签 tag_notifications 对象中的Map包含的字段

- Notification n.*: Notification对象里的所有字段
- username: 用户名
- avatar: 用户头像
- title: 话题标题
- topicId: 话题ID

### 标签 tag_search 对象中的Map包含的字段

- id: 话题ID
- title: 话题标题
- content: 话题内容

### 标签 tag_user_topics 对象中的Map包含的字段

- Topic t.*: Topic对象里的所有字段
- username: 用户名
- avatar: 用户头像

### 标签 tag_user_comments 对象中的Map包含的字段

- Comment c.*: Comment对象里的所有字段
- topicUsername: 话题的用户名
- commentUsername: 话题的用户名
- title: 话题标题
- topicId: 话题ID

### 标签 tag_user_collects 对象中的Map包含的字段

- Topic t.*: Topic对象里的所有字段
- username: 用户名
- avatar: 用户头像

## 自定义标签使用

自定义标签用法很简单，不会用的话，可以参考已经存在的主题里的用法，下面说一下首页的 tag_topics 标签的用法

```
<@tag_topics pageNo=pageNo tab=tab>
  // tag_topics 里的两个参数都是从controller里传过来的
  // 在标签内部就可以拿到自定义标签返回的对象了，比如这个标签返回的就一个page对象
  <#list page.records as topic>
    <p>${topic.title}</p>
  </#list>
</@tag_topics>
```

至于标签里返回的对象都是什么东西，下面介绍，先说说每个路由渲染的页面里都能取出什么东西吧

## 路由渲染可获取对象

| 地址                      | 类名              | 参数             | 放在model中对象                                       | 渲染视图文件名                           |
| ------------------------- | ----------------- | ---------------- | ----------------------------------------------------- | ---------------------------------------- |
| /                         | IndexController   | tab, pageNo      | tab, pageNo                                           | index.ftl                                |
| /top100                   | IndexController   |                  |                                                       | top100.ftl                               |
| /settings                 | IndexController   |                  | user                                                  | user/settings.ftl                        |
| /tags                     | IndexController   | pageNo           | pageNo                                                | tag/tags.ftl                             |
| /login                    | IndexController   |                  |                                                       | login.ftl                                |
| /register                 | IndexController   |                  |                                                       | register.ftl                             |
| /notifications            | IndexController   |                  |                                                       | notifications.ftl                        |
| /logout                   | IndexController   |                  |                                                       | 重写向到首页                             |
| /search                   | IndexController   | pageNo, keyword  | pageNo, keyword                                       | search.ftl                               |
| /changeLanguage           | IndexController   | lang: zh, cn     |                                                       | 重写向到之前页面                         |
| /user/{username}          | UserController    | username         | githubLogin, user, username, oAuthUsers, collectCount | user/profile.ftl                         |
| /user/{username}/topics   | UserController    | username, pageNo | username, pageNo                                      | user/topics.ftl                          |
| /user/{username}/comments | UserController    | username, pageNo | username, pageNo                                      | user/comments.ftl                        |
| /user/{username}/collects | UserController    | username, pageNo | username, pageNo                                      | user/collects.ftl                        |
| /topic/{id}               | TopicController   | id               | collect, topic, tags, topicUser, collects             | topic/detail.ftl                         |
| /topic/create             | TopicController   | tag              | tag                                                   | topic/create.ftl                         |
| /topic/edit/{id}          | TopicController   | id               | topic, tags                                           | topic/edit.ftl                           |
| /topic/tag/{name}         | TopicController   | name             | tag, page                                             | tag/tag.ftl                              |
| /comment/edit/{id}        | CommentController | id               | comment, topic                                        | comment/edit.ftl                         |
| /common/captcha              | CommonController  |                  |                                                       | 响应的是一张图片验证码的流               |
| /oauth/github             | OAuthController   |                  |                                                       | 重写向到Github授权页面，授权完成自动回调 |

## 对象包含的字段

### 分页对象 Page

这个对象是Mybatis-Plus里封装的，常用字段有以下几个

- List records: 查询出来的列表放在这个里面，类型是个List
- long current: 当前是第几页，从1开始
- long total: 总条数
- long pages: 总页数
- long size: 每页显示条数

### 用户对象 User

```java
private Integer id;
private String username;
private String telegramName;
private String avatar;
private String password;
private String email;
// 个人网站
private String website;
// 个人简介
private String bio;
private Integer score;
private Date inTime;
private String token;
// 有消息通知是否通过邮箱收取
private Boolean emailNotification;
```

### 话题对象 Topic

```java
private Integer id;
private String title;
private String content;
private Date inTime;
private Date modifyTime;
private Integer userId;
// 评论数
private Integer commentCount;
// 收藏数
private Integer collectCount;
// 浏览数
private Integer view;
// 置顶
private Boolean top;
// 加精
private Boolean good;
// 点赞用户的id英文,隔开的，要计算被多少人点赞过，可以通过英文,分隔这个字符串计算数量
private String upIds;
```

### 评论对象 Comment

```java
private Integer id;
private Integer topicId;
private Integer userId;
private String content;
private Date inTime;
private Integer commentId;
// 点赞用户的id
private String upIds;
```

### 评论(盖楼)对象 CommentsByTopic

```java
// 话题下面的评论列表单个对象的数据结构
public class CommentsByTopic extends Comment implements Serializable {

  private String username;
  private String avatar;
  // 评论的层级，直接评论话题的，layer即为0，如果回复了评论的，则当前回复的layer为评论对象的layer+1
  private Integer layer;
}
```

### 通知对象 Notification

```java
private Integer id;
private Integer topicId;
private Integer userId;
// 通知对象ID
private Integer targetUserId;
// 动作: REPLY, COMMENT, COLLECT, TOPIC_UP, COMMENT_UP
private String action;
private Date inTime;
private String content;
// 是否已读
private Boolean read;
```

### 授权登录对象 OAuthUser

```java
private Integer id;
// oauth帐号的id
private Integer oauthId;
// 帐号类型，GITHUB, QQ, WECHAT, WEIBO 等
private String type;
// oauth帐号的登录名
private String login;
private String accessToken;
private Date inTime;
// 个人简介
private String bio;
private String email;
// 本地用户的id
private Integer userId;
```
