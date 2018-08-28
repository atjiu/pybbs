<#include "../layout/layout.ftl">
<@html page_title="评论列表" page_tab="comment">
  <section class="content-header">
    <h1>
      评论
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/comment/list">评论</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">评论列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>话题</th>
            <th>内容</th>
            <th>用户</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.content as comment>
            <tr>
              <td>${comment.id}</td>
              <td><a href="/topic/${comment.topic_id}" target="_blank">${comment.topic_title!}</a></td>
              <td>${comment.content!}</td>
              <td>${comment.username!}</td>
              <td>${comment.in_time!}</td>
              <td>
                <#if sec.allGranted('comment:edit')>
                  <a href="/admin/comment/edit?id=${comment.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.allGranted('comment:delete')>
                  <button onclick="deleteBtn('${comment.id}')" class="btn btn-sm btn-danger">删除</button>
                </#if>
              </td>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
      <#if page.totalPages &gt; 1>
        <div class="box-footer clearfix">
          <#include "../layout/paginate.ftl">
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/admin/comment/list" urlParas=""/>
        </div>
      </#if>
    </div>
  </section>
<script>
  function deleteBtn(id) {
    if (confirm('确定要删除这条评论吗？')) {
      $.ajax({
        url: '/admin/comment/delete',
        type: 'get',
        async: false,
        cache: false,
        dataType: 'json',
        data: {
          id: id
        },
        success: function(data) {
          if (data.code === 200) {
            window.location.reload();
          } else {
            toast(data.description);
          }
        }
      })
    }
  }
</script>
</@html>