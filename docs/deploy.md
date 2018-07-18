## 购买服务器

有很多选择

| 国内 | 国外 |
| --- | --- |
| [阿里云][aliyun], [腾讯云][tencent]| [Linode][Linode], [Vultr][Vultr], [DigitalOcean][DigitalOcean] | 

[aliyun]: https://www.aliyun.com/
[tencent]: https://cloud.tencent.com/
[Linode]: https://www.linode.com/
[Vultr]: https://www.vultr.com/
[DigitalOcean]: https://www.digitalocean.com/
  
## 初始化服务器

这一步在云服务器控制台里就可以完成，建议安装ubuntu系统，我部署的服务器就是ubuntu，比较熟悉

## 安装软件

下面命令默认你用的是root登录的，如果用的是创建的用户登录的，在命令前加上sudo就可以了

```shell
apt update 
```

如果是国内的服务器，修改在国内的镜像源，速度会快很多，下面给一些选择

- [清华镜像源](https://mirror.tuna.tsinghua.edu.cn/help/ubuntu/)
- [阿里镜像源](https://opsx.alibaba.com/guide?lang=zh-cn&document=28)

配置好了，再运行一下 `apt update`

#### 安装 Nginx 

```shell
apt install nginx
```

#### 关闭apache服务（如果有的话）

```shell
/etc/init.d/apache2 stop

也可以试试 service apache2 stop
只要能关闭就好
```

#### 安装 Java 8

```shell
apt install python-software-properties
add-apt-repository ppa:webupd8team/java
apt update
apt install oracle-java8-installer
```

#### 安装Maven

```shell
apt install maven
```

#### 安装数据库MySQL

```shell
apt install mysql-server
# 安装过程中会让输入密码
```

#### 创建数据库

```shell
mysql -uroot -p
CREATE DATABASE `yiiu` CHARACTER SET utf8 COLLATE utf8_general_ci;
```

#### 安装Redis

安装好之后我记得就是启动状态，不用管它的

```shell
apt install redis-server
```

#### 安装git(如果系统已经自带了就不用装了)

```shell
apt install git
```

#### 下载项目

找一个你能记住的地方，把项目down下来，我习惯放在 `/home/` 下

```shell
mkdir -p /home/git/
cd /home/git/
git clone https://github.com/yiiu-co/yiiu
```

#### Maven构建项目

```shell
cd /home/git/yiiu
mvn clean compile package 
```

构建完成了，在 `yiiu/target/` 下会有一个 `yiiu.jar` 文件，将其拷贝到能记住的位置，我还是习惯放在 `/home/` 下

```shell
mkdir -p /home/java/java
cp /home/yiiu/target/yiiu.jar /home/java/yiiu/

# 拷贝配置文件
cp /home/yiiu/src/main/resources/application.yml /home/java/yiiu/
# 修改配置文件名
cd /home/java/yiiu/
mv application.yml application-prod.yml
# 具体配置见文档其它部分
```

#### 创建启动关闭脚本

```shell
# 启动脚本
echo 'java -jar yiiu.jar --spring.profiles.active=prod > log.file 2>&1 &' > start.sh
# 关闭脚本
echo 'ps -ef | grep yiiu.jar | grep -v grep | cut -c 9-15 | xargs kill -s 9' > shutdown.sh
# 给脚本执行的权限
chmod a+x start.sh shutdown.sh
```

#### 启动项目

```shell
cd /home/java/yiiu/
# 启动
./start.sh
# 关闭 
./shutdown.sh
```

## Nginx配置反向代理

```shell
cd /etc/nginx/conf.d
```

编辑 default.conf 如果不存在，创建一个

将下面这段配置想办法写到文件里就可以了

```shell
server {
  listen 80;
  server_name yiiu.co;
  location / {
    proxy_pass http://127.0.0.1:8080/;
    include conf.d/proxy.conf;
  }
}
```

其中 `proxy.conf` 文件内容如下，也是放在 `/etc/nginx/conf.d` 下的

```shell
proxy_redirect          off;
proxy_set_header        Host            $host;
proxy_set_header        X-Real-IP       $remote_addr;
proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
client_max_body_size    20m;
client_body_buffer_size 128k;
proxy_connect_timeout   90;
proxy_send_timeout      90;
proxy_read_timeout      90;
proxy_buffers           32 4k;
```

启动Nginx

```shell
service nginx restart
```

域名映射

到你域名控制台去配置一个解析，解析A链接到你服务器的ip

愉快的访问你的网站吧！

## PS: 其它

查看端口被占用命令：`lsof -i:8080`

杀掉进程命令：`kill -9 pid`