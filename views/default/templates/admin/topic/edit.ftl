<#include "../layout/layout.ftl">
<@html page_title="话题编辑" page_tab="topic">
  <section class="content-header">
    <h1>
      话题
      <small>编辑</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/topic/list">话题</a></li>
      <li class="active">编辑</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">话题编辑</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form id="form">
          <div class="form-group">
            <label>标题</label>
            <input type="text" id="title" value="${topic.title!}" class="form-control"/>
          </div>
          <div class="form-group">
            <label>内容</label>
            <#include "../../front/components/editor.ftl"/>
            <@editor height="400px" content=model.formatContent(topic.content)/>
          </div>
          <button type="submit" id="btn" class="btn btn-sm btn-primary">提交</button>
        </form>
      </div>
    </div>
  </section>
<script>
  $(function() {
    $("#form").submit(function() {
      var title = $("#title").val();
      var contentHtml = editor.txt.html();
      if(!title) {
        toast("请输入标题");
      } else if(!contentHtml) {
        toast("请输入内容");
      } else {
        $.ajax({
          url: '/admin/topic/edit',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: '${topic.id}',
            title: title,
            content: contentHtml
          },
          success: function(data){
            if(data.code === 200) {
              window.location.href = "/admin/topic/list";
            } else {
              toast(data.description);
            }
          }
        })
        return false;
      }
    });
  })
</script>
</@html>