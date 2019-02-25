## 发布评论

- 地址 POST `/api/comment`
- 请求类型 application/json
- 参数
  - token
  - content: 评论的内容
  - topicId: 评论的话题ID
  - commentId: 回复评论的对象（盖楼评论的上级评论id）
- 返回 Result(Comment)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "id": 1,
    "topicId": 1,
    "userId": 1,
    "content": "我是一个评论",
    "inTime": 1548992997521,
    "commentId": null,
    "upIds": null
  }
}
```

## 更新评论

- 地址 PUT `/api/comment/:id`
- 请求类型 application/json
- 参数
  - token
  - id: 评论的id
  - content: 评论的内容
- 返回 Result(Comment)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "id": 1,
    "topicId": 1,
    "userId": 1,
    "content": "我是更新后的评论",
    "inTime": 1548993059477,
    "commentId": null,
    "upIds": null
  }
}
```

## 删除评论

- 地址 DELETE `/api/comment/:id`
- 请求类型 application/json
- 参数
  - token
  - id: 评论的id
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 点赞评论

- 地址 GET `/api/comment/:id/vote`
- 请求类型 application/json
- 参数
  - token
  - id: 评论的id
- 返回 Result(int) 返回点赞后当前评论的总赞数

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```
