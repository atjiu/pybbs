<#include "../../layout/" + layoutName/>
<@html page_title="修改个人密码" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../layout/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="changePassword"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        修改密码
      </div>
      <div class="panel-body">
        <form id="form">
          <div class="form-group">
            <label for="oldPassword">旧密码</label>
            <input type="password" class="form-control" id="oldPassword" name="oldPassword"/>
          </div>
          <div class="form-group">
            <label for="newPassword">新密码</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword"/>
          </div>
          <#if user.block>
            <button type="button" class="btn btn-default" disabled="disabled" data-toggle="tooltip"
                    data-placement="bottom" title="你的帐户已经被禁用了">修改密码
            </button>
          <#else>
            <button type="submit" class="btn btn-default">修改密码</button>
          </#if>
          <span class="text-danger">${changePasswordErrorMsg!}</span>
        </form>
      </div>
    </div>
  </div>
</div>
<script>
  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
    $("#form").submit(function() {
      var oldPassword = $("#oldPassword").val();
      var newPassword = $("#newPassword").val();
      if(!oldPassword) {
        toast('请输入旧密码');
        return false;
      }
      if(!newPassword) {
        toast('请输入新密码');
        return false;
      }
      $.ajax({
        url: '/api/user/setting/changePassword',
        type: 'post',
        async: true,
        cache: false,
        dataType: 'json',
        data: {
          oldPassword: oldPassword,
          newPassword: newPassword
        },
        success: function(data) {
          if(data.code === 200) {
            toast('修改成功，1s后登出');
            setTimeout(function() {
              window.location.href = '/logout';
            }, 2000);
          } else {
            toast(data.description);
          }
        }
      });
      return false;
    })
  })
</script>
</@html>