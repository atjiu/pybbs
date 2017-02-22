<#include "../common/layout.ftl">
<@html page_title="话题编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/"><@spring.message "site.panel.header.home"/></a> / <@spring.message "site.panel.header.editTopic"/>
      </div>
      <div class="panel-body">
        <form method="post" action="/topic/${topic.id}/edit" id="topicForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="title"><@spring.message "site.form.topic.title"/></label>
            <input type="text" class="form-control" id="title" name="title" value="${topic.title!}" placeholder="<@spring.message "site.form.topic.title"/>">
          </div>
          <div class="form-group">
            <label for="title"><@spring.message "site.form.topic.content"/></label>
            <textarea name="content" id="content" rows="15" class="form-control">${topic.content?html!}</textarea>
          </div>
          <div class="form-group">
            <label for="title"><@spring.message "site.form.topic.section"/></label>
            <select name="tab" id="tab" class="form-control">
              <#list sections as section>
                <option value="${section.name}"
                        <#if topic.tab == section.name>selected="selected"</#if>>
                ${section.name}
                </option>
              </#list>
            </select>
          </div>
          <button type="button" onclick="publishTopic();" class="btn btn-default"><@spring.message "site.button.save"/></button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/guide.ftl"/>
  </div>
</div>
<#if _editor == 'markdown'>
  <link rel="stylesheet" href="/static/bootstrap/libs/editor/editor.css"/>
  <script type="text/javascript" src="/static/bootstrap/libs/webuploader/webuploader.withoutimage.js"></script>
  <script type="text/javascript" src="/static/bootstrap/libs/markdownit.js"></script>
  <script type="text/javascript" src="/static/bootstrap/libs/editor/editor.js"></script>
  <script type="text/javascript" src="/static/bootstrap/libs/editor/ext.js"></script>
  <script type="text/javascript">
    var editor = new Editor({element: $("#content")[0], status: []});
    editor.render();
  </script>
<#elseif _editor == 'wangeditor'>
  <link rel="stylesheet" href="/static/bootstrap/libs/wangeditor/css/wangEditor.min.css">
  <script src="/static/bootstrap/libs/wangeditor/js/wangEditor.min.js"></script>
  <script>
    var editor = new wangEditor('content');
    editor.config.menus = [
      'source',
      '|',
      'bold',
      'underline',
      'italic',
      'strikethrough',
      'forecolor',
      'bgcolor',
      '|',
      'quote',
      'fontfamily',
      'fontsize',
      'head',
      'unorderlist',
      'orderlist',
      '|',
      'link',
      'unlink',
      'table',
      '|',
      'img',
      'insertcode'
    ];
    editor.config.uploadImgUrl = '/wangEditorUpload';
    editor.config.uploadParams = {
      '${_csrf.parameterName}': '${_csrf.token}'
    };
    editor.config.uploadImgFileName = 'file';
    editor.create();
  </script>
</#if>
</@html>