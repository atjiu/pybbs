## Number of unread notifications

- Address GET `/api/notification/notRead`
- Request Type application/json
- Params
  - token
- Return Result(int) // Returns the number of unread messages

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```

## Mark Notification Read

- Address GET `/api/notification/markRead`
- Request Type application/json
- Params
  - token
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Notification List

- Address GET `/api/notification/list`
- Request Type application/json
- Params
  - token
- Return Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "read": [
      {
        "inTime": 1548993303000,
        "topicId": 1,
        "read": true,
        "action": "COMMENT",
        "targetUserId": 1,
        "id": 1,
        "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
        "title": "我是一个话题标题",
        "userId": 2,
        "content": "我是第二条评论",
        "username": "test"
      }
    ],
    "notRead": [
      {
        "inTime": 1548993369000,
        "topicId": 1,
        "read": false,
        "action": "REPLY",
        "targetUserId": 1,
        "id": 2,
        "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
        "title": "我是一个话题标题",
        "userId": 2,
        "content": "@tomoya 我是第三条评论",
        "username": "test"
      },
      {
        "inTime": 1548993369000,
        "topicId": 1,
        "read": false,
        "action": "COMMENT",
        "targetUserId": 1,
        "id": 3,
        "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
        "title": "我是一个话题标题",
        "userId": 2,
        "content": "@tomoya 我是第三条评论",
        "username": "test"
      }
    ]
  }
}
```
