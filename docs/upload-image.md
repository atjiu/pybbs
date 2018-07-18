**目前只支持上传到本地和上传到七牛**

## 本地上传

```yaml
site:
  staticUrl: http://localhost:8080/static/images/upload/
  uploadPath: ./views/${site.theme}/static/images/upload/
  uploadType: local # local, qiniu
```

|  | 值 | 解释 |
| --- | --- | --- | 
| uploadType |  | 如果上传图片到本地，请务必将uploadType设置成local | 
| staticUrl |  | 静态文件访问路径，保持默认即可，如果非要改，比较麻烦，当然如果你是高手，也可以自行配置 | 
| uploadPath |  | 保持默认的路径即可 | 

## 上传七牛

```yaml
site:
  uploadType: qiniu # local, qiniu
  upload:
    qiniu:
      accessKey: # 七牛开发者上传的key
      secretKey: # 七牛开发者上传的密钥
      bucket: # 创建的存储空间名称
      domain: # 存储空间域名
```

|  | 值 | 解释 |
| --- | --- | --- | 
| uploadType |  | 如果上传图片到七牛，请务必将uploadType设置成qiniu | 
| accessKey |  | 七牛开发者上传的key，在 https://portal.qiniu.com/signup?ref=developer.qiniu.com 开通七牛开发者帐号，然后即可拿到相应的key | 
| secretKey |  | 七牛开发者上传的密钥 | 
| bucket |  | 创建的存储空间名称 | 
| domain |  | 存储空间域名 | 

**PS：除了上面提供的两种配置静态路径的方法，还可以使用nginx来配置，但要将路径配置好**