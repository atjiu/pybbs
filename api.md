# YIIU 接口文档

# 首页

### 首页

地址 GET /api/

参数

- pageNo 页数
- tab 板块，只能为空或者填: NEWEST, NOANSWER, GOOD

返回

Result.success(Page<Map>)

### 搜索

地址 GET /api/search

参数

- pageNo 页数
- keyword 关键字

返回

Result.success(Page<TopicIndex>)

### 标签

地址 GET /api/tags/

参数

- pageNo 页数

返回

Result.success(Page<Tag>)

### Top100

地址 GET /api/top100

参数

无

返回

Result.success(Page<User>)

### 登录

地址 POST /api/login

参数

- username 用户名
- password 密码

返回

Result.success()

### 注册

地址 POST /api/register

参数

- username 用户名
- password 密码
- email 邮箱
- emailCode 邮箱验证码
- code 图片验证码

返回

Result.success()

# 公共

### 发送邮件

地址 GET /api/common/sendEmailCode

参数

- email 邮箱

返回

Result.success()

### 上传图片

地址 POST /api/common/wangEditorUpload

参数

- file: MultipartFile 文件对象

返回

Map<String, Object>

# 话题

### 话题详情

地址 GET /api/topic/{id}

参数

- id 话题ID

返回

Result.success(Map<String, Object>)

### 发布话题

地址 POST /api/topic/save

参数

- title 标题（不能为空）
- content 内容 
- tag 标签（不能为空，英文逗号隔开）

返回

Result.success(Topic)

### 编辑话题

地址 POST /api/topic/edit

参数

- id 话题ID
- title 标题（不能为空）
- content 内容 
- tag 标签（不能为空，英文逗号隔开）

返回

Result.success(Topic)

### 话题点赞/踩

地址 GET /api/topic/{id}/vote

参数

- id 话题ID
- action 赞成或者反对，只能填：UP, DOWN

返回

Result.success(Map<String, Object>)

# 评论

### 评论列表

地址 GET /api/comment/list

参数

- topicId 话题ID

返回

Result.success(List<Map<String, Object>>)

### 创建评论

地址 POST /api/comment/save

参数

- topicId 话题ID
- commentId 回复的评论ID，可以为null
- content 评论内容（不能为空）

返回

Result.success(Comment)

### 编辑评论

地址 POST /api/comment/edit

参数

- id 评论ID
- content 评论内容（不能为空）

返回

Result.success(Comment)

### 对评论点赞/踩

地址 GET /api/comment/{id}/vote

参数

- id 评论ID
- action 评论动作，只能填 UP, DOWN

返回

Result.success(Map<String, Object>)

# 用户

### 个人信息

地址 GET /api/user/{username}

参数

- username 用户名

返回

Result.success(User)

### 用户话题

地址 GET /api/user/{username}/topics

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Map>)

### 用户评论

地址 GET /api/user/{username}/comments

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Map>)

### 用户收藏

地址 GET /api/user/{username}/collects

- username 用户名
- pageNo 分页
- pageSize 每页显示条数（最大不能超过系统设置的pageSize）

返回

Result.success(Page<Map>)

### 个人设置

地址 POST /api/user/setting/profile

参数

- url 个人网站
- bio 个人简介
- commentEmail: Boolean 是否开启评论邮件
- replyEmail: Boolean 是否开启回复邮件

返回

Result.success(User)

### 修改密码

地址 POST /api/user/setting/changePassword

参数

- oldPassword 旧密码
- newPassword 新密码

返回

Result.success()

### 修改头像

地址 POST /api/user/setting/changeAvatar

参数

- avatar 头像的Base64转码字符串

返回

Result.success()

### 刷新Token

地址 GET /api/user/setting/refreshToken

参数

无

返回

Result.success()

### 用户日志列表

参数

- pageNo 页数

返回

Result.success(Page<Log>)

# 标签

### 标签输入自动完成

地址 GET /api/tag/autocomplete

参数

- keyword 输入的内容

返回

Result.success(List<Tag>)

# 通知

### 未读消息

地址 GET /api/notification/notRead

参数

无

返回

Result.success(long)

# 收藏

### 添加收藏

地址 GET /api/collect/add

参数

- topicId 话题ID

返回

Result.success(long)

### 添加收藏

地址 GET /api/collect/delete

参数

- topicId 话题ID

返回

Result.success(long)

# 非接口（输出非json）

### 图片验证码

地址 GET /common/code

参数

无

返回

jpeg图片