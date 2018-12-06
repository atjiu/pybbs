<#include "../layout/layout.ftl"/>
<@html page_title="设置" page_tab="settings">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">设置</div>
      <div class="panel-body">
        <br>
        <form class="form-horizontal" onsubmit="return;">
          <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="username" name="username" disabled value="${user.username}"
                     placeholder="用户名">
            </div>
          </div>
          <div class="form-group">
            <label for="githubName" class="col-sm-2 control-label">Github用户名</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="githubName" name="githubName" value="${user.githubName!}"
                     placeholder="Github用户名">
            </div>
          </div>
          <div class="form-group">
            <label for="telegramName" class="col-sm-2 control-label">Telegram用户名</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="telegramName" name="telegramName" value="${user.telegramName!}"
                     placeholder="Telegram用户名">
            </div>
          </div>
          <div class="form-group">
            <label for="website" class="col-sm-2 control-label">个人主页</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="website" name="website" value="${user.website!}"
                     placeholder="个人主页">
            </div>
          </div>
          <div class="form-group">
            <label for="bio" class="col-sm-2 control-label">个人简介</label>
            <div class="col-sm-10">
              <textarea name="bio" id="bio" rows="3" class="form-control"
                        placeholder="个人简介">${user.bio!?html}</textarea>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <div class="checkbox">
                <label>
                  <input type="checkbox" id="emailNotification" <#if user.emailNotification>checked</#if>>
                  有新消息发送邮件
                </label>
              </div>
            <#--<div class="checkbox">
              <label>
                <input type="checkbox" id="telegramNotification"> 有新消息发送Telegram通知
              </label>
            </div>-->
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" id="settings_btn" class="btn btn-default">提交</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">修改密码</div>
      <div class="panel-body">
        <form onsubmit="return;" class="form-horizontal">
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-10">
              <div class="input-group">
                <input type="email" name="email" id="email" class="form-control" value="${user.email!}"
                       placeholder="邮箱"/>
                <span class="input-group-btn">
                  <button type="button" id="sendEmailCode" class="btn btn-default" autocomplete="off"
                          data-loading-text="发送中...">发送验证码</button>
                </span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label for="code" class="col-sm-2 control-label">验证码</label>
            <div class="col-sm-10">
              <input type="text" name="code" id="code" class="form-control" placeholder="验证码"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" id="settings_email_btn" class="btn btn-default">更改邮箱</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">修改头像</div>
      <div class="panel-body">
        <form onsubmit="return;" class="form-horizontal">
          <div class="form-group">
            <label for="" class="col-sm-2 control-label" style="vertical-align: middle">当前头像</label>
            <div class="col-sm-10">
              <img src="${user.avatar}" class="avatar avatar-lg" alt="avatar"/>&nbsp;&nbsp;
              <img src="${user.avatar}" class="avatar" style="vertical-align: bottom" alt="avatar"/>&nbsp;&nbsp;
              <img src="${user.avatar}" class="avatar avatar-sm" style="vertical-align: bottom" alt="avatar"/>
            </div>
            <div class="col-sm-offset-2 col-sm-10" style="margin-top: 10px;">
              <a href="javascript:;" id="selectAvatar">上传新头像</a>
              <input type="file" class="hidden" name="file" id="file"/>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">修改密码</div>
      <div class="panel-body">
        <form onsubmit="return;" class="form-horizontal">
          <div class="form-group">
            <label for="oldPassword" class="col-sm-2 control-label">旧密码</label>
            <div class="col-sm-10">
              <input type="password" name="oldPassword" id="oldPassword" class="form-control" placeholder="旧密码"/>
            </div>
          </div>
          <div class="form-group">
            <label for="newPassword" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-10">
              <input type="password" name="newPassword" id="newPassword" class="form-control" placeholder="新密码"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" id="settings_pwd_btn" class="btn btn-default">更改密码</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <#include "../components/user_info.ftl"/>
  </div>
</div>
<script>
  $(function () {
    $("#settings_btn").click(function () {
      var githubName = $("#githubName").val();
      var telegramName = $("#telegramName").val();
      var website = $("#website").val();
      var bio = $("#bio").val();
      var emailNotification = $("#emailNotification").is(":checked");
      $.post("/api/settings/update", {
        githubName: githubName,
        telegramName: telegramName,
        website: website,
        bio: bio,
        emailNotification: emailNotification,
      }, function (data) {
        if (data.code === 200) {
          toast("更新个人资料成功", "success");
          setTimeout(function () {
            window.location.reload();
          }, 700);
        } else {
          toast(data.description);
        }
      })
    });

    // 上传头像
    $("#selectAvatar").click(function () {
      $("#file").click();
    });
    $("#file").change(function () {
      var fd = new FormData();
      fd.append("file", document.getElementById("file").files[0]);
      $.post({
        url: "/api/uploadAvatar",
        data: fd,
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (data) {
          if (data.code === 200) {
            toast("修改头像成功", "success");
            $.each($(".avatar "), function (i, v) {
              $(v).attr("src", data.detail);
            })
          } else {
            toast(data.description);
          }
        }
      })
    });

    // 更改邮箱
    $("#sendEmailCode").on("click", function () {
      var loadingBtn = $(this).button("loading");
      var email = $("#email").val();
      $.get("/api/settings/sendEmailCode?email=" + email, function (data) {
        if (data.code === 200) {
          toast("发送成功", "success");
        } else {
          toast(data.description);
        }
        loadingBtn.button("reset");
      })
    })
    $("#settings_email_btn").click(function () {
      var email = $("#email").val();
      var code = $("#code").val();
      $.post("/api/settings/updateEmail", {
        email: email,
        code: code,
      }, function (data) {
        if (data.code === 200) {
          toast("更改成功", "success");
          setTimeout(function () {
            window.location.reload();
          }, 700);
        } else {
          toast(data.description);
        }
      })
    })

    // 更改密码
    $("#settings_pwd_btn").click(function () {
      var oldPassword = $("#oldPassword").val();
      var newPassword = $("#newPassword").val();
      if (!oldPassword) {
        toast("请输入旧密码");
        return;
      }
      if (!newPassword) {
        toast("请输入新密码");
        return;
      }
      $.post("/api/settings/updatePassword", {
        oldPassword: oldPassword,
        newPassword: newPassword,
      }, function (data) {
        if (data.code === 200) {
          toast("修改密码成功", "success");
        } else {
          toast(data.description);
        }
      })
    });
  })
</script>
</@html>
