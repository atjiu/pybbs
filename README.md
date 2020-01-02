> 使用说明：请在醒目的地方标明 powered by pybbs

[接口文档](https://github.com/tomoya92/pybbs/blob/master/api.md) | [在线地址](https://dev.yiiu.co/)

前端项目开源地址: https://github.com/tomoya92/pybbs-front-react

pybbs4.0 重新回归精简，请求全部以接口提供，数据库使用内嵌的mongodb(可以通过简单的配置更改为外置数据库)，并且提供release安装包，方便部署，相关配置见下文

## 部署

[下载安装包](https://github.com/tomoya92/pybbs/releases)，解压，运行脚本(支持linux, mac)

**启动之前，要装好mongodb数据库，如果数据库有用户名密码，请在解压出来的文件夹内的`application-prod.yml`文件内修改数据库连接信息**

- `sh start.sh`         # 启动服务
- `sh shutdown.sh`      # 停止服务
- `tail -200f log.file` # 查看日志

## 配置文件

项目配置文件是根目录下的 conf.yml 相应的都有注释

### 跨域配置

配置对象：site.corsDomain

例：一个前端项目域名是：http://example.com 要调用pybbs的接口服务，这时会出现跨域问题，配置方法如下

```yml
site:
  corsDomain: ["http://example.com"]
```

### 管理员配置

配置对象：site.admin

例：要将 tomoya 这个用户设置成管理员，配置方法如下

```yml
site:
  admin: ["tomoya"]
```

### 禁掉用户配置

配置对象：site.ban

例：要将 test 这个用户禁掉，配置方法如下

```yml
site:
  ban: ["test"]
```

## 反馈

- [开发俱乐部](https://17dev.club/)
- [issues](https://github.com/tomoya92/pybbs/issues)

*提问题的时候请将问题重现步骤描述清楚*

## 贡献

欢迎大家提 issues 及 pr

## 其它版本

|              | 2.2                                                                 | 2.3                                                     | 2.4                                                                                                                              | 2.6                                                   | 2.6.1 | 3.0                                                                 | master(这个版本是纯接口的)                                                   |
|:-------------|:--------------------------------------------------------------------|:--------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------|:-----------------------------------------|:--------------------------------------------------------------------|:-----------------------------------------------------------------------------|
| 开源地址     | [传送门](https://github.com/tomoya92/pybbs/tree/v2.2)               | [传送门](https://github.com/tomoya92/pybbs/tree/v2.3)   | [传送门](https://github.com/tomoya92/pybbs/tree/v2.4)                                                                            | [传送门](https://github.com/tomoya92/pybbs/tree/v2.6) |                                          | [传送门](https://github.com/tomoya92/pybbs/tree/3.0)                | | [传送门](https://github.com/tomoya92/pybbs)                                |
| 开发框架     | JFinal                                                              | JFinal                                                  | Spring-Boot                                                                                                                      | Spring-Boot                                           |                                          | Spring-Boot，MyBatis                                                | Spring-Boot                                                                  |
| 数据库       | MySQL                                                               | MySQL                                                   | MySQL                                                                                                                            | MySQL                                                 |                                          | MySQL                                                               | MongoDB                                                                      |
| 前台         | &radic;                                                             |                                                         |                                                                                                                                  |                                                       |                                          | &radic;                                                             |                                                                              |
| 后台         | &radic;                                                             |                                                         |                                                                                                                                  |                                                       |                                          | &radic;                                                             |                                                                              |
| 前后台合一   |                                                                     | &radic;                                                 | &radic;                                                                                                                          | &radic;                                               |                                          |                                                                     |                                                                              |
| 编辑器       | [WangEditor(富文本)](https://github.com/wangfupeng1988/wangEditor/) | [editor(Markdown)](https://github.com/lepture/editor)   | 可切换 [editor(Markdown)](https://github.com/lepture/editor) [WangEditor(富文本)](https://github.com/wangfupeng1988/wangEditor/) | [pyeditor](https://github.com/tomoya92/pyeditor)      |                                          | [WangEditor(富文本)](https://github.com/wangfupeng1988/wangEditor/) |                                                                              |
| 积分机制     | &radic;                                                             | &radic;                                                 |                                                                                                                                  | &radic;                                               |                                          | &radic;(这个版本叫声望)                                             | &radic;                                                                      |
| 私信         | &radic;                                                             |                                                         |                                                                                                                                  |                                                       |                                          |                                                                     |                                                                              |
| 本地登录注册 | &radic;                                                             |                                                         | &radic;                                                                                                                          | &radic;                                               |                                          | &radic;                                                             | &radic;                                                                      |
| Github登录   |                                                                     | &radic;                                                 |                                                                                                                                  |                                                       |                                          | &radic;                                                             |                                                                              |
| QQ登录       | &radic;                                                             |                                                         |                                                                                                                                  |                                                       |                                          |                                                                     |                                                                              |
| 微博登录     | &radic;                                                             |                                                         |                                                                                                                                  |                                                       |                                          |                                                                     |                                                                              |
| 网站接口     | &radic;                                                             | &radic;                                                 | &radic;                                                                                                                          | &radic;                                               |                                          | &radic;                                                             | &radic;                                                                      |
| 权限         | RBAC                                                                | RBAC                                                    | RBAC                                                                                                                             | RBAC                                                  |                                          | RBAC                                                                | 通过配置用户名增加一些额外功能                                               |
| 搜索         | 模糊搜索                                                            | Solr                                                    | 模糊搜索                                                                                                                         | Hibernate-Search                                      |                                          | Elasticsearch                                                       |                                                                              |
| 标签         | &radic;                                                             | &radic;                                                 |                                                                                                                                  | &radic;                                               |                                          | &radic;                                                             |                                                                              |
| 国际化       |                                                                     |                                                         | &radic;                                                                                                                          |                                                       |                                          |                                                                     |                                                                              |
| APP          |                                                                     | [pybbsMD(Android)](https://github.com/tomoya92/pybbsMD) |                                                                                                                                  |                                                       |                                          |                                                                     |                                                                              |
| 前端         |                                                                     |                                                         |                                                                                                                                  |                                                       |                                          |                                                                     | [pybbs-front-react(React.js)](https://github.com/tomoya92/pybbs-front-react) |

动图演示移步：https://17dev.club/article/5bd2bb10c8b14050bdc03eff

## 捐赠

![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png)
![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png)

**如果觉得这个项目对你有帮助，欢迎捐赠！**

## License

GNU AGPLv3 (pybbs4.0开始，开源协议从MIT更改为GNU AGPLv3)
