#### 模块
##### get /api/section 模块列表

------------

接收 get 参数
示例：[/api/section](http://jfbbs.tomoya.cn/api/section "/api/section")

#### 主题
##### get /api/index 主题首页

------------

接收 get 参数

- p int 页数，默认1 **必填**
- tab String 主题分类 [参照模块列表接口](http://jfbbs.tomoya.cn/api/section "参照模块列表接口")
- size int 每一页的主题数量，默认20

示例：[/api/index](http://jfbbs.tomoya.cn/api/index "/api/index")

##### get /api/topic/:id 主题详情

------------

接收 get 参数

示例：[/api/topic/867bcaa553164b8c9baf80751d683a0f](http://jfbbs.tomoya.cn/api/topic/867bcaa553164b8c9baf80751d683a0f "/api/topic/867bcaa553164b8c9baf80751d683a0f")

##### post /api/topic/create 新建主题

------------

接收 post 参数

- token String 用户令牌 **必填**
- title String 标题 **必填**
- sid String 模块id [参照模块列表接口](http://jfbbs.tomoya.cn/api/section "参照模块列表接口") **必填**
- content String 主体内容 **必填**
- original_url 原文链接，原创可不填

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": {
        "modify_time": "2015-06-11 12:07:18",
        "title": "使用JFinal社区搭建自己的社区网站就是这么简单~~",
        "good": 1,
        "content": "**小白都会的使用JFinal社区搭建自己的社区网站的方法**\r\n#####...",
        "s_id": 2,
        "view": 2356,
        "in_time": "2015-05-05 16:55:44",
        "top": 1,
        "original_url": "",
        "id": "867bcaa553164b8c9baf80751d683a0f",
        "author_id": "1a973292fc004c...",
        "show_status": 1,
        "reposted": 0
    }
}
```

##### get /api/collect 收藏主题

------------

接收 get 参数

- token String 用户令牌 **必填**
- tid String 被收藏的主题id **必填**

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": null
}
```

##### post /api/collect/delete 取消收藏

------------

接收 get 参数

- token String 用户令牌 **必填**
- tid String 被取消收藏的主题id **必填**

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": null
}
```

#### 评论
##### post /api/reply/create 新建评论

------------

接收 post 参数

- token String 用户令牌 **必填**
- tid String 评论话题的id **必填**
- content String 评论的内容 **必填**
- quote String 引用：对另一个评论回复时，另一个评论的id

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": {
        "topic": {
            "modify_time": "2015-06-11 12:07:18",
            "title": "使用JFinal社区搭建自己的社区网站就是这么简单~~",
            "good": 1,
            "content": "**小白都会的使用JFinal社区搭建自己的社区网站的方法**\r\n##### ",
            "s_id": 2,
            "view": 2356,
            "in_time": "2015-05-05 16:55:44",
            "top": 1,
            "original_url": "",
            "id": "867bcaa553164b8c9baf80751d683a0f",
            "author_id": "1a973292fc004c...",
            "show_status": 1,
            "reposted": 0
        },
        "reply": {
            "in_time": "2015-06-28",
            "quote": "3196fd09d13f422c97b9c59f47e4e90e",
            "id": "8c78cda152fc4c76a47ac93aa5177b00",
            "quote_content": "测试回复",
            "author_id": "1a973292fc004c...",
            "tid": "867bcaa553164b8c9baf80751d683a0f",
            "content": "测试回复1",
            "quote_author_nickname": "朋也"
        }
    }
}
```

#### 用户
##### post /api/user 用户详情

------------

接收 post 参数

- token String 用户令牌 **必填**

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": {
        "gender": "男",
        "open_id": "BE24E41FE40C...",
        "signature": "一个爱好动漫的程序员",
        "expire_time": "2015-07-10 16:00:51",
        "avatar": "http://qzapp.qlogo.cn/qzapp/101203891/BE24E41FE40CA067D2063611B9469A35/50",
        "url": "http://jfbbs.tomoya.cn",
        "token": "9b76f4fcf654...",
        "score": 669,
        "mission": "2015-06-27",
        "password": "",
        "in_time": "2015-04-11 16:00:51",
        "nickname": "朋也",
        "id": "14fab41f30254d...",
        "thirdlogin_type": "qq",
        "email": "liygheart@qq.com"
    }
}
```

#### 消息通知
##### get /api/notification/countnotread 获取未读消息数

------------

接收 get 参数

- token String 用户令牌 **必填**

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": 3
}
```

##### get /api/notification 获取已读和未读消息

接收 get 参数

- token String 用户令牌 **必填**

返回值示例

```json
{
    "code": "200",
    "description": "success",
    "detail": {
        "oldMessages": {
            "totalRow": 188,
            "pageNumber": 1,
            "firstPage": true,
            "lastPage": false,
            "totalPage": 10,
            "pageSize": 20,
            "list": [
                {
                    "read": 1,
                    "in_time": "2015-06-26 08:52:46",
                    "from_author_id": "31f33c2a91cc4d...",
                    "nickname": "回声",
                    "id": 435,
                    "message": "回复了你的话题",
                    "author_id": "1a973292fc00...",
                    "rid": "1cb4fb1d2e2949e58bb96a247531fdb1",
                    "title": "大网易，人才辈出啊！！",
                    "tid": "15e5b091253641e78783a27e85a944f0"
                }
            ]
        },
        "notifications": [
            {
                "read": 0,
                "in_time": "2015-06-28 19:36:50",
                "from_author_id": "123",
                "nickname": null,
                "id": 449,
                "message": "回复了你的话题",
                "author_id": "1a973292fc0...",
                "rid": "8c78cda152fc4c76a47ac93aa5177b00",
                "title": "使用JFinal社区搭建自己的社区网站就是这么简单~~",
                "tid": "867bcaa553164b8c9baf80751d683a0f"
            }
        ]
    }
}
```
