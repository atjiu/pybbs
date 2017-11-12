<#include "../../common/layout.ftl"/>
<@html page_title="修改个人资料" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="profile"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 个人设置
      </div>
      <div class="panel-body">
        <form action="/user/profile" method="post" id="userProfileForm" enctype="multipart/form-data">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username">昵称</label>
            <input type="text" disabled class="form-control" id="username" value="${user.username}"/>
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <input type="text" class="form-control" id="email" name="email" value="${user.email!?html}"/>
          </div>
          <div class="form-group">
            <label for="url">个人主页</label>
            <input type="text" class="form-control" id="url" name="url" value="${user.url!?html}"/>
          </div>
          <div class="form-group">
            <label for="bio">个性签名</label>
            <textarea class="form-control" name="bio" id="bio">${user.bio!?html}</textarea>
          </div>
          <#if user.block == true>
            <button type="button" disabled="disabled" class="btn btn-default">保存设置</button>
          <#else>
            <button type="button" id="userProfileUpdateBtn" onclick="updateUserProfile()"
                    class="btn btn-default">保存设置
            </button>
          </#if>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
</div>
<script>
  function updateUserProfile() {
    var errors = 0;
    var em = $("#error_message");
    var bio = $("#bio").val();
    if (bio.length > 1000) {
      errors++;
      em.html("个性签名不能超过1000个字");
    }
    if (errors === 0) {
      var form = $("#userProfileForm");
      form.submit();
    }
  }
</script>
</@html>