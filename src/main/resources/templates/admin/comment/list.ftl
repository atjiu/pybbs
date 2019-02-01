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
        <form action="/admin/comment/list" class="form-inline">
          <div class="form-group" style="margin-bottom: 10px;">
            <input type="text" readonly id="startDate" name="startDate" value="${startDate!}"
                   class="form-control" placeholder="开始时间">
            <input type="text" readonly id="endDate" name="endDate" value="${endDate!}"
                   class="form-control" placeholder="结束时间">
            <input type="text" name="username" value="${username!}" class="form-control" placeholder="用户名">
            <button type="submit" class="btn btn-primary btn-sm">搜索</button>
          </div>
        </form>
        <table class="table table-bordered table-striped">
          <thead>
          <tr>
            <th>#</th>
            <th>话题</th>
            <th>用户</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.records as comment>
            <tr>
              <td>${comment.id}</td>
              <td><a href="/topic/${comment.topicId}" target="_blank">${comment.title!}</a></td>
              <td>${comment.username!}</td>
              <td>${comment.inTime!}</td>
              <td>
                <#if sec.hasPermission("comment:edit")>
                  <a href="/admin/comment/edit?id=${comment.id}" class="btn btn-xs btn-warning">编辑</a>
                </#if>
                <#if sec.hasPermission("comment:delete")>
                  <button onclick="deleteBtn('${comment.id}')" class="btn btn-xs btn-danger">删除</button>
                </#if>
              </td>
            </tr>
            <tr>
              <td colspan="5">${comment.content}</td>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
    </div>
    <#include "../layout/paginate.ftl">
    <@paginate currentPage=page.current totalPage=page.pages actionUrl="/admin/comment/list"
    urlParas="&startDate=${startDate!}&endDate=${endDate!}&username=${username!}"/>
  </section>
<script>
  $(function () {
    $("#startDate").datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      todayBtn: true,
      todayHighlight: true,
    });
    $("#endDate").datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      todayBtn: true,
      todayHighlight: true,
    });
  });
  <#if sec.hasPermission("comment:delete")>
    function deleteBtn(id) {
      if (confirm('确定要删除这条评论吗？')) {
        $.get("/admin/comment/delete?id=" + id, function (data) {
          if (data.code === 200) {
            toast("成功", "success");
            setTimeout(function () {
              window.location.reload();
            }, 700);
          } else {
            toast(data.description);
          }
        })
      }
    }
  </#if>
</script>
</@html>
