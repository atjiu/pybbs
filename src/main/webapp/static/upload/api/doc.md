# 如何开始
1. clone代码,maven编译打成war包,或者下载war包 链接: http://pan.baidu.com/s/1nuxPAkh 密码: ebwj
2. 下载tomcat,解压
3. 将war包放到`tomcat/webapp`下,启动tomcat(`./bin/startup.sh`)
4. 修改webapp下的`jfinalbbs/WEB-INF/classes/config.properties`文件的配置,怎么配置,打开看看就明白了,很简单
5. 重启tomcat
6. 打开浏览器,输入`http://localhost:8080/jfinalbbs`回车
7. 后台访问路径`http://localhost:8080/jfinalbbs/admin/index`

# 出问题怎么办?
1. 加QQ群 419343003 ,阅读群公告后可以提问题
2. 在Github上提 Issues
3. 提问题的时候请将问题重现步骤描述清楚,对于截个图什么也不说,发一句话就了事的,我会看心情回答(你懂的!)

# 贡献代码
目前jfinalbbs就一个`master`分支,就朋也一个人在开发维护,如果想分享自己拓展的功能等,可以给朋也的`master`分支推送`pull request`

以上