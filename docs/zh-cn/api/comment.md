## 发布评论

- 地址 POST `/api/comment/create`
- 参数
  - token
  - content: 评论的内容
  - topicId: 评论的话题ID
  - commentId: 回复评论的对象（盖楼评论的上级评论id）
- 返回 Result(Comment)

## 更新评论

- 地址 POST `/api/comment/update`
- 参数
  - token
  - id: 评论的id
  - content: 评论的内容
- 返回 Result(Comment)

## 删除评论

- 地址 GET `/api/comment/delete`
- 参数
  - token
  - id: 评论的id
- 返回 Result()

## 点赞评论

- 地址 GET `/api/comment/vote`
- 参数
  - token
  - id: 评论的id
- 返回 Result(int) 返回点赞后当前评论的总赞数
