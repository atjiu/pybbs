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
        <span class="pull-right">
          <#if sec.hasPermission("topic:index_all")>
            <button onclick="index_all_topic()" class="btn btn-primary btn-xs">索引全部话题</button>&nbsp;
          </#if>
          <#if sec.hasPermission("topic:delete_all_index")>
            <button onclick="delete_all_index()" class="btn btn-danger btn-xs">删除所有话题索引</button>
          </#if>
        </span>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form action="/admin/topic/list" class="form-inline">
          <div class="form-group" style="margin-bottom: 10px;">
            <input type="text" readonly id="startDate" name="startDate" value="${startDate!}"
                   class="form-control" placeholder="开始时间">
            <input type="text" readonly id="endDate" name="endDate" value="${endDate!}"
                   class="form-control" placeholder="结束时间">
            <input type="text" name="username" value="${username!}" class="form-control" placeholder="用户名">
            <button type="submit" class="btn btn-primary btn-sm">搜索</button>
          </div>
        </form>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>标题</th>
            <th>用户</th>
            <th>评论数</th>
            <th>状态</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.records as topic>
            <tr>
              <td>${topic.id}</td>
              <td><a href="/topic/${topic.id}" target="_blank">${topic.title}</a></td>
              <td><a href="/user/${topic.username}" target="_blank">${topic.username}</a></td>
              <td>${topic.commentCount}</td>
              <td>
                <#if topic.top>
                  置顶
                <#elseif topic.good>
                  精华
                <#else>
                  &nbsp;
                </#if>
              </td>
              <td>${topic.inTime!}</td>
              <td>
                <#if sec.hasPermission("topic:index")>
                  <button onclick="index_topic('${topic.id}')" class="btn btn-xs btn-primary">索引</button>
                </#if>
                <#if sec.hasPermission("topic:delete_index")>
                  <button onclick="delete_index('${topic.id}')" class="btn btn-xs btn-danger">删除索引</button>
                </#if>
                <#if sec.hasPermission("topic:top")>
                  <button onclick="actionBtn('${topic.id}', 'top', this)" class="btn btn-xs btn-warning">
                    <#if topic.top>
                      取消置顶
                    <#else>
                      置顶
                    </#if>
                  </button>
                </#if>
                <#if sec.hasPermission("topic:good")>
                  <button onclick="actionBtn('${topic.id}', 'good', this)" class="btn btn-xs btn-warning">
                    <#if topic.good>
                      取消加精
                    <#else>
                      加精
                    </#if>
                  </button>
                </#if>
                <#if sec.hasPermission("topic:edit")>
                  <a href="/admin/topic/edit?id=${topic.id}" class="btn btn-xs btn-warning">编辑</a>
                </#if>
                <#if sec.hasPermission("topic:delete")>
                  <button onclick="actionBtn('${topic.id}', 'delete', this)" class="btn btn-xs btn-danger">删除</button>
                </#if>
              </td>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
    </div>
    <#include "../layout/paginate.ftl">
    <@paginate currentPage=page.current totalPage=page.pages actionUrl="/admin/topic/list"
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
  <#if sec.hasPermissionOr("topic:top", "topic:good", "topic:delete")>
    function actionBtn(id, action, self) {
      var msg, url;
      var tip = $(self).text().replace(/[\r\n]/g, '').trim();
      if(action === 'top') {
        url = '/admin/topic/top?id=' + id;
        msg = '确定'+tip+'这条评论吗？';
      } else if(action === 'good') {
        url = '/admin/topic/good?id=' + id;
        msg = '确定'+tip+'这条评论吗？';
      } else if(action === 'delete') {
        url = '/admin/topic/delete?id=' + id;
        msg = '确定要删除这条评论吗？';
      }
      if (confirm(msg)) {
        $.get(url, function (data) {
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
  <#if sec.hasPermission("topic:index")>
    function index_topic(id) {
      if (confirm("确定要单独索引这个话题吗？")) {
        $.get("/admin/topic/index?id=" + id, function(data) {
          if (data.code === 200) {
            toast("成功", "success");
          } else {
            toast(data.description);
          }
        });
      }
    }
  </#if>
  <#if sec.hasPermission("topic:index_all")>
    function index_all_topic() {
      if (confirm("如果话题数量很多的话，这个操作会非常耗时，点击确定继续")) {
        $.get("/admin/topic/index_all", function(data) {
          if (data.code === 200) {
            toast("成功", "success");
          } else {
            toast(data.description);
          }
        })
      }
    }
  </#if>
  <#if sec.hasPermission("topic:delete_all_index")>
    function delete_all_index() {
      if (confirm("删除了所有话题索引，用户就搜不到数据了，确定吗？")) {
        $.get("/admin/topic/delete_all_index", function(data) {
          if (data.code === 200) {
            toast("成功", "success");
          } else {
            toast(data.description);
          }
        })
      }
    }
  </#if>
  <#if sec.hasPermission("topic:delete_index")>
    function delete_index(id) {
      if (confirm("确定要删除这个话题的索引吗？")) {
        $.get("/admin/topic/delete_index?id=" + id, function(data) {
          if (data.code === 200) {
            toast("成功", "success");
          } else {
            toast(data.description);
          }
        })
      }
    }
  </#if>
</script>
</@html>
