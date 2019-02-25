## Create Comment

- Address POST `/api/comment`
- Request Type application/json
- Params
  - token
  - content
  - topicId
  - commentId
- Return Result(Comment)

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

## Update Comment

- Address PUT `/api/comment/:id`
- Request Type application/json
- Params
  - token
  - id
  - content
- Return Result(Comment)

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

## Delete Comment

- Address DELETE `/api/comment/:id`
- Request Type application/json
- Params
  - token
  - id
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```

## Like Comment

- Address GET `/api/comment/:id/vote`
- Request Type application/json
- Params
  - token
  - id
- Return Result(int) Return the total number of comments for the current comment after the like

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```
