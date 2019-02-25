## 未读消息个数

- 地址 GET `/api/notification/notRead`
- 请求类型 application/json
- 参数
  - token
- 返回 Result(int) // 返回未读消息条数

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```

## 标记消息已读

- 地址 GET `/api/notification/markRead`
- 请求类型 application/json
- 参数
  - token
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 消息列表

- 地址 GET `/api/notification/list`
- 请求类型 application/json
- 参数
  - token
- 返回 Result(Map)

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
