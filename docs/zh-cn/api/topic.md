## 话题详情

- 地址 GET `/api/topic/:id`
- 请求类型 application/json
- 参数
  - token: **可不传**，传token返回值里会多一个这个话题是否被自己收藏，如果不填就没有这个对象
  - id: 话题id
- 返回 Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "comments": [
      {
        "id": 1,
        "topicId": 1,
        "userId": 1,
        "content": "modify_comment_content",
        "inTime": 1548992961000,
        "commentId": null,
        "upIds": "2",
        "username": "tomoya",
        "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
        "layer": 0
      },
      {
        "id": 5,
        "topicId": 1,
        "userId": 2,
        "content": "@tomoya 我是第三条评论",
        "inTime": 1548993369000,
        "commentId": 1,
        "upIds": null,
        "username": "test",
        "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
        "layer": 1
      },
      {
        "id": 4,
        "topicId": 1,
        "userId": 2,
        "content": "我是第二条评论",
        "inTime": 1548993303000,
        "commentId": null,
        "upIds": null,
        "username": "test",
        "avatar": "http://localhost:8080/static/upload/avatar/test/avatar.png",
        "layer": 0
      }
    ],
    "topic": {
      "id": 1,
      "title": "我是一个话题标题",
      "content": "我是话题内容\n\n我还支持Markdown语法哦\n\n![](https://avatars3.githubusercontent.com/u/6915570?s=460&v=4)\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n| Column A | Column B | Column C |\n| -------- | -------- | -------- |\n| A1       | B1       | C1       |\n| A2       | B2       | C2       |\n| A3       | B3       | C3       |\n\n[开发俱乐部](https://17dev.club/)",
      "inTime": 1548992281000,
      "modifyTime": 1548992296000,
      "userId": 1,
      "commentCount": 4,
      "collectCount": 1,
      "view": 13,
      "top": false,
      "good": false,
      "upIds": null
    },
    "topicUser": {
      "id": 1,
      "username": "tomoya",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
      "email": null,
      "website": null,
      "bio": null,
      "score": 20,
      "inTime": 1548992041000,
      "emailNotification": false
    },
    "collect": {
      "topicId": 1,
      "userId": 2,
      "inTime": 1548999420000
    },
    "collects": [
      {
        "topicId": 1,
        "userId": 2,
        "inTime": 1548999420000
      }
    ],
    "tags": [
      {
        "id": 1,
        "name": "一个标签",
        "description": "标签描述，解释一下标签让人能更明白它的意思",
        "icon": "http://spring.io/img/homepage/icon-spring-boot.svg",
        "topicCount": 1,
        "inTime": 1548992281000
      }
    ]
  }
}
```

## 发布话题

- 地址 POST `/api/topic`
- 请求类型 application/json
- 参数
  - token
  - title: 话题标题
  - content: 话题内容（可为空）
  - tags: 标签，多个标签之间英文逗号隔开，最多5个
- 返回 Result(Topic)

```json
{
    "code": 200,
    "description": "SUCCESS",
    "detail": {
        "topic": {
            "id": 11,
            "title": "222",
            "content": "",
            "inTime": 1551063711700,
            "modifyTime": null,
            "userId": 1,
            "commentCount": 0,
            "collectCount": 0,
            "view": 0,
            "top": false,
            "good": false,
            "upIds": null
        },
        "tags": [
            {
                "id": 3,
                "name": "22",
                "description": null,
                "icon": null,
                "topicCount": 3,
                "inTime": 1550826849000
            }
        ]
    }
}
```

## 编辑话题

- 地址 PUT `/api/topic`
- 请求类型 application/json
- 参数
  - token
  - id: 话题ID
  - title: 话题标题
  - content: 话题内容（可为空）
  - tags: 标签，多个标签之间英文逗号隔开，最多5个
- 返回 Result(Topic)

```json
{
    "code": 200,
    "description": "SUCCESS",
    "detail": {
        "topic": {
            "id": 11,
            "title": "333",
            "content": null,
            "inTime": 1551063712000,
            "modifyTime": 1551064039058,
            "userId": 1,
            "commentCount": 0,
            "collectCount": 0,
            "view": 0,
            "top": false,
            "good": false,
            "upIds": null
        },
        "tags": [
            {
                "id": 4,
                "name": "333",
                "description": null,
                "icon": null,
                "topicCount": 1,
                "inTime": 1551064026000
            }
        ]
    }
}
```

## 删除话题

- 地址 DELETE `/api/topic`
- 请求类型 application/json
- 参数
  - token
  - id: 话题ID
- 返回 Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## 点赞话题

- 地址 GET `/api/topic/:id/vote`
- 请求类型 application/json
- 参数
  - token
  - id: 话题ID
- 返回 Result(int) 返回点赞后当前话题的总赞数

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```
