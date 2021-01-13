<#include "../layout/layout.ftl"/>
<@html page_title="编辑话题" page_tab="">
    <div>
        <div>
            <a href="/">主页</a> / <a href="/topic/${topic.id}">${topic.title}</a> / 编辑评论
            <a href="javascript:uploadImageBtn();" class="pull-right">上传图片</a>
        </div>
        <div style="margin-top: 10px;">
            <textarea name="content" id="content" rows="15">${comment.content?html}</textarea>
        </div>
        <div>
            <button id="btn">更新</button>
        </div>
    </div>
    <script>
        $(function () {
            $("#btn").click(function () {
                var content = $("#content").val();
                if (!content) {
                    alert("请输入内容");
                    return;
                }
                $.ajax({
                    url: '/api/comment/${comment.id}',
                    type: 'put',
                    cache: false,
                    async: false,
                    contentType: 'application/json',
                    dataType: 'json',
                    data: JSON.stringify({
                        content: content,
                    }),
                    headers: {
                        'token': '${_user.token!}'
                    },
                    success: function (data) {
                        if (data.code === 200) {
                            window.location.href = "/topic/${comment.topicId}";
                        } else {
                            alert(data.description);
                        }
                    }
                })
            })
        });
    </script>
    <#include "../components/upload.ftl"/>
</@html>
