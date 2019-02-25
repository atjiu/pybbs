## 收藏话题

- 地址 POST `/api/collect/:topicId`
- 请求类型 application/json
- 参数
  - token
  - topicId: 话题ID
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 取消收藏

- 地址 DELETE `/api/collect/:topicId`
- 请求类型 application/json
- 参数
  - token
  - topicId: 话题ID
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```
