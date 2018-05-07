## 正式环境

1. 下载yiiu `wget -c https://github.com/yiiu-co/yiiu/archive/master.zip`
2. 解压
3. 创建数据库 yiiu
4. 修改项目配置文件 `src/main/resources/application.yml` 请参考配置说明
5. 打包 `mvn clean compile package` 要进入到项目文件夹里运行
6. 启动项目 `java -jar yiiu.jar > log.file 2>&1 &`
7. 项目启动后，表结构就已经自动创建好了，将项目里的 `init.sql` 里的数据导入到数据库里
8. 访问 `http://localhost:8080` 
9. 关闭服务 `ps -ef | grep yiiu.jar | grep -v grep | cut -c 9-15 | xargs kill -s 9`
10. 查看日志运行 `tail -200f yiiu.log`

## 开发环境

1. 下载项目 `git clone https://github.com/yiiu-co/yiiu`
2. 创建数据库 yiiu
3. 启动服务 `mvn spring-boot:run` 也可以在ide里直接运行 `src/main/java/co/yiiu/Application.java` 里的main方法
4. 将项目里的 `init.sql` 里的数据导入到数据库里
5. 访问 `http://localhost:8080` 后台 `http://localhost:8080/admin/login` 用户名 admin 密码 123123
6. 若要修改配置，可参数配置说明

**注意，项目里用到了lombok，如果是开发环境的话，请在ide里安装lombok插件，否则ide里会报错**