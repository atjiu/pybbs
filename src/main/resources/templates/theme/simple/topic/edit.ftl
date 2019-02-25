<#include "../layout/layout.ftl"/>
<@html page_title="编辑话题" page_tab="">
  <h3>编辑话题</h3>
  <table border=0 style="width: 100%;">
    <tr>
      <td width="40">标题</td>
      <td style="padding-right: 14px;"><input type="text" name="title" id="title" value="${topic.title}"
                                              placeholder="标题"/></td>
    </tr>
    <tr>
      <td valign="top">内容</td>
      <td style="padding-right: 14px;">
        <textarea name="content" id="content" rows="20" placeholder="内容，支持Markdown语法">${topic.content!?html}</textarea>
        <div><a href="javascript:uploadImageBtn();">上传图片</a></div>
      </td>
    </tr>
    <tr>
      <td>标签</td>
      <td style="padding-right: 14px;"><input type="text" name="tags" id="tags" value="${tags!}"
                                              placeholder="标签, 多个标签以 英文逗号 隔开"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>
        <br>
        <button onclick="update()">更新话题</button>
      </td>
    </tr>
  </table>
  <script>
    function update() {
      var title = $("#title").val();
      var content = $("#content").val();
      var tags = $("#tags").val();
      if (!title || title.length > 120) {
        alert("请输入标题，且最大长度在120个字符以内");
        return;
      }
      if (!tags || tags.split(",").length > 5) {
        alert("请输入标签，且最多只能填5个");
        return;
      }
      $.ajax({
        url: '/api/topic/${topic.id}',
        type: 'put',
        cache: false,
        async: false,
        dataType: 'json',
        headers: {
          "token": "${_user.token}",
        },
        contentType: "application/json",
        data: JSON.stringify({
          title: title,
          content: content,
          tags: tags,
        }),
        success: function (data) {
          if (data.code === 200) {
            window.location.href = "/topic/" + data.detail.topic.id
          } else {
            alert(data.description);
          }
        }
      })
    }
  </script>
  <#include "../components/upload.ftl"/>
</@html>
