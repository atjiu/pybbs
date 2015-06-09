### JFinal社区：一个是使用JFinal框架开发的社区网站
#### 线上地址：http://jfbbs.tomoya.cn/
#### 后台页面入口地址：http://你的域名/adminlogin
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
### 2015年04月26日 更新
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
### 2015年06月07日 更新
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

#### 感谢大家的支持，如果项目中遇到什么问题，欢迎联系我：[liygheart@qq.com](mailto:liygheart@qq.com)
#### 也可以到JFinal社区群里反馈，qq群号：[419343003](http://shang.qq.com/wpa/qunwpa?idkey=c130a2aea2fa297b67d39eca4531bcf878735eecd3fe7645d49d8c7f5458147e)
