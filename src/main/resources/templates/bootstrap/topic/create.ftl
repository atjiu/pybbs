<#include "../common/layout.ftl">
<@html page_title="发布话题">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 发布话题
      </div>
      <div class="panel-body">
        <form method="post" action="/topic/save" id="topicForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="标题">
          </div>
          <div class="form-group">
            <label for="title">内容</label>
            <textarea name="content" id="content" rows="15" class="form-control" placeholder="支持Markdown语法哦~"></textarea>
          </div>
          <div class="form-group">
            <label for="title">版块</label>
            <select name="tab" id="tab" class="form-control">
              <#list sections as section>
                <option value="${section}">${section}</option>
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
        <p>• 话题内容支持 Markdown 文本标记语法</p>
        <p>• 发布话题奖励 5 积分，但是被管理员删除话题将会扣除作者 7 积分</p>
        <p>• 发布话题之前,可以点击预览查看</p>
        <p>• ctrl+b 粗体</p>
        <p>• ctrl+i 斜体</p>
        <p>• ctrl+k 插入链接</p>
        <p>• ctrl+alt+i 插入图片</p>
        <p>• ctrl+' 插入引用</p>
        <p>• ctrl+alt+l 有序列表</p>
        <p>• ctrl+l 无序列表</p>
        <p>• 截图在编辑器里直接粘贴即可上传(IE10+)</p>
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