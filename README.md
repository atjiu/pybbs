> Instructions：Please keep the powered by Pybbs at the bottom of the page

**Notice: backup database before upgrading**

[English](https://github.com/tomoya92/pybbs) | [中文](https://github.com/tomoya92/pybbs/blob/master/README_zh_CN.md)

## Technology stack

- jdk8
- tomcat8
- springboot
- springsecurity
- springdata
- freemarker
- mysql
- bootstrap

**Well, that's the spring family bucket**

## Features

- I18N international support
- ehcache support
- Compatibility(IE9+)
- Responsive layout
- Local sign in/ sign up
- Use spring-boot development
- Permission to use spring-security, based on the permissions to do URL easy configuration management
- Spring-data operation data storage
- Use [editor](https://github.com/lepture/editor) as markdown editor, More convenient to write, but also supports screenshots paste upload

## Getting Started

- `git clone https://github.com/tomoya92/pybbs.git`
- Modify configuration item in configuration file `src/main/resources/application.yml`
- Create database `pybbs-springboot`
- Run main method of `PybbsApplication.java`
- Browser `http://localhost:8080/`
- Default account `tomoya` `123123`

## Jar package run

- `git clone https://github.com/tomoya92/pybbs.git`
- Modify configuration item in configuration file `src/main/resources/application.yml`
- Create database `pybbs-springboot`
- Run `mvn clean -Dmaven.test.skip=true` command to generate jar package, located in `target/pybbs.jar` package
- Run `java -jar pybbs.jar` to start the service
- Browser `http://localhost:8080/`
- Default account `tomoya` `123123`

## Pending completion

- [x] Add cache（ehcache）
- [x] I18N
- [ ] Indexed
- [ ] Integrated third party login（Github, QQ, Weibo）

## How to do a problem?

[Issues](https://github.com/tomoya92/pybbs/issues) on Github

## Other versions

- Golang: [https://github.com/tomoya92/pybbs-go](https://github.com/tomoya92/pybbs-go)
- spring-boot: [https://github.com/tomoya92/pybbs](https://github.com/tomoya92/pybbs)
- jfinal: [https://github.com/tomoya92/pybbs/tree/v2.3](https://github.com/tomoya92/pybbs/tree/v2.3)
- ssm: [https://github.com/ehuacui/ehuacui-bbs](https://github.com/ehuacui/ehuacui-bbs)

## Contribution

Welcome to send pull request

## Theme

[https://github.com/tomoya92/pybbs-theme](https://github.com/tomoya92/pybbs-theme)

## Donate

| ![image](https://cloud.githubusercontent.com/assets/6915570/18000010/9283d530-6bae-11e6-8c34-cd27060b9074.png) | ![image](https://cloud.githubusercontent.com/assets/6915570/17999995/7c2a4db4-6bae-11e6-891c-4b6bc4f00f4b.png) |
| :------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------: |
|                                                     Alipay                                                     |                                                     Wechat                                                     |

## Docker run
> create application.yml 

```
docker run --name pybbs -p 9080:8080 -v /app/pybbs/application.yml:/app/application.yml -d stiangao/pybbs:v2.4
```

## License

MIT
