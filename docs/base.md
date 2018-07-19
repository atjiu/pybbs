# 网站配置

**整个网站的配置都在一个配置文件里，路径是 src/main/java/resources/application.yml **

```yaml
site:
  name: YIIU
  intro: <h5>属于Java语言的bbs</h5><p>在这里，您可以提问，回答，分享，诉说，这是个属于Java程序员的社区，欢迎您的加入！</p>
  baseUrl: http://localhost:8080/ # 不要忘了后面的 '/'
  staticUrl: http://localhost:8080/static/images/upload/
  pageSize: 20
  uploadPath: ./views/${site.theme}/static/images/upload/
  theme: default
  search: false
  GA: #Google Analytics ID
  googleZZ: # google站长meta标签content值
  baiduTJ: # 百度统计ID
  baiduZZ: # 百度站长meta标签content值
  uploadType: local # local, qiniu
  loginPoints: 2 # 登录点，最多2个位置登录，第三个登录点会覆盖第一个
  attempts: 5 # 登录后台尝试次数
  attemptsWaitTime: 15 # 尝试次数达到${attempts}次后，等待时间，单位分钟
  cookie:
    domain: localhost
    userName: remember-me
    userMaxAge: 30 # 天
    adminUserName: admin-remember-me
    adminUserMaxAge: 30 # 天
```

|  | 值 | 解释 |
| --- | --- | --- | 
| name | YIIU | 网站名称 | 
| intro |  | 用户没有登录的时候显示在右边栏里对站点的描述信息，可自行修改，支持html语法 | 
| baseUrl |  | 网站部署好以后的访问地址 | 
| staticUrl |  | 网站上传图片的访问地址，如果uploadType设置为qiniu，那么这个将失效 | 
| pageSize | 20 | 每页显示的条数 | 
| uploadPath |  | 本地上传图片路径，如果uploadType设置为qiniu，那么这个将失效 | 
| theme | default | 主题名，目前只能填default, 因为只有这一套主题 | 
| search | false | 是否开启检索，设置为true后，要接入elasticsearch，如果没有安装es，请将这里设置成false | 
| GA |  | 谷歌统计ID，请自行去申请然后填上即可 |  
| googleZZ |  | google站长meta标签content值 |  
| baiduTJ |  | 百度统计ID |  
| baiduZZ |  | 百度站长meta标签content值 |  
| uploadType | local/qiniu | local表示将图片上传到本地，qiniu表示将图片上传到七牛上，但相应的要配置下面的七牛配置项 |
| loginPoints | 2 | 可在2个设备上登录，可以修改多个设备，默认2个 |  
| attempts | 5 | 登录后台最多可以尝试次数 |  
| loginPoints | 15 | 达到最大尝试次数后，帐号被锁多少分钟，单位：分钟 |  
| cookie.domain |  | 站点访问域名是什么就填什么，不要带http(s):// 和 端口号 |  
| cookie.userName |  | 前端登录后，写入cookie里的用户信息对象名 |  
| cookie.userMaxAge |  | 前端用户登录成功写入的cookie有效期，默认30天 |  
| cookie.adminUserName |  | 后端登录后，写入cookie里的用户信息对象名 |  
| cookie.adminUserMaxAge |  | 后端用户登录成功写入的cookie有效期，默认30天 |  

**上传路径比较容易出问题，如果不知道怎么配置，就用默认的即可**

