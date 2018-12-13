<#include "../layout/layout.ftl">
<@html page_title="用户编辑" page_tab="auth_admin_user">
  <section class="content-header">
    <h1>
      用户
      <small>编辑</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/user/list">用户</a></li>
      <li class="active">编辑</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">用户编辑</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <div class="row">
          <div class="col-sm-6">
            <form id="form" action="/admin/admin_user/edit" method="post">
              <input type="hidden" name="id" value="${adminUser.id}">
              <div class="form-group">
                <label>用户名</label>
                <input type="text" id="username" name="username" value="${adminUser.username!}" class="form-control" placeholder="用户名">
              </div>
              <div class="form-group">
                <label>密码</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="密码">
              </div>
              <div class="form-group">
                <label>角色</label>
                <p>
                  <#list roles as role>
                    <input type="radio" name="roleId" value="${role.id}" id="role_${role.id}" <#if role.id == adminUser.roleId>checked</#if>>&nbsp;
                    <label for="role_${role.id}">${role.name!}</label>
                  </#list>
                </p>
              </div>
              <button type="submit" class="btn btn-xs btn-primary">保存</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
<script>
  $(function() {
    $("#form").submit(function() {
      var username = $("#username").val();
      var oldPassword = $("#oldPassword").val();
      var password = $("#password").val();
      var roleId = $("input[name='roleId']:checked").val();
      if(!username) {
        toast('用户名不能为空');
        return false;
      }
      $.ajax({
        url: '/admin/admin_user/edit',
        async: true,
        cache: false,
        type: 'post',
        dataType: 'json',
        data: {
          id: '${adminUser.id}',
          username: username,
          password: password,
          roleId: roleId
        },
        success: function(data) {
          if(data.code === 200) {
            toast('修改成功');
            setTimeout(function() {
              window.location.href = '/admin/admin_user/list';
            }, 1000);
          } else {
            toast(data.description);
          }
        }
      })
      return false;
    })
  })
</script>
</@html>
