### Step

1. Buy a domain name, there are a lot of domain name providers, choose one you like to buy one.
2. Install java and mysql
3. Deploy according to the deployment method in [Getting Started](getting-started)

### Nginx configuration

If you only have one forum item on the server, you can directly change the port in the program to 80.
If you want to toss something else, you should use nginx as the proxy forwarding request. 
The specific configuration is as follows:

If example.com is your domain name, the program startup port is 8080, the configuration is as follows

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

### Frp configuration

See my blog about this configuration. [利用frp内网穿透实现用自家电脑发布网站(不用买服务器了)](https://tomoya92.github.io/2018/10/18/frp-tutorial/)

### Https configuration

Https strongly recommends using letsencrypt configuration is simple, mainly free, the only drawback is to continue the time for 3 months, 
configuration see the document: [letsencrypt结合nginx配置https备忘](https://tomoya92.github.io/2016/08/28/letsencrypt-nginx-https/)

Configure the deployment environment, good luck, you can build quickly, bad luck, toss two days is a common thing, calmly slowly match
