通知配置，支持两种消息提醒，**轮询** + **socket推送**

## 配置

```yaml
site:
  socketNotification: false
  socket:
    hostname: localhost
    port: 9092
    url: http://localhost:9092
```

|  | 值 | 解释 |
| --- | --- | --- | 
| socketNotification | false | 默认关闭，如果开启，系统通知会采用socket的方式从服务端推给网页，关闭系统会采用轮询的方式通过ajax请求服务器拿数据展示在页面上 | 
| hostname | localhost | socket服务绑定的ip地址 | 
| port |  | socket服务的端口 | 
| url |  | 页面连接socket服务的地址，单独拿出来配置一下是为了方便线上部署 | 

## 部署

部署项目会有些坑，因为它是在一个项目里启动了两个端口，下面提供一种部署方式，仅供参考，如果你有更好的部署方法，请务必分享给我

通过nginx，拦截 `/socket.io/` 路径来转发

```
server {
  listen xxx;
  server_name xxx;
  location /socket.io/ {
    proxy_pass http://localhost:9092;
  }
}
```