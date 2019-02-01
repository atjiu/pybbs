## 收藏话题

- 地址 GET `/api/collect/get`
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

- 地址 GET `/api/collect/delete`
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