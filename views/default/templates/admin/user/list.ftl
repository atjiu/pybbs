<#include "../layout/layout.ftl">
<@html page_title="用户列表" page_tab="user">
  <section class="content-header">
    <h1>
      用户
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/user/list">用户</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">用户列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>用户名</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>声望</th>
            <th>状态</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.content as user>
          <tr <#if user.block> class="danger"</#if>>
            <td>${user.id}</td>
            <td>${user.username!}</td>
            <td>${user.mobile!}</td>
            <td>${user.email!}</td>
            <td>${user.reputation!0}</td>
            <td>
                <#if user.block>
                  <span class="text-danger">禁用</span>
                <#else>
                  <span class="text-success">正常</span>
                </#if>
            </td>
            <td>${user.inTime?datetime}</td>
            <td>
                <#if sec.allGranted('user:block')>
                  <button onclick="blockBtn('${user.id}')" class="btn btn-sm btn-warning">禁用</button>
                </#if>
                <#if sec.allGranted('user:edit')>
                  <a href="/admin/user/edit?id=${user.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.allGranted('user:delete')>
                  <button onclick="deleteBtn('${user.id}')" class="btn btn-sm btn-danger">删除</button>
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
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/admin/user/list" urlParas=""/>
        </div>
      </#if>
    </div>
  </section>
<script>
  function deleteBtn(id) {
    if (confirm('确定要删除这个用户吗？\r\n 注：此过程不可逆！！')) {
      if (confirm('真的确定了吗？此过程不可逆！！！')) {
        $.ajax({
          url: '/admin/user/delete',
          type: 'get',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: id
          },
          success: function (data) {
            if (data.code === 200) {
              window.location.reload();
            } else {
              toast(data.description);
            }
          }
        })
      }
    }
  }

  function blockBtn(id) {
    $.ajax({
      url: '/admin/user/block',
      type: 'get',
      async: false,
      cache: false,
      dataType: 'json',
      data: {
        id: id
      },
      success: function (data) {
        if (data.code === 200) {
          window.location.reload();
        } else {
          toast(data.description);
        }
      }
    })
  }
</script>
</@html>