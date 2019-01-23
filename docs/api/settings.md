## Update Settings

- Address POST `/api/settings/update`
- Params
  - token
  - telegramName
  - website
  - bio
  - emailNotification: Whether to receive email notifications
- Return Result()

## Send Email Code

- Address GET `/api/settings/sendEmailCode`
- Params
  - token
  - email
  - code: Picture verification code
- Return Result()

## Update Email

- Address POST `/api/settings/updateEmail`
- Params
  - token
  - email
- Return Result()

## Upload Avatar

- Address POST `/api/settings/uploadAvatar`
- Params
  - token
  - file: Avatar
- Return Result(String) // After the upload is successful, the address of the avatar will be returned.

## Update Password

- Address POST `/api/settings/updatePassword`
- Params
  - token
  - oldPassword
  - newPassword
- Return Result()

## Refresh Token

- Address GET `/api/settings/refreshToken`
- Params
  - token
- Return Result(String) // Return new token
