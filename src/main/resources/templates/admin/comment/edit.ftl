<#include "../layout/layout.ftl">
<@html page_title="评论编辑" page_tab="comment">
    <section class="content-header">
        <h1>
            评论
            <small>列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/admin/comment/list">评论</a></li>
            <li class="active">编辑</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">评论编辑</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="form-group">
                    <textarea name="content" id="content" class="form-control">${comment.content?html}</textarea>
                </div>
                <div class="form-group">
                    <button type="button" id="btn" class="btn btn-primary">
                        <span class="glyphicon glyphicon-send"></span> 更新
                    </button>
                </div>
            </div>
        </div>
    </section>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/codemirror.min.css" rel="stylesheet">
    <style>
        .CodeMirror {
            border: 1px solid #ddd;
        }
    </style>
    <script src="/static/theme/default/js/codemirror.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/mode/markdown/markdown.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.47.0/addon/display/placeholder.min.js"></script>
    <script>
        $(function () {
            CodeMirror.keyMap.default["Shift-Tab"] = "indentLess";
            CodeMirror.keyMap.default["Tab"] = "indentMore";
            var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
                lineNumbers: true,     // 显示行数
                indentUnit: 4,         // 缩进单位为4
                tabSize: 4,
                matchBrackets: true,   // 括号匹配
                mode: 'markdown',     // Markdown模式
                lineWrapping: true,    // 自动换行
            });

            $("#btn").click(function () {
                var content = editor.getDoc().getValue();
                if (!content) {
                    toast("请输入内容");
                    return;
                }
                $.post("/admin/comment/edit", {
                    id: ${comment.id},
                    content: content
                }, function (data) {
                    if (data.code === 200) {
                        toast("更新成功", "success");
                        setTimeout(function () {
                            window.location.href = "/admin/comment/list";
                        }, 700);
                    } else {
                        toast(data.description);
                    }
                })
            })
        });
    </script>
</@html>
