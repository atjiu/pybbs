<#include "../common/layout.ftl"/>
<@html page_title="${topic.title} - 内容追加">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / <a href="/t/${topic.id}">${topic.title}</a> / 内容追加
            </div>
            <div class="panel-body">
                <form action="/t/append/${topic.id}" method="post" id="replyForm">
                    <div class="form-group">
                        <textarea name="content" id="content" rows="15"
                                  class="form-control" placeholder="支持Markdown语法哦~"></textarea>
                    </div>
                    <button type="button" class="btn btn-default" onclick="replySubmit()">提交</button>
                    <span id="error_message"></span>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <#include "../components/userinfo.ftl">
        <@info/>
    </div>
</div>
<link rel="stylesheet" href="/static/libs/editor/editor.css"/>
<script type="text/javascript" src="/static/libs/webuploader/webuploader.withoutimage.js"></script>
<script type="text/javascript" src="/static/libs/markdownit.js"></script>
<script type="text/javascript" src="/static/libs/editor/editor.js"></script>
<script type="text/javascript" src="/static/libs/editor/ext.js"></script>
<script type="text/javascript">
    var editor = new Editor({element: $("#content")[0], status: []});
    editor.render();

    var $input = $(editor.codemirror.display.input);
    $input.keydown(function(event){
        if (event.keyCode === 13 && (event.ctrlKey || event.metaKey)) {
            event.preventDefault();
            if(editor.codemirror.getValue().length == 0) {
                $("#error_message").html("回复内容不能为空");
            } else {
                $("#replyForm").submit();
            }
        }
    });
</script>
</@html>
