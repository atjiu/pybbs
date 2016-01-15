### 项目运行说明

#### 社区是一个maven项目

- 使用idea开发，所以使用idea开发的朋友可以直接fork一份代码，然后将自己git库里的代码clone下来就可以部署在idea里开发了，很方便
- 使用eclipse/myeclipse的朋友，可以参照线上的一篇文章http://jfbbs.tomoya.cn/topic/ad58a1e050ab4ab7a54988490d97c40d.html 来部署，或者直接在本地新建一个普通的java web项目，然后将代码拷贝到对应的地方就可以了（推荐这种方式）
- 修改config.properties项目配置文件，打开将对应的项修改成本机的地址信息即可
- 修改第三方登录的配置文件qqconnectconfig.properties，weiboconfig.properties，将key，secret配置好
- 关于服务器，如果是eclipse的话，强烈建议选择tomcat作为服务器，如果是idea开发的话，可以使用maven run，也可以配置tomcat发布到tomcat里运行

#### 常见错误

- 项目起来了，但打开首页，样式全飞了。解决办法：找到config.properties文件，将baseUrl修改成自己目前访问首页的浏览器地址栏里的地址，重启服务器重新访问即可

#### 其他

- 关于项目上的其他错误等，都可以在git@osc里提issues，也可以到线上去提问答（强烈建议）
- 你对于JFinal社区有好的想法，特别想增加上某项功能，欢迎给我发邮件 liygheart@qq.com
- 你将社区代码fork了一份到自己的git@osc库里，然后修改了或者增加了某项功能，也欢迎pull request到master分支上来，朋也会审核查看，通过了就会整合到master分支里去的


**JFinal社区是一个完全开源的项目，不会收取任何费用，旨在搭建属于Java语言的社区系统，为广大爱好社区的朋友提供便利**