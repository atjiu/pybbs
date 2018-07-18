**目前只做过QQ邮箱的测试，没有问题**

```yaml
spring:
  mail:
    host: smtp.qq.com
    username: xxoo@qq.com
    password: # 这里的密码是QQ邮箱的授权码
    port: 465
    properties:
      mail.smtp.auth: true
      mail.smtp.ssl.enable: true
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
```

|  | 值 | 解释 |
| --- | --- | --- | 
| host | smtp.qq.com | 固定的，不要修改，如果是企业邮箱，请修改成 smtp.exmail.qq.com | 
| username |  | 用户名 | 
| password |  | 不是QQ密码，要进入邮箱设置里通过扫码拿到授权码 | 
| port | 465 | 端口，不要改 | 
| properties.[*] |  | 默认即可，不要修改 | 

**如果你对其它邮箱也进行了配置且可行的话，请告知我，谢谢**