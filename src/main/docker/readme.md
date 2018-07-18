## docker 

#### build
```
mvn package * docker build
```
说明: 会生成 yiiu/yiiu:${project.version} 的镜像

#### push
```
docker push
```
说明：若要push到docker hub，则要docker login登录

#### run

```
docker run -d --name yiiu -v /var/yiiu:/app/ -p 8080:8080 tomoya92/yiiu
```
说明： --name 是容器的名称， -v /var/yiiu:/app/ 把容器的/app目录映射到主机的 /var/yiiu 下面。 容器的 /app 目录是工作目录，相当于 `java -jar yiiu.jar` 命令所在的目录，
也就是说容器启动的时候会读取该目录的文件，该目录下的 views、application.yml、yiiu.sqlite 会被读取到.

前提：镜像要push到docker hub