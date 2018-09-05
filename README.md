> 使用说明：请在醒目的地方标明 powered by pybbs

[接口文档](https://github.com/tomoya92/pybbs/blob/master/api.md)

pybbs4.0 重新回归精简，请求全部以 restful 风格的接口提供，数据库使用内嵌的mongodb(可以通过简单的配置更改为外置数据库)，并且提供release安装包，方便部署，相关配置见下文

## 部署

[下载安装包](https://github.com/tomoya92/pybbs/releases)，解压，运行脚本(支持linux, mac)

- `sh start.sh`      # 启动服务
- `sh shutdown.sh`   # 停止服务

### 数据库文件在哪？

数据库使用的是内嵌的mongodb，数据库文件在安装目录根目录下的 `data` 文件夹里，备份直接拷贝即可

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

[issues](https://github.com/tomoya92/pybbs/issues)

QQ群：`419343003`

*提问题的时候请将问题重现步骤描述清楚*

## 贡献

欢迎大家提 issues 及 pr 

## 捐赠

![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png)
![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png)

**如果觉得这个项目对你有帮助，欢迎捐赠！**

## License

GNU AGPLv3 (pybbs4.0开始，开源协议从MIT更改为GNU AGPLv3)
