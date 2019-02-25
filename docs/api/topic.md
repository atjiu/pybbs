## Topic Detail

- Address GET `/api/topic/:id`
- Request Type application/json
- Params
  - token: **Optional**, if you pass the token, there will be one more object in the return value,
    showing that the current topic is not collected by yourself. If you do not fill it, there is no such object.
  - id
- Return Result(Map)

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

## Create Topic

- Address POST `/api/topic`
- Request Type application/json
- Params
  - token
  - title
  - content: [Optional]
  - tags: tags, multiple tags separated by commas, up to 5
- Return Result(Topic)

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

## Edit Topic

- Address PUT `/api/topic`
- Request Type application/json
- Params
  - token
  - id
  - title
  - content: [Optional]
  - tags: tags, multiple tags separated by commas, up to 5
- Return Result(Topic)

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

## Delete Topic

- Address DELETE `/api/topic`
- Request Type application/json
- Params
  - token
  - id
- Return Result()

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": null
}
```

## Like Topic

- Address GET `/api/topic/:id/vote`
- Request Type application/json
- Params
  - token
  - id
- Return Result(int) // Return the total number of likes for the current topic after the like

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": 1
}
```
