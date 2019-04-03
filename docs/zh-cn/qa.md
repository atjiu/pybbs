> 这里整理一下常见的问题，用前请务必先阅读完

## 数据库在哪？

启动项目只需要配置好数据源连接地址，用户名，密码，其它都不用管，如果数据源连接信息都配置的对，启动还是报错，请查看你配置的MySQL用户是否有创建数据库以及表和对表的CRUD的权限

## 上传的图片为啥不显示

首先这是个只会在用IDEA开发启动时出现的问题，原因是IDEA在启动SpringBoot项目的时候会把项目中的 `resources` 加载到内存里，pybbs中的`static`文件夹在resources里

所以项目启动之后，再上传到`static`文件夹里的文件就不会被idea自动加载了，这时候只需要在idea里build一下项目即可，不用重启

## 后台用户名密码是多少？

文档里有

## 启动项目时报错 `java.lang.IllegalStateException: Failed to execute CommandLineRunner`

这个错误一般是主题文件夹没有找到的错，可以参考一下这篇文章，如果是一样的错就对了 [https://17dev.club/article/5c98adb7bbe14024b9e067b3](https://17dev.club/article/5c98adb7bbe14024b9e067b3)

如果你是按照文档上的`快速开始`来启动的，就不会出现这个问题，开发环境加载的是`resources/templates`下的主题文件夹，正式环境加载的是 `./templates/theme`

所以解决这个问题的方法就是区分开你是正式环境还是部署环境启动的

## 打包启动报错，找不到jar包

如果不想用github上我提供的 release 里的包，可以自行打包，不过打包要注意，我配置了`assembly` 打包，请使用下面的命令进行打包

`mvn clean assembly:assembly`

## redis配置失败

redis请不要开启auth，程序内集成的代码没有支持auth的配置

如果你非要支持auth选项，可以自行修改源码，源码类名是 `RedisService.java`

## 启动时报错 `No timezone mapping entry for 'GMT 8'`

这是MySQL时区的问题，只在windows上有问题，我本机测试是把数据源里url链接后面的 `&serverTimezone=GMT%2B8` 删了就可以了，不过也有用户反馈这种方法不行

那就换成另一种写法 `&serverTimezone=Asia/Shanghai` 也是可以的
