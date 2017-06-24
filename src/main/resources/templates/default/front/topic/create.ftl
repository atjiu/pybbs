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
          <div class="row">
            <div class="col-md-8">
              <div class="form-group">
                <label for="title">标题</label>
                <input type="text" class="form-control" id="title" name="title" value="${title!}" placeholder="标题">
              </div>
            </div>
            <div class="col-md-4">
              <div class="form-group">
                <label for="title">版块</label>
                <select name="tab" id="tab" class="form-control">
                  <@sections_tag>
                    <#list sections as section>
                      <option value="${section.name!}">${section.name!}</option>
                    </#list>
                  </@sections_tag>
                </select>
              </div>
            </div>
          </div>
        <#--editor component-->
          <#include "../components/editor.ftl"/>
          <@editor/>

        <#--label component-->
          <#include "../components/label.ftl"/>
          <@label/>

          <button type="button" class="btn btn-default" onclick="$('#editorForm').submit()">
            <span class="glyphicon glyphicon-send"></span> 发布
          </button>
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
        <p>• 每天每个用户最多只能发${site.maxCreateTopic}个话题。</p>
        <p>• 发布话题会扣除用户10积分</p>
      </div>
    </div>
  </div>
</div>
</@html>