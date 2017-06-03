---
layout: post
title: 快速开始
---

## 开发环境部署

1. `git clone https://github.com/tomoya92/pybbs`
2. 使用`idea` or `eclipse`打开项目
3. 等待maven构建完成
4. 运行 `PybbsApplication.java` 类
5. 访问 `http://localhost:8080`

## 生产环境部署

1. 新建文件夹 `pybbs`
2. [下载最新jar包](https://github.com/tomoya92/pybbs/releases) 并放到 `pybbs` 文件夹下
3. [下载最新源码包](https://github.com/tomoya92/pybbs/releases) 放到除 `pybbs` 以外的任意目录下并解压
4. 拷贝源码包下的 `src/main/resources/application.yml` 文件到 `pybbs` 目录下
5. 修改 `application.yml` 文件里的配置项，可参考下面**配置文件说明修改**
6. 运行 `java -jar pybbs.jar` 
7. 访问 `http://localhost:8080`
