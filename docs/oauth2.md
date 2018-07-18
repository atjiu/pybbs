**目前只开发了Github的登录**

```yaml
site:
  oauth2:
    github:
      clientId: # github申请应用的ClientId
      clientSecret: # 对应的密钥
      callbackUrl: ${site.baseUrl}/oauth2/github/callback
```

|  | 值 | 解释 |
| --- | --- | --- | 
| clientId |  | 在Github -> Settings -> Developers 申请一个OAuth App 然后就可以拿到clientId | 
| clientSecret |  | 申请OAuth App后可拿到 | 
| callbackUrl | ${site.baseUrl}/oauth2/github/callback | Github的回调地址，固定写法，请不要修改，这个也要配置在Github的申请App里，注意${site.baseUrl}要改成你的站点域名 | 
