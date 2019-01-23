## 更新个人信息

- 地址 POST `/api/settings/update`
- 参数
  - token
  - telegramName: Telegram用户名
  - website: 个人网站
  - bio: 个人简介
  - emailNotification: 是否接收邮箱通知
- 返回 Result()

## 发送邮箱验证码

- 地址 GET `/api/settings/sendEmailCode`
- 参数
  - token
  - email: 邮箱地址
  - code: 邮箱接收到网站发送的验证码
- 返回 Result()

## 更新用户邮箱

- 地址 POST `/api/settings/updateEmail`
- 参数
  - token
  - email: 邮箱地址
- 返回 Result()

## 上传头像

- 地址 POST `/api/settings/uploadAvatar`
- 参数
  - token
  - file: 上传图片的文件对象
- 返回 Result(String) // 上传成功后，会返回图片的访问地址

## 修改密码

- 地址 POST `/api/settings/updatePassword`
- 参数
  - token
  - oldPassword: 旧密码
  - newPassword: 新密码
- 返回 Result()

## 刷新token

- 地址 GET `/api/settings/refreshToken`
- 参数
  - token
- 返回 Result(String) // 返回新token 
