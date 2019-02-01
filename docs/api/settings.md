## Update Settings

- Address POST `/api/settings/update`
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

- Address POST `/api/settings/updateEmail`
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

## Upload Avatar

- Address POST `/api/settings/uploadAvatar`
- Params
  - token
  - file: Avatar
- Return Result(String) // After the upload is successful, the address of the avatar will be returned.

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png"
}
```

## Update Password

- Address POST `/api/settings/updatePassword`
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