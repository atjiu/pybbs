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

## Freemarker自定义标签

pybbs提供了如下几个自定义标签

- tag_topics 话题列表，参数: pageNo, tab, 返回对象: page
- tag_other_topic 作者其它话题，参数: userId, topicId, limit, 返回对象: topics
- tag_notifications 通知列表，参数: userId, read, limit, 返回对象: notifications
- tag_score 积分排行，参数: limit, 返回对象: users
- tag_search 搜索结果列表，参数: keyword, pageNo, 返回对象: page
- tag_tags 标签列表，参数: pageNo, pageSize, 返回对象: page
- tag_user_topics 用户的话题列表，参数: username, pageNo, pageSize, 返回对象: topics
- tag_user_comments 用户的评论列表，参数: username, pageNo, pageSize, 返回对象: comments
- tag_user_collects 用户的收藏列表，参数: username, pageNo, pageSize, 返回对象: collects
- tag_topic_comments 话题的评论列表，参数: topicId, 返回对象: comments

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

至于标签里返回的对象都是什么东西，下面介绍


