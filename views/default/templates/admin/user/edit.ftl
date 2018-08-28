<#include "../layout/layout.ftl">
<@html page_title="用户编辑" page_tab="user">
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
        <form id="form">
          <div class="form-group">
            <label>用户名</label>
            <input type="text" id="username" value="${user.username!}" class="form-control"/>
          </div>
          <div class="form-group">
            <label>密码</label>
            <input type="password" id="password" value="" class="form-control" placeholder="密码(为空不修改)"/>
          </div>
          <div class="form-group">
            <label>声望</label>
            <input type="number" pattern="\d" id="reputation" value="${user.reputation!0}" class="form-control" placeholder="声望"/>
          </div>
          <button type="submit" id="btn" class="btn btn-sm btn-primary">提交</button>
        </form>
      </div>
    </div>
  </section>
<script>
  $(function() {
    $("#form").submit(function() {
      var username = $("#username").val();
      var reputation = $("#reputation").val();
      if(!username) {
        toast("请输入用户名");
      } else if(!reputation) {
        toast("请输入声望值");
      } else {
        $.ajax({
          url: '/admin/user/edit',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: '${user.id}',
            username: username,
            password: $("#password").val(),
            reputation: reputation,
          },
          success: function(data){
            if(data.code === 200) {
              window.location.href = "/admin/user/list";
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