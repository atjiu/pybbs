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
                        <textarea name="content" id="content" rows="15" onkeydown="qsend(event)"
                                  class="form-control" placeholder="支持Markdown语法哦~"></textarea>
                    </div>
                    <button type="button" class="btn btn-default" onclick="replySubmit()">提交</button>
                    <button type="button" onclick="previewContent();" class="btn btn-default pull-right">预览</button>
                    <span id="error_message"></span>
                </form>
                <div id="preview"></div>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <#include "../components/userinfo.ftl">
        <@info/>
    </div>
</div>
<script type="text/javascript" src="/static/js/marked.js"></script>
<script type="text/javascript" src="/static/js/textarea.js"></script>
</@html>
