## 简介

> YIIU是使用 Spring-Boot 开发的一个论坛（社区）系统，具有简洁的界面，实用的功能，您值得拥有

在线地址：[yiiu.co](https://yiiu.co/)

## 技术栈

- Java8
- MySQL
- Bootstrap3.x
- SpringBoot2.0
- Redis
- Elasticsearch5.x

## 使用前准备

1. 有Java环境

  通过 `java -version` 查看一下自己服务器上配置的java版本是不是8

2. 有Redis环境

  YIIU没有使用session，而是把用户的登录状态存在Redis里，所以需要一个Redis服务
    
3. 有Elasticsearch环境**(非必要)**
    
  es版本是个问题，必要要下载5.x版本的es，否则可能会集成不成功，这个主要是做检索用的，如果你不需要开启检索功能，那么这个可以不用准备，启动也不会报错
    
4. 有MySQL环境
    
  理论上支持所有Hibernate支持的数据库，因为项目里的SQL都是用HQL写的，没有原生SQL，所以不会出现SQL语言不兼容报错问题，开发使用的是MySQL，这里只做MySQL介绍
    
5. 安装maven（非必要）
  
  用于对源码构建成jar包，如果是开发环境，ide里自带的也有maven工具