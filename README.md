### JFinalbbs：一个是使用JFinal框架开发的社区网站
#### 线上地址：http://jfbbs.tomoya.cn/
#### 后台页面入口地址：http://你的域名/adminlogin

非常欢迎和感谢对本项目发起Pull Request的朋友，特别鸣谢以下朋友

@奋斗一生 @冯先森 @liuyuyu 

#### 项目组成：
* 前端：bootstrap + freemarker
* 后端：jfinal 
* 数据库：mysql

#### 项目运行方式：
* 将代码拉取下来
* 编译pom.xml文件，下载好jar包
* 将项目按照maven格式配置好
* 将jfinalbbs.sql脚本在mysql数据库里运行，创建jfinalbbs数据库
* 配置项目中的config.txt文件中的数据库连接
* 如果要使用第三方登录的话，需要在qq互联里新建应用，然后将应用的appid，appkey，redirect_url填好
* qq登录的回调执行方法在IndexController里，方法名：qqlogincallback，所以，回调地址的格式：http://你的域名/qqlogincallback
* 至此配置就结束了，运行一下查看效果吧！

---
### 2015年04月26日 更新 V1.1
- 社区模块后台设置
- 社区添加登录注册功能（找回密码的话需要发送邮件，这时候需要自己配置邮箱的用户名，密码，配置类：EmailSender

```java
    // 邮箱服务器
    private String host = "smtp.exmail.qq.com";
    private String username = "";
    private String password = "";
```
- 友链后台设置
- 话题后台设置置顶，精华
- 模块列表，友链列表 加缓存
- 模块，友链，后台排序（使用的是 jqueryui）

---
### 2015年06月07日 更新 V1.2
- 添加新浪微博登录
- - 需要配置weiboconfig.properties
```
client_ID=
client_SERCRET=
redirect_URI=http://你的域名/weibologincallback
```

- 添加bootstrap的一套ui，[flat-ui](http://www.bootcss.com/p/flat-ui/)
- 去掉七牛云与百度UE整合的jar包等，目前项目中只有百度UE
- 本地登录功能关掉了，代码被注释掉了，想用的将代码放开就可以了
- 最后，数据库要跟新的脚本比较一下，有可能数据库有变动

UI预览：
![](https://dn-outside.qbox.me/QQ20150607-1.png)

---
### 2015年06月30日 更新 V1.3

- 将百度UE编辑器更换成Markdown编辑器[editor.md](http://git.oschina.net/pandao/editor.md)
- - editor.md编辑器的图片上传需要将editormd.js里图片地址修改成自己的服务器访问地址
- - `imageUploadURL: "http://你的域名/qiniuupload/"`
- 增加手机浏览器打开页面适配
- 社区接口开放，详见[Api](http://jfbbs.tomoya.cn/api)
- 增加连续签到天数统计
- 增加用户设置个人信息
- 后台增加签到列表查询，方便统计当日签到量
- 后台话题列表，回复列表增加用户列，方便知道对应作者是谁
- 后台话题添加前台显示隐藏功能
- 后台模块列表添加显示隐藏功能
- 数据库脚本添加字段注释，并附带初始数据
- 修复初次设置友链报错bug

### 2015年10月08日 更新 V1.4

##### 侧边栏新增

- 无人回复话题列表
- 积分榜添加超链接
- 社区运行状态
- 当前作者的其他话题列表

#### 其他新增功能

- 本地的用户登录注册
- 用户设置里绑定/解绑 QQ，新浪微博
- 上传自定义头像
- 找回密码，发送邮件功能
- 增加我回复的话题列表

#### 更新

- 友链移动到页面底部
- 底部增加源码地址
- 话题列表添加最后回复用户的头像显示
- 话题列表排序按照最后回复时间排序（挖坟）
- 侧边栏增加小图标，是不是比以前的又好看了些^_^
- 其他一些小瑕疵的修复

#### 数据库更新
- code表修改名称为：valicode
- topic表添加字段last_reply_time，last_reply_author_id
- user表删除字段thirdlogin_type，expire_time，open_id
- user表添加字段sina_avatar，sina_open_id，sina_nickname，qq_avatar，qq_open_id，qq_nickname
- 具体详情解释请对照jfinalbbs_v1.4.sql文件


具体更新效果可以到线上体验，欢迎大家提出宝贵的意见以及建议


### 2015年10月10日 更新 V1.4.1

#### 手机端适配

- 支持手机端发布话题了
- 支持手机端修改话题
- 手机端支持登录注册了
- 手机端可以查看个人中心了
- 手机端去掉了editor_md编辑器，不过还支持markdown语法

#### 其他更新

- 修复一些bug
- 创建了2个分支，一个是editor_md编辑器的版本，一个是ueditor编辑器的版本，可以到git@osc里去查看下载

分支地址：http://git.oschina.net/20110516/jfinalbbs/branches/recent


### 2015年10月15日 V1.5更新内容

#### 前台新增
- 问答模块下采纳回复
- 精华话题盖章
![](http://i13.tietuku.com/8ec3c165cacabb0e.png)
- 回复被删除章
![](http://i13.tietuku.com/392f555f2f56726e.png)
- 回复中可以@别人了
- 发布，编辑话题可以添加标签了
- **以上功能手机端页面也都实现了**

#### 后台更新
- 首页增加了今日话题，今日回复，今日注册用户，今日签到用户的展示
![](http://i11.tietuku.com/2f77fe56f5dc06fb.png)
- 话题增加了编辑，查看话题功能
- 回复增加了查看回复功能
- 用户增加了搜索
- 增加了标签菜单

------

##### 感谢大家的支持，如果项目中遇到什么问题，欢迎联系我：[liygheart@qq.com](mailto:liygheart@qq.com)
##### 也可以到JFinalbbs群里反馈，qq群号：[419343003](http://shang.qq.com/wpa/qunwpa?idkey=c130a2aea2fa297b67d39eca4531bcf878735eecd3fe7645d49d8c7f5458147e)
##### 喜欢JFinalbbs的朋友，也可以 [捐助](http://jfbbs.tomoya.cn/donate) 朋也哦，朋也会更有动力的^_^