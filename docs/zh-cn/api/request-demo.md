pybbs上的接口风格已经全都换成RESTFUL风格的了，调用方式也有了相应的调整

1. 所有需要传token的接口，token参数要放在请求头里(headers)
2. 所有需要传参数的接口，参数都以 json 的形式传递
3. 请求不单单是get, post了，还加入了put, delete，请仔细查看接口文档

下面给一个发帖的jQuery调用示例:

```js
$.ajax({
  url: '/api/topic',
  type: 'post',
  cache: false,
  async: false,
  headers: {
    'token': '8f2e6b0d-5a7a-44eb-9c96-4f87d55c212e'
  },
  contentType: 'application/json',
  data: JSON.stringify({
    title: title,
    content: content,
    tags: tags,
  }),
  success: function(data) {
    if (data.code === 200) {
      window.location.href = "/topic/" + data.detail.topic.id
    } else {
      alert(data.description);
    }
  }
})
```
