## 用户信息

- 地址 GET `/api/user/:username`
- 请求类型 application/json
- 参数
  - username: 用户名
- 返回 Result(Map)
  - 用户信息
  - 话题列表（7条）
  - 评论列表（7条）
  - 收藏条数

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "oAuthUsers": [
      {
        "id": 1,
        "oauthId": 6915570,
        "type": "GITHUB",
        "login": "tomoya92",
        "inTime": 1548734221000,
        "bio": "hello world",
        "email": "py2qiuse@gmail.com",
        "userId": 1
      }
    ],
    "comments": {
      "records": [
        {
          "inTime": 1548992961000,
          "topicId": 1,
          "commentUsername": "tomoya",
          "upIds": "2",
          "id": 1,
          "topicUsername": "tomoya",
          "title": "我是一个话题标题",
          "userId": 1,
          "content": "modify_comment_content"
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "searchCount": true,
      "pages": 1
    },
    "topics": {
      "records": [
        {
          "collectCount": 2,
          "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
          "title": "我是一个话题标题",
          "userId": 1,
          "good": false,
          "content": "我是话题内容\n\n我还支持Markdown语法哦\n\n![](https://avatars3.githubusercontent.com/u/6915570?s=460&v=4)\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n| Column A | Column B | Column C |\n| -------- | -------- | -------- |\n| A1       | B1       | C1       |\n| A2       | B2       | C2       |\n| A3       | B3       | C3       |\n\n[开发俱乐部](https://17dev.club/)",
          "commentCount": 4,
          "inTime": 1548992281000,
          "view": 20,
          "modifyTime": 1548992296000,
          "top": false,
          "upIds": "2",
          "id": 1,
          "username": "tomoya"
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "searchCount": true,
      "pages": 1
    },
    "collectCount": 1,
    "user": {
      "id": 1,
      "username": "tomoya",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
      "email": "py2qiuse@gmail.com",
      "website": "https://17dev.club/",
      "bio": "hello world",
      "score": 20,
      "inTime": 1548992041000,
      "emailNotification": false
    }
  }
}
```

## 用户话题列表

- 地址 GET `/api/user/:username/topics`
- 请求类型 application/json
- 参数
  - username: 用户名
  - pageNo: 页数
- 返回 Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "topics": {
      "records": [
        {
          "collectCount": 2,
          "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
          "title": "我是一个话题标题",
          "userId": 1,
          "good": false,
          "content": "我是话题内容\n\n我还支持Markdown语法哦\n\n![](https://avatars3.githubusercontent.com/u/6915570?s=460&v=4)\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n| Column A | Column B | Column C |\n| -------- | -------- | -------- |\n| A1       | B1       | C1       |\n| A2       | B2       | C2       |\n| A3       | B3       | C3       |\n\n[开发俱乐部](https://17dev.club/)",
          "commentCount": 4,
          "inTime": 1548992281000,
          "view": 20,
          "modifyTime": 1548992296000,
          "top": false,
          "upIds": "2",
          "id": 1,
          "username": "tomoya"
        }
      ],
      "total": 1,
      "size": 20,
      "current": 1,
      "searchCount": true,
      "pages": 1
    },
    "user": {
      "id": 1,
      "username": "tomoya",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
      "email": "py2qiuse@gmail.com",
      "website": "https://17dev.club/",
      "bio": "hello world",
      "score": 20,
      "inTime": 1548992041000,
      "emailNotification": false
    }
  }
}
```

## 用户评论列表

- 地址 GET `/api/user/:username/comments`
- 请求类型 application/json
- 参数
  - username: 用户名
  - pageNo: 页数
- 返回 Result(Map)

```json
{
  "code": 200,
  "description": "SUCCESS",
  "detail": {
    "comments": {
      "records": [
        {
          "inTime": 1548992961000,
          "topicId": 1,
          "commentUsername": "tomoya",
          "upIds": "2",
          "id": 1,
          "topicUsername": "tomoya",
          "title": "我是一个话题标题",
          "userId": 1,
          "content": "modify_comment_content"
        }
      ],
      "total": 1,
      "size": 20,
      "current": 1,
      "searchCount": true,
      "pages": 1
    },
    "user": {
      "id": 1,
      "username": "tomoya",
      "telegramName": null,
      "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
      "email": "py2qiuse@gmail.com",
      "website": "https://17dev.club/",
      "bio": "hello world",
      "score": 20,
      "inTime": 1548992041000,
      "emailNotification": false
    }
  }
}
```

## 用户收藏列表

- 地址 GET `/api/user/:username/collects`
- 请求类型 application/json
- 参数
  - username: 用户名
  - pageNo: 页数
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
      "email": "py2qiuse@gmail.com",
      "website": "https://17dev.club/",
      "bio": "hello world",
      "score": 20,
      "inTime": 1548992041000,
      "emailNotification": false
    },
    "collects": {
      "records": [
        {
          "collectCount": 2,
          "avatar": "http://localhost:8080/static/upload/avatar/tomoya/avatar.png",
          "title": "我是一个话题标题",
          "userId": 1,
          "good": false,
          "content": "我是话题内容\n\n我还支持Markdown语法哦\n\n![](https://avatars3.githubusercontent.com/u/6915570?s=460&v=4)\n\n- 无序列表1\n- 无序列表2\n- 无序列表3\n\n1. 有序列表1\n2. 有序列表2\n3. 有序列表3\n\n| Column A | Column B | Column C |\n| -------- | -------- | -------- |\n| A1       | B1       | C1       |\n| A2       | B2       | C2       |\n| A3       | B3       | C3       |\n\n[开发俱乐部](https://17dev.club/)",
          "commentCount": 4,
          "tags": [
            {
              "id": 1,
              "name": "一个标签",
              "description": "标签描述，解释一下标签让人能更明白它的意思",
              "icon": "http://spring.io/img/homepage/icon-spring-boot.svg",
              "topicCount": 1,
              "inTime": 1548992281000
            }
          ],
          "inTime": 1548992281000,
          "view": 20,
          "modifyTime": 1548992296000,
          "top": false,
          "upIds": "2",
          "id": 1,
          "username": "tomoya"
        }
      ],
      "total": 1,
      "size": 20,
      "current": 1,
      "searchCount": true,
      "pages": 1
    }
  }
}
```
