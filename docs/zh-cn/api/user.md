## 用户信息

- 地址 GET `/api/user/:username`
- 参数
  - username: 用户名
- 返回 Result(Map)
  - 用户信息
  - 话题列表（7条）
  - 评论列表（7条）
  - 收藏条数

## 用户话题列表

- 地址 GET `/api/user/:username/topics`
- 参数
  - username: 用户名
  - pageNo: 页数
- 返回 Result(Map)

## 用户评论列表

- 地址 GET `/api/user/:username/comments`
- 参数
  - username: 用户名
  - pageNo: 页数
- 返回 Result(Map)

## 用户收藏列表

- 地址 GET `/api/user/:username/collects`
- 参数
  - username: 用户名
  - pageNo: 页数
- 返回 Result(Map)
