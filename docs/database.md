```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost/yiiu?useSSL=false&characterEncoding=utf8
    username: root
    password:
```

|  | 值 | 解释 |
| --- | --- | --- | 
| url |  | 连接数据库URL | 
| username |  | 用户名 | 
| password |  | 密码 | 

**MySQL有的版本会出现保存emoji出错，这个请参照下面配置对数据库进行修改**

**添加emoji支持（仅MySQL数据库）**

- 创建数据库时选择 utf8mb4 字符集
- 添加下面这段配置到 /etc/mysql/mysql.conf.d/mysqld.conf 里的 [mysqld] 下，保存重启Mysql服务

```
[mysqld]
character-set-client-handshake = FALSE
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
init_connect='SET NAMES utf8mb4'
```
  
如果不行，试着把yiiu也重启一下