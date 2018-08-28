<#include "../layout/layout.ftl">
<@html page_title="评论编辑" page_tab="comment">
  <section class="content-header">
    <h1>
      评论
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/comment/list">评论</a></li>
      <li class="active">编辑</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">评论编辑</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form id="form">
          <div class="form-group">
            <label>话题</label>
            <p>${topic.title!}</p>
          </div>
          <div class="form-group">
            <label>评论内容</label>
            <#include "../../front/components/editor.ftl"/>
            <@editor height="400px" content="${comment.content!}"/>
          </div>
          <button type="submit" id="btn" class="btn btn-sm btn-primary">提交</button>
        </form>
      </div>
    </div>
  </section>
<script>
  $(function() {
    $("#form").submit(function() {
      var contentHtml = editor.txt.html();
      var contentText = editor.txt.text();
      if(!contentText) {
        toast("评论不能为空");
      } else {
        $.ajax({
          url: '/admin/comment/edit',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: '${comment.id}',
            content: contentHtml
          },
          success: function(data){
            if(data.code === 200) {
              window.location.href = "/admin/comment/list";
            } else {
              toast(data.description);
            }
          }
        })
        return false;
      }
    })
  })
</script>
</@html>