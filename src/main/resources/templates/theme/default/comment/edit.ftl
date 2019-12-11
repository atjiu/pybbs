<#include "../layout/layout.ftl"/>
<@html page_title="编辑话题" page_tab="">
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-header">
                    <a href="/">主页</a> / <a href="/topic/${topic.id}">${topic.title}</a> / 编辑评论
                    <span class="pull-right">
            <a href="javascript:uploadFile('topic');">上传图片</a>&nbsp;|
            <a href="javascript:uploadFile('video');">上传视频</a>
          </span>
                </div>
                <textarea name="content" id="content" class="form-control">${comment.content?html}</textarea>
                <div class="card-body">
                    <button type="button" id="btn" class="btn btn-info">
                        <span class="glyphicon glyphicon-send"></span> 更新
                    </button>
                </div>
            </div>
        </div>
        <div class="col-md-3 d-none d-md-block">
            <#include "../components/markdown_guide.ftl"/>
            <#include "../components/create_topic_guide.ftl"/>
        </div>
    </div>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/codemirror.min.css" rel="stylesheet">
    <style>
        .CodeMirror {
            border-top: 0;
        }
    </style>
    <script src="/static/theme/default/js/codemirror.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/mode/markdown/markdown.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/addon/display/placeholder.min.js"></script>
    <script>
        $(function () {
            CodeMirror.keyMap.default["Shift-Tab"] = "indentLess";
            CodeMirror.keyMap.default["Tab"] = "indentMore";
            window.editor = CodeMirror.fromTextArea(document.getElementById("content"), {
                lineNumbers: true,     // 显示行数
                indentUnit: 4,         // 缩进单位为4
                tabSize: 4,
                matchBrackets: true,   // 括号匹配
                mode: 'markdown',     // Markdown模式
                lineWrapping: true,    // 自动换行
            });

            $("#btn").click(function () {
                var content = window.editor.getDoc().getValue();
                if (!content) {
                    err("请输入内容");
                    return;
                }
                $.ajax({
                    url: '/api/comment/${comment.id}',
                    cache: false,
                    async: false,
                    type: 'put',
                    dataType: 'json',
                    contentType: 'application/json',
                    headers: {
                        'token': '${_user.token}'
                    },
                    data: JSON.stringify({
                        content: content,
                    }),
                    success: function (data) {
                        if (data.code === 200) {
                            suc("更新成功");
                            setTimeout(function () {
                                window.location.href = "/topic/${comment.topicId}";
                            }, 700);
                        } else {
                            err(data.description);
                        }
                    }
                })
            })
        });
    </script>
    <#include "../components/upload.ftl"/>
</@html>
