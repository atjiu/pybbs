## 更新个人信息

- 地址 PUT `/api/settings`
- 请求类型 application/json
- 参数
  - token
  - telegramName: Telegram用户名
  - website: 个人网站
  - bio: 个人简介
  - emailNotification: 是否接收邮箱通知
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 发送邮箱验证码

- 地址 GET `/api/settings/sendEmailCode`
- 请求类型 application/x-www-form-urlencoded
- 参数
  - token
  - email: 邮箱地址
  - code: 邮箱接收到网站发送的验证码
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 更新用户邮箱

- 地址 PUT `/api/settings/updateEmail`
- 请求类型 application/json
- 参数
  - token
  - email: 邮箱地址
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 修改密码

- 地址 PUT `/api/settings/updatePassword`
- 请求类型 application/json
- 参数
  - token
  - oldPassword: 旧密码
  - newPassword: 新密码
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 刷新token

- 地址 GET `/api/settings/refreshToken`
- 请求类型 application/x-www-form-urlencoded
- 参数
  - token
- 返回 Result(String) // 返回新token

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": "208bb4c1-daf1-4a32-b198-7f4db8f6d565"
}
```
