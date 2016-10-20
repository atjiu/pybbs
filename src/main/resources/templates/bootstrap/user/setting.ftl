<#include "../common/layout.ftl"/>
<@html page_title="修改个人资料" page_tab="setting">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 个人设置
      </div>
      <div class="panel-body">
        <form action="/user/setting" method="post" id="userProfileForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="nickname">昵称</label>
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
          <button type="button" id="userProfileUpdateBtn" onclick="updateUserProfile()"
                  class="btn btn-default">保存设置
          </button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">

  </div>
</div>
</@html>