部署上线相关配置

### 步骤

1. 购买域名，域名提供商非常多，选一个自己喜欢的购买一个就可以了
2. 去服务器运营商购买服务器，建议阿里云，购买的时候看清区域，国内做论坛 **必须要备案的** ，不过阿里云也有国外的节点，购买的时候请注意
3. 安装java，mysql
4. 按照 快速开始 中的部署方法部署

### nginx配置

如果你服务器上就只一个论坛项目，那直接将程序里的端口改成80即可，如果你还想折腾点其它的东西，那就要用到nginx做代理转发请求了，具体配置如下

假如 example.com 是你的域名，程序启动端口是 8080 ，配置如下

```
server {
  server_name example.com;
  location / {
    proxy_pass http://127.0.0.1:8080/;
    proxy_redirect          off;
    proxy_set_header        Host            $host;
    proxy_set_header        X-Real-IP       $remote_addr;
    proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
    client_max_body_size    2m;
    client_body_buffer_size 128k;
    proxy_connect_timeout   300;
    proxy_send_timeout      300;
    proxy_read_timeout      300;
    proxy_buffers           32 32k;
    proxy_buffer_size       64k;
    proxy_busy_buffers_size 128k;
  }
}
```

### frp 映射配置

关于这个配置可以参见我的一篇博客 [利用frp内网穿透实现用自家电脑发布网站(不用买服务器了)](https://tomoya92.github.io/2018/10/18/frp-tutorial/)

### 配置https

https强烈推荐使用 letsencrypt 配置简单，主要是免费，唯一的缺点就是要3个月续一下时间，配置参见文档：[letsencrypt结合nginx配置https备忘](https://tomoya92.github.io/2016/08/28/letsencrypt-nginx-https/)

配置外网环境运气好，很快就可以搭建好，运气不好，折腾两天是常事，淡定慢慢配
