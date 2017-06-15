<#include "../common/layout.ftl">
<@html page_title="发布话题">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 发布话题
      </div>
      <div class="panel-body">
        <form method="post" action="/topic/save" id="editorForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" name="title" value="${title!}" placeholder="标题">
          </div>
          <#include "../components/editor.ftl"/>
          <@editor/>
          <div class="form-group">
            <label for="title">版块</label>
            <select name="tab" id="tab" class="form-control">
              <#list sections as section>
                <option value="${section.name!}">${section.name!}</option>
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
        <p>• 请在标题中描述内容要点。如果一件事情在标题的长度内就已经可以说清楚，那就没有必要写正文了。</p>
        <p>• 在最后，请为你的主题选择一个节点。恰当的归类会让你发布的信息更加有用。</p>
        <p>• 保持对陌生人的友善。用知识去帮助别人。</p>
      </div>
    </div>
  </div>
</div>
</@html>