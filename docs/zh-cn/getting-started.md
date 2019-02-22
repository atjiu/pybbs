快速开始

## 开发人员搭建

- git clone https://github.com/tomoya92/pybbs
- 使用idea打开，项目用的是idea开发的，如果你对eclipse熟悉，也是可以的
- idea打开它会自动构建项目，构建工具是maven
- 修改配置文件 `src/main/resources/application-dev.yml` 里的数据库相关配置
- 找到`co.yiiu.pybbs.PybbsApplication`类，直接运行main方法即可启动
- 浏览器运行 `http://localhost:8080` , 后台地址 `http://localhost:8080/adminlogin` 后台用户名 admin 密码 123123

## 非开发人员搭建

- 首先保证你服务器上配置好了 java 环境，版本 jdk1.8 和 MySQL服务器，版本 5.7.x 其它可选环境配置参见 [网站准备工作](zh-cn/ready)
- 然后下载最新的一键启动压缩包，下载地址：[https://github.com/tomoya92/pybbs/releases](https://github.com/tomoya92/pybbs/releases)
- 解压, 修改解压出来的文件夹里的 `application-prod.yml` 文件，只需要修改一个地方，就是数据库的连接信息，[配置方法](zh-cn/base)
- 运行压缩包里的脚本 `sh start.sh`
- 关闭服务运行 `sh shutdown.sh` 
- 查看启动日志 `tail -200f log.file`
- 查看服务是否启动 `ps -ef|grep pybbs` 如果有pybbs的进程，就说明服务启动了
- 网站的其它配置，参见文档

## docker运行

- 保证服务器有docker和docker-compose环境
- `git clone https://github.com/tomoya92/pybbs` 或 下载最新版
- cd pybbs进入项目
- 运行 `docker-compose up -d` 命令启动容器，-d是后台运行的意思
- 启动后，访问 `http://localhost:8080` 
- 关闭容器 `docker-compose down`
- 查看日志 `docker-compose logs -f server`

**第一次运行会比较慢,视服务器性能和网速决定**

**项目根目录下会生成 `mysql` 文件夹为数据库文件,注意谨慎操作，另外论坛启动后，用户上传的图片和系统生成的默认头像会自动同步到根目录下的 `static` 文件夹下**

**这个Dockerfile是 [@zzzzbw](https://github.com/zzzzbw) 大佬帮忙开发的 万分感谢！！**

## 打war包运行（不推荐）

这种方式要修改代码

- 首先打开 `pom.xml` 将 `<packaging>jar</packaging>` 改成 `<packaging>war</packaging>`
- 然后在 `dependencies` 里加入一个依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-tomcat</artifactId>
  <scope>provided</scope>
</dependency>
```

- 在 `src/main/java/co/yiiu/pybbs/` 下创建一个类，名字随便启，然后将下面内容拷贝进去

```java
package co.yiiu.pybbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

- 最后运行`mvn clean assembly:assembly` 进行打包
- 打包成功后，找到target里的`pybbs.war`，将其拷贝到tomcat下的webapps里，启动tomcat即可

这种方式我测试有个静态资源问题，有兴趣的可以试着找一下解决办法，这里就不折腾了，**真的不推荐这种方式启动**
