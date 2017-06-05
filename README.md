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

## Docker run

```
docker run --name pybbs -p 10090:8080 -v /var/pybbs/application.yml:/app/application.yml -d stiangao/pybbs:v2.4
```

## License

MIT
