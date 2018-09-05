# YIIU 接口文档

下面接口要认证信息的，要带上认证信息，格式如下

请求头里加上 `Authorization` ，值为："Bearer " + token token为登录成功后返回的，如：

```json
{
  "header": {
    "Authorization": "Bearer 123123",  // 123123 为登录成功返回的token
  }
}
```

# 分类

### 分类列表

地址 GET /section/

参数

无

返回

Result.success(List)

# 首页

### 首页

地址 GET /

参数

- pageNo 页数
- tab 分类，具体参数在 `conf.yml` 里, 默认的是 share, ask, job, blog, good

返回

Result.success(Page<Topic>)

### Top100

地址 GET /top100

参数 

- size 条数，默认7条，最多100条

返回

Result.success(List<User>)

### 登录

地址 POST /login

参数

- username 用户名
- password 密码

返回

Result.success(token)

### 注册

地址 POST /register

参数

- username 用户名
- password 密码

返回

Result.success(token)

### 登出

地址 GET /logout

参数

无

返回

Result.success(String)

### 上传图片

地址 POST /upload

参数

要认证信息

- file: MultipartFile 文件对象

返回

Result.success(url)

# 话题

### 话题详情

地址 GET /topic/detail

参数

如果传认证信息，会返回当前查看的话题是否被收藏过，不传刚不会返回

- id 话题ID

返回

Result.success(Map<String, Object>)

### 发布话题

地址 POST /topic/create

参数

要认证信息

- title 标题（不能为空）
- content 内容 (支持markdown语法)
- tab 分类

返回

Result.success()

### 编辑话题

地址 POST /topic/update

参数

要认证信息

- id 话题ID
- title 标题（不能为空）
- content 内容 
- tab 分类

返回

Result.success()

### 删除话题

地址 POST /topic/delete

参数

要认证信息

- id 话题ID

返回

Result.success()

### 话题(取消)加精

地址 GET /topic/good

参数

要认证信息

- id 话题ID

返回

Result.success()

### 话题(取消)置顶

地址 GET /topic/top

参数

要认证信息

- id 话题ID

返回

Result.success()

# 评论

### 创建评论

地址 POST /comment/create

参数

要认证信息

- topicId 话题ID
- commentId 回复的评论ID，可以为null
- content 评论内容（不能为空, 不支持markdown语法）

返回

Result.success()

### 编辑评论

地址 POST /comment/update

参数

要认证信息

- id 评论ID
- content 评论内容（不能为空）

返回

Result.success()

### 删除评论

地址 POST /comment/delete

参数

要认证信息

- id 评论ID

返回

Result.success()

# 用户

### 个人信息

地址 GET /user/:username

参数

- username 用户名

返回

Result.success(User)

### 用户话题

地址 GET /user/:username/topics

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Topic>)

### 用户评论

地址 GET /user/{username}/comments

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Comment>)

### 用户收藏

地址 GET /user/{username}/collects

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Collect>)

### 个人设置

地址 POST /user/settings/profile

参数

要认证信息

- website 个人网站
- bio 个人简介
- email 个人邮件
- avatar 头像地址

返回

Result.success()

### 修改密码

地址 POST /user/settings/updatePassword

参数

要认证信息

- rawPassword 原密码
- newPassword 新密码

返回

Result.success()

# 收藏

### 收藏话题

地址 POST /collect/save

参数

要认证信息

- topicId 话题ID

返回

Result.success()

地址 POST /collect/delete

参数

要认证信息

- topicId 话题ID

返回

Result.success()