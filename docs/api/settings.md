## Update Settings

- Address PUT `/api/settings`
- Request Type application/json
- Params
  - token
  - telegramName
  - website
  - bio
  - emailNotification: Whether to receive email notifications
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Send Email Code

- Address GET `/api/settings/sendEmailCode`
- Request Type application/x-www-form-urlencoded
- Params
  - token
  - email
  - code: Picture verification code
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Update Email

- Address PUT `/api/settings/updateEmail`
- Request Type application/json
- Params
  - token
  - email
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Update Password

- Address PUT `/api/settings/updatePassword`
- Request Type application/json
- Params
  - token
  - oldPassword
  - newPassword
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Refresh Token

- Address GET `/api/settings/refreshToken`
- Request Type application/x-www-form-urlencoded
- Params
  - token
- Return Result(String) // Return new token

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": "208bb4c1-daf1-4a32-b198-7f4db8f6d565"
}
```
