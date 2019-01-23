程序内置了elasticsearch功能，不过要增加相应的配置才能用

此功能默认是关闭的，具体的配置方法如下

1. 下载elasticsearch,版本建议 6.5.3 我用的就是这个版本做的开发的
2. 安装ik分词插件，如果你懂命令行操作,可以执行这条命令 `cd elasticsearch-6.5.3/bin && ./elasticsearch-plugin https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.5.3/elasticsearch-analysis-ik-6.5.3.zip`
3. 启动程序，进入后台，打开系统设置
4. 具体操作如下图所示

打开搜索功能的开关

![](../assets/QQ20190103-135005.png)

配置ES的连接(连接信息根据自己环境做相应的修改，不一定是图上所示的配置)

![](../assets/QQ20190103-135046.png)
