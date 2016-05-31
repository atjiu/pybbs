<#include "../common/layout.ftl"/>
<@html page_title="${topic.title!} 回复编辑">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / <a href="/t/${topic.id!}">${topic.title!}</a> / 编辑回复
            </div>
            <div class="panel-body">
                <form action="/r/edit" method="post" id="replyForm">
                    <input type="hidden" name="id" value="${reply.id!}"/>

                    <div class="form-group">
                        <textarea name="content" id="content" onkeydown="qsend(event)" rows="15"
                                  class="form-control">${reply.content!}</textarea>
                    </div>
                    <button type="button" onclick="replySubmit()" class="btn btn-default">保存</button>
                    <span id="error_message"></span>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
<script type="text/javascript" src="/static/js/textarea.js"></script>
</@html>