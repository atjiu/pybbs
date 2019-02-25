首页接口分别有以下几个

## 首页列表

- 地址 GET `/api/` 或者 `/api/index`
- 请求类型 application/x-www-form-urlencoded
- 参数
  - pageNo: 页码
  - tab: 分类，分别是 精华：`good` 无人评论：`noanswer` 热门：`hot` 最新：`newest`
- 返回 Result(IPage<Map>)

```json
{
  "code":200,
  "description":"SUCCESS",
  "detail":{
    "records":[
      {
        "collectCount":0,
        "avatar":"http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
        "title":"我是一个话题标题",
        "userId":1,
        "good":false,
        "content":"我是话题内容\n\n我还支持Markdown语法哦\n\n![](https://avatars3.githubusercontent.com/u/6915570?s=460&v=4)\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n| Column A | Column B | Column C |\n| -------- | -------- | -------- |\n| A1       | B1       | C1       |\n| A2       | B2       | C2       |\n| A3       | B3       | C3       |\n\n[开发俱乐部](https://17dev.club/)",
        "commentCount":0,
        "tags":[
          {
            "id":1,
            "name":"一个标签",
            "description":null,
            "icon":null,
            "topicCount":1,
            "inTime":1548992281000
          }
        ],
        "inTime":1548992281000,
        "view":2,
        "modifyTime":1548992296000,
        "top":false,
        "id":1,
        "username":"tomoya"
      }
    ],
    "total":1,
    "size":20,
    "current":1,
    "searchCount":true,
    "pages":1
  }
}
```

## 登录

- 地址 POST `/api/login`
- 请求类型 application/json
- 参数
  - username: 用户名
  - password: 密码
- 返回 Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "user": {
      "id": 1,
      "username": "tomoya",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
      "email": null,
      "website": null,
      "bio": null,
      "score": 10,
      "inTime": 1548992041000,
      "emailNotification": false
    },
    "token": "208bb4c1-daf1-4a32-b198-7f4db8f6d565"
  }
}
```

## 注册

- 地址 POST `/api/register`
- 请求类型 application/json
- 参数
  - username: 用户名
  - password: 密码
- 返回 Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "user": {
      "id": 2,
      "username": "test",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
      "email": null,
      "website": null,
      "bio": null,
      "score": 0,
      "inTime": 1548992486000,
      "emailNotification": false
    },
    "token": "b7ee4a41-48d9-4185-9ab7-bcd202719ded"
  }
}
```

## 标签

- 地址 POST `/api/tags`
- 请求类型 application/x-www-form-urlencoded
- 参数
  - pageNo
- 返回 Result(List<Tag>)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "records": [
      {
        "id": 1,
        "name": "一个标签",
        "description": "标签描述，解释一下标签让人能更明白它的意思",
        "icon": "http://spring.io/img/homepage/icon-spring-boot.svg",
        "topicCount": 1,
        "inTime": 1548992281000
      }
    ],
    "total": 1,
    "size": 20,
    "current": 1,
    "searchCount": true,
    "pages": 1
  }
}
```

## 上传头像

- 地址 POST `/api/upload`
- 请求类型 application/form-data
- 参数
  - token
  - file: 上传图片的文件对象
  - type: 上传图片的类型，如果上传头像请填 `avatar`，如果是发帖上传图片请填 `topic`
- 返回 Result(String) // 上传成功后，会返回图片的访问地址

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png"
}
```
