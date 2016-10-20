<#include "../common/layout.ftl">
<@html page_title="话题编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 话题编辑
      </div>
      <div class="panel-body">
        <form method="post" action="/topic/${topic.id}/edit" id="topicForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" name="title" value="${topic.title!}" placeholder="标题">
          </div>
          <div class="form-group">
            <label for="title">内容</label>
            <textarea name="content" id="content" rows="15" class="form-control">${topic.content?html!}</textarea>
          </div>
          <div class="form-group">
            <label for="title">版块</label>
            <select name="tab" id="tab" class="form-control">
              <#list sections as section>
                <option value="${section}"
                        <#if topic.tab == section>selected="selected"</#if>>
                ${section}
                </option>
              </#list>
            </select>
          </div>
          <button type="button" onclick="publishTopic();" class="btn btn-default">发布</button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <div class="panel panel-default">
      <div class="panel-heading">
        <b>话题发布指南</b>
      </div>
      <div class="panel-body">
        <p>• 问题标题: 请用准确的语言描述您发布的问题思想</p>
        <p>• 添加标签: 添加一个或者多个合适的标签, 让您发布的问题得到更多有相同兴趣的人参与.</p>
        <p>• 给话题选择合适的板块方便查找浏览</p>
      </div>
    </div>
  </div>
</div>
<link rel="stylesheet" href="/static/bootstrap/libs/editor/editor.css"/>
<script type="text/javascript" src="/static/bootstrap/libs/webuploader/webuploader.withoutimage.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/markdownit.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/editor.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/ext.js"></script>
<script type="text/javascript">
  var editor = new Editor({element: $("#content")[0], status: []});
  editor.render();
</script>
</@html>