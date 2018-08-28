<#include "../layout/layout.ftl">
<@html page_title="话题列表" page_tab="topic">
  <section class="content-header">
    <h1>
      话题
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/topic/list">话题</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">话题列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form action="/admin/topic/list" class="form-inline">
          <div class="form-group">
            <input type="text" readonly id="startTime" name="startTime" value="${startTime!}" class="form-control" placeholder="开始时间">
            <input type="text" readonly id="endTime" name="endTime" value="${endTime!}" class="form-control" placeholder="结束时间">
            <input type="text" name="username" value="${username!}" class="form-control" placeholder="用户名">
            <select name="status" id="status" class="form-control">
              <option value="">请选择</option>
              <option value="good" <#if status! == "good">selected</#if>>精华</option>
              <option value="top" <#if status! == "top">selected</#if>>置顶</option>
            </select>
            <button type="submit" class="btn btn-primary btn-sm">搜索</button>
          </div>
        </form>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>标题</th>
            <th>用户</th>
            <th>状态</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.content as topic>
            <tr>
              <td>${topic.id}</td>
              <td><a href="/topic/${topic.id}" target="_blank">${topic.title!}</a></td>
              <td>${topic.username!}</td>
              <td>
                <#if topic.top>
                  置顶
                <#elseif topic.good>
                  精华
                <#else>
                  &nbsp;
                </#if>
              </td>
              <td>${topic.in_time!}</td>
              <td>
                <#if sec.allGranted('topic:top')>
                  <button onclick="actionBtn('${topic.id}', 'top')" class="btn btn-sm btn-warning">置顶</button>
                </#if>
                <#if sec.allGranted('topic:good')>
                  <button onclick="actionBtn('${topic.id}', 'good')" class="btn btn-sm btn-warning">加精</button>
                </#if>
                <#if sec.allGranted('topic:edit')>
                  <a href="/admin/topic/edit?id=${topic.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.allGranted('topic:delete')>
                  <button onclick="actionBtn('${topic.id}', 'delete')" class="btn btn-sm btn-danger">删除</button>
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
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/admin/topic/list"
            urlParas="&startTime=${startTime!}&endTime=${endTime!}&username=${username!}&status=${status!}"/>
        </div>
      </#if>
    </div>
  </section>
<script>
  $(function () {
    $("#startTime").datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd'
    });
    $("#endTime").datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd'
    });
  })
  function actionBtn(id, action) {
    var msg, url;
    if(action === 'top') {
      url = '/admin/topic/top';
      msg = '确定要置顶这条评论吗？';
    } else if(action === 'good') {
      url = '/admin/topic/good';
      msg = '确定要加精这条评论吗？';
    } else if(action === 'delete') {
      url = '/admin/topic/delete';
      msg = '确定要删除这条评论吗？';
    }
    if (confirm(msg)) {
      $.ajax({
        url: url,
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