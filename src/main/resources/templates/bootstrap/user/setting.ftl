<#include "../common/layout.ftl"/>
<@html page_title="修改个人资料" page_tab="setting">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 个人设置
      </div>
      <div class="panel-body">
        <form action="/user/setting" method="post" id="userProfileForm" enctype="multipart/form-data">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username">昵称</label>
            <input type="text" disabled class="form-control" id="username" value="${user.username}"/>
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <input type="text" class="form-control" id="email" name="email" value="${user.email!}"/>
          </div>
          <div class="form-group">
            <label for="url">个人主页</label>
            <input type="text" class="form-control" id="url" name="url" value="${user.url!}"/>
          </div>
          <div class="form-group">
            <label for="signature">个性签名</label>
            <textarea class="form-control" name="signature" id="signature">${user.signature!}</textarea>
          </div>
          <div class="form-group">
            <label for="avatar">头像</label>
            <input type="file" class="form-control" name="avatar" id="avatar"/>
            <br>
            <img src="${user.avatar!}" width="150">
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
    <div class="panel panel-default">
      <div class="panel-heading">
        修改密码
      </div>
      <div class="panel-body">
        <form action="/user/changePassword" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="oldPassword">旧密码</label>
            <input type="password" class="form-control" id="oldPassword" name="oldPassword"/>
          </div>
          <div class="form-group">
            <label for="newPassword">新密码</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword"/>
          </div>
          <#if user.block == true>
            <button type="button" class="btn btn-default" disabled="disabled">修改密码</button>
          <#else>
            <button type="submit" class="btn btn-default">修改密码</button>
          </#if>
          <span class="text-danger">${changePasswordErrorMsg!}</span>
        </form>
      </div>
    </div>

    <div class="panel panel-default">
      <div class="panel-heading">用户令牌</div>
      <div class="panel-body">
        <p>Token: ${user.token!} <a href="/user/refreshToken" class="btn btn-xs btn-danger">刷新Token</a></p>
        <p id="qrcode"></p>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">

  </div>
</div>
<script src="//cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script type="text/javascript">
  $('#qrcode').qrcode("${user.token!}");
</script>
</@html>