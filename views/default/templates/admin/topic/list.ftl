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
          <#list page.content as map>
            <tr>
              <td>${map.topic.id}</td>
              <td><a href="/topic/${map.topic.id}" target="_blank">${map.topic.title!}</a></td>
              <td>${map.user.username!}</td>
              <td>
                <#if map.topic.top>
                  置顶
                <#elseif map.topic.good>
                  精华
                <#else>
                  &nbsp;
                </#if>
              </td>
              <td>${map.topic.inTime!}</td>
              <td>
                <#if sec.hasPermission('topic:top')>
                  <button onclick="actionBtn('${map.topic.id}', 'top')" class="btn btn-sm btn-warning">置顶</button>
                </#if>
                <#if sec.hasPermission('topic:good')>
                  <button onclick="actionBtn('${map.topic.id}', 'good')" class="btn btn-sm btn-warning">加精</button>
                </#if>
                <#if sec.hasPermission('topic:edit')>
                  <a href="/admin/topic/edit?id=${map.topic.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.hasPermission('topic:delete')>
                  <button onclick="actionBtn('${map.topic.id}', 'delete')" class="btn btn-sm btn-danger">删除</button>
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
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/admin/topic/list" urlParas=""/>
        </div>
      </#if>
    </div>
  </section>
<script>
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