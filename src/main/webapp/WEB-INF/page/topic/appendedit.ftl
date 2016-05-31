<#include "../common/layout.ftl"/>
<@html page_title="${topic.title} - 追加内容编辑">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / <a href="/t/${topic.id}">${topic.title}</a> / 追加内容编辑
            </div>
            <div class="panel-body">
                <form action="/t/appendedit" method="post" id="replyForm">
                    <input type="hidden" name="id" value="${topicAppend.id!}"/>

                    <div class="form-group">
                        <textarea name="content" id="content" rows="15" onkeydown="qsend(event)"
                                  class="form-control">${topicAppend.content!}</textarea>
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
<script type="text/javascript" src="/static/js/textarea.js"></script>
</@html>