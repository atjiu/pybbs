## 话题详情

- 地址 GET `/api/topic/detail`
- 参数
  - token: **可不传**，传token返回值里会多一个这个话题是否被自己收藏，如果不填就没有这个对象
  - id: 话题id
- 返回 Result(Map)

## 发布话题

- 地址 POST `/api/topic/create`
- 参数
  - token
  - title: 话题标题
  - content: 话题内容（可为空）
  - tags: 标签，多个标签之间英文逗号隔开，最多5个
- 返回 Result(Topic)

## 编辑话题

- 地址 POST `/api/topic/edit`
- 参数
  - token
  - id: 话题ID
  - title: 话题标题
  - content: 话题内容（可为空）
  - tags: 标签，多个标签之间英文逗号隔开，最多5个
- 返回 Result(Topic)

## 删除话题

- 地址 GET `/api/topic/delete`
- 参数
  - token
  - id: 话题ID
- 返回 Result()

## 点赞话题

- 地址 GET `/api/topic/vote`
- 参数
  - token
  - id: 话题ID
- 返回 Result(int) 返回点赞后当前话题的总赞数
