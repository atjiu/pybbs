##### JFinal社区：一个是使用JFinal框架开发的社区网站
##### 线上地址：http://jfinalbbs.liygheart.com/
##### 后台页面入口地址：http://你的域名/adminlogin
##### 项目组成：
* 前端：bootstrap + freemarker
* 后端：jfinal 
* 数据库：mysql

###### 项目运行方式：
* 将代码拉取下来
* 编译pom.xml文件，下载好jar包
* 将项目按照maven格式配置好
* 将jfinalbbs.sql脚本在mysql数据库里运行，创建jfinalbbs数据库
* 配置项目中的config.txt文件中的数据库连接
* 如果要使用第三方登录的话，需要在qq互联里新建应用，然后将应用的appid，appkey，redirect_url填好
* qq登录的回调执行方法在IndexController里，方法名：qqlogincallback，所以，回调地址的格式：http://你的域名/qqlogincallback
* 至此配置就结束了，运行一下查看效果吧！

###### 如果项目中遇到什么问题，欢迎联系我：liygheart@qq.com
###### 也可以到JFinal社区群里反馈，qq群号：419343003
