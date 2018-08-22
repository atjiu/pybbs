<#include "../layout/" + layoutName/>
<@html page_title="编辑话题">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a data-pjax href="/">主页</a> / 编辑话题
      </div>
      <div class="panel-body">
        <form id="form">
          <div class="form-group">
            <label for="title">标题</label>
            <input type="text" class="form-control" id="title" name="title" value="${topic.title}" placeholder="标题">
          </div>
          <div class="form-group">
            <label for="content">内容</label>
            <#include "../components/editor.ftl"/>
            <@editor height="400px" content=model.formatContent(topic.content)/>
          </div>
          <div class="form-group">
            <label for="tag">标签</label>
            <#include "../components/tagsinput.ftl"/>
            <@tagsinput tags=topic.tag/>
          </div>
          <button type="button" id="btn" class="btn btn-default">
            <span class="glyphicon glyphicon-send"></span> 更新
          </button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/create_topic_guide.ftl"/>
    <@create_topic_guide/>
  </div>
</div>
<script type="text/javascript">
  $(function () {
    $("#btn").click(function () {
      var title = $("#title").val();
      var contentHtml = editor.txt.html();
      var contentText = editor.txt.text();
      var tag = $("#tag").val();
      if(!title) {
        toast("请输入标题");
      // } else if(!contentText) {
      //   toast("请输入内容");
      } else if(!tag) {
        toast("请输入标签");
      } else {
        $.ajax({
          url: '/api/topic/edit',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: '${topic.id}',
            title: title,
            content: contentText ? contentHtml : '',
            tag: tag
          },
          success: function(data){
            if(data.code === 200) {
              window.location.href = "/topic/" + data.detail.id;
            } else {
              toast(data.description);
            }
          }
        })
      }
    });
  })
</script>
</@html>