<#include "../common/layout.ftl">
<@html page_title="话题编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 话题编辑
      </div>
      <div class="panel-body">
        <form method="post" action="/admin/topic/${topic.id}/edit" id="editorForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="oldLabels" value="${topic.labelId!}"/>

          <div class="row">
            <div class="col-md-8">
              <div class="form-group">
                <label for="title">标题</label>
                <input type="text" class="form-control" id="title" name="title" value="${topic.title!}"
                       placeholder="标题">
              </div>
            </div>
            <div class="col-md-4">
              <div class="form-group">
                <label for="title">版块</label>
                <select name="tab" id="tab" class="form-control">
                  <@sections_tag>
                    <#list sections as section>
                      <option value="${section.name}"
                              <#if topic.tab == section.name>selected="selected"</#if>>
                      ${section.name}
                      </option>
                    </#list>
                  </@sections_tag>
                </select>
              </div>
            </div>
          </div>

        <#--editor component-->
          <#include "../../front/components/editor.ftl"/>
          <@editor content=topic.content/>

        <#--label component-->
          <#include "../../front/components/label.ftl"/>
          <@label/>

          <button type="button" onclick="javascript:$('#editorForm').submit()" class="btn btn-default">
            <span class="glyphicon glyphicon-send"></span> 发布
          </button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../front/components/create_topic_guide.ftl"/>
    <@create_topic_guide/>
  </div>
</div>
</@html>