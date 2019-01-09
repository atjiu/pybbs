首页接口分别有以下几个

## 首页列表

- 地址 GET `/api/` 或者 `/api/index`
- 参数
  - pageNo: 页码
  - tab: 分类，分别是 精华：`good` 无人评论：`noanswer` 热门：`hot` 最新：`newest`
- 返回 Result(IPage<Map>)
 
## 登录

- 地址 POST `/api/login`
- 参数
  - username: 用户名
  - password: 密码
- 返回 Result(User)

## 注册

- 地址 POST `/api/register`
- 参数
  - username: 用户名
  - password: 密码
- 返回 Result(User)
