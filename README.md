> 使用说明：请保留页面底部的 powered by 朋也社区

## 技术栈

- jdk8
- tomcat8
- springboot
- springsecurity
- springdata
- freemarker
- mysql
- bootstrap

**呃，就是spring全家桶**

## 特性

- 社区兼容性（IE9+）
- 页面自适应布局
- 本地登录，注册
- 使用springboot开发
- 权限使用springsecurity，基于url做的权限方便配置管理
- 使用的springdata操作数据存储
- 使用 [editor](https://github.com/lepture/editor) 作为 Markdown编辑器, 书写更方便, 还支持截图粘贴上传
- 页面与 https://bbs.tomoya.cn 一样 

## 如何开始

- `git clone https://github.com/tomoya92/pybbs.git`
- `git clone https://github.com/tomoya92/java-utils.git` 并按照readme的说明安装到本地 
- 修改配置文件 `src/main/resources/application.yml` `src/main/resources/config.yml` 里的配置项
- 创建数据库 `pybbs-springboot`
- 运行`PybbsApplication.java`里的`main`方法
- 启动项目后表是自动创建的，然后将 `pybbs-springboot.sql` 导入数据库
- 浏览器 `http://localhost:8080/`
- 默认帐户 `tomoya` `123123`

## 打jar包运行

- `git clone https://github.com/tomoya92/pybbs.git`
- `git clone https://github.com/tomoya92/java-utils.git` 并按照readme的说明安装到本地 
- 修改配置文件 `src/main/resources/application.yml` `src/main/resources/config.yml` 里的配置项
- 创建数据库 `pybbs-springboot`
- 运行 `mvn package` 命令，生成jar包，位置在 `target/pybbs.jar`
- 运行 `java -jar pybbs.jar` 即可启动服务
- 启动项目后表是自动创建的，然后将 `pybbs-springboot.sql` 导入数据库
- 浏览器 `http://localhost:8080/`
- 默认帐户 `tomoya` `123123`

## 待完成

- [ ] 加入缓存（redis, ehcache）
- [ ] 集成第三方登录（QQ，微博）
- [ ] 检索

## 碰到问题怎么办?

- 到 [https://bbs.tomoya.cn](https://bbs.tomoya.cn) 上提问答
- 在Github上提 [Issues](https://github.com/tomoya92/pybbs/issues)
- 提问题的时候请将问题重现步骤描述清楚
- 加QQ群：`419343003`

## 其它版本

- golang版：[https://github.com/tomoya92/pybbs-go](https://github.com/tomoya92/pybbs-go)
- springboot版：[https://github.com/tomoya92/pybbs](https://github.com/tomoya92/pybbs)
- jfinal版：[https://github.com/tomoya92/pybbs/tree/v2.3](https://github.com/tomoya92/pybbs/tree/v2.3)
- ssm版：[https://github.com/ehuacui/ehuacui-bbs](https://github.com/ehuacui/ehuacui-bbs)

## 贡献

欢迎大家提pr

## 捐赠

![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png)
![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png)

## License

MIT
