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

          <button type="submit" class="btn btn-default">
            <span class="glyphicon glyphicon-send"></span> 发布
          </button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/create_topic_guide.ftl"/>
    <@create_topic_guide/>
  </div>
</div>
</@html>