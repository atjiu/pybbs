The apis styles on pybbs have all been changed to RESTFUL style, and the calling method has been adjusted accordingly.

1. All apis that need to pass the token, the token parameter should be placed in the request headers (headers)
2. All apis that need to pass parameters, parameters are passed in json format
3. The request is not only get, post, but also put, delete, please check the api documentation carefully.

Here's an example of a jQuery call to post:

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
