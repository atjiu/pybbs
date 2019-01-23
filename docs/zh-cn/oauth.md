项目里实现了一个github登录功能，下面说一下配置方法

申请clientId, clientSecret地址：https://github.com/settings/developers 前提要先登录github

打开页面后，点击 `New OAuth APP` 按钮

![](../assets/QQ20190107-135811.png)

填上必要的信息

![](../assets/QQ20190107-140155.png)

填写好之后，保存，跳转的页面上就有 clientId, clientSecret信息了，如下图

![](../assets/QQ20190107-135903.png)

拷贝上图中红框内容，粘贴到网站后台系统设置页面里的 Github 配置信息里

**注意**

- 网站域名必须外网能访问，如是你要在内网测试，可以使用ngrok，frp等工具来做内网穿透，具体使用方法百度吧，网上很多
- 回调地址格式是 网站域名+/oauth/github/callback 假如你的域名是 `http://example.com` 那么这里的回调地址就是 `http://example.com/oauth/github/callback` 不要填错了

配置好之后，保存，再次回到首页，就可以看到页面 header 上就有了`Github登录`的入口了

