程序启动后，要配置上传文件保存路径，否则用户注册会看不到自己的头像

配置地址：启动程序 -> 登录后台 -> 系统设置

![](../assets/QQ20190103-155421.png)

说明：

**路径一定要是绝对路径**

### 非nginx静态映射配置

如果你没有使用nginx做静态文件映射，就请配置在程序启动目录下，举个例子：

你下载的jar包存放在 `/opt/pybbs/pybbs.jar` 那么这里的地址就应该是 `/opt/pybbs/static/upload/` 

如果你用的是docker部署的服务，那这个路径配置就是固定的 `/app/static` 了，上传的图片会自动同步到docker启动目录下的static文件夹里

### nginx静态文件映射配置方法

nginx静态文件映射配置

```
server {
  #...
  location /static/ {
    root /opt/cdn/;
    autoindex on;
  }
}
```

那么你这个地上的配置就应该是 `/opt/cdn/static/upload/`

### 访问地址

默认给的是 `http://localhost:8080/static/upload/` 如果你的访问域名是 `http://example.com` 那这里就要换成 `http://example.com/static/upload/`
