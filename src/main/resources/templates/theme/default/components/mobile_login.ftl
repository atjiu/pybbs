<div class="panel panel-info back" id="mobile_login_div">
  <div class="panel-heading">手机号登录</div>
  <div class="panel-body">
    <form action="" onsubmit="return;">
      <div class="form-group">
        <label for="mobile">手机号</label>
        <input type="tel" id="mobile" name="mobile" class="form-control" placeholder="手机号"/>
      </div>
      <div class="form-group">
        <label for="code">密码</label>
        <input type="text" id="code" name="code" class="form-control" placeholder="手机验证码"/>
      </div>
      <div class="form-group">
        <label for="captcha">验证码</label>
        <div class="input-group">
          <input type="text" class="form-control" id="mobile_captcha" name="captcha" placeholder="验证码"/>
          <span class="input-group-btn">
                <img style="border: 1px solid #ccc;" src="/common/captcha" id="mobileCaptcha" onclick="changeMobileCaptcha()"/>
              </span>
        </div>
      </div>
      <div class="form-group">
        <button type="button" id="mobile_login" onclick="mobile_login()" class="btn btn-info">登录/注册</button>
      </div>
    </form>
    <#if (!model.isEmpty(site.oauth_github_client_id!) && !model.isEmpty(site.oauth_github_client_secret!))
    || (!model.isEmpty(site.sms_access_key_id!) && !model.isEmpty(site.sms_secret!))>
      <hr>
    </#if>
    <#if !model.isEmpty(site.oauth_github_client_id!) && !model.isEmpty(site.oauth_github_client_secret!)>
      <a href="/oauth/github" class="btn btn-success btn-block"><i class="fa fa-github"></i>&nbsp;&nbsp;通过Github登录/注册</a>
    </#if>
    <#--<#if !model.isEmpty(site.oauth_github_client_id!) && !model.isEmpty(site.oauth_github_client_secret!)>-->
    <a class="btn btn-primary btn-block" id="local_login"><i class="fa fa-sign-in"></i>&nbsp;&nbsp;帐号登录</a>
    <#--</#if>-->
  </div>
</div>
<script>
  function changeMobileCaptcha() {
    var date = new Date();
    $("#mobileCaptcha").attr("src", "/common/captcha?ver=" + date.getTime());
  }
  function mobile_login() {
    var mobile = $("#mobile").val();
    var code = $("#code").val();
    var captcha = $("#mobile_captcha").val();
    if (!mobile) {
      toast("请输入手机号");
      return;
    }
    if (!code) {
      toast("请输入手机验证码");
      return;
    }
    if (!captcha) {
      toast("请输入验证码");
      return;
    }
    $.ajax({
      url: '/api/mobile_login',
      type: 'post',
      cache: false,
      async: false,
      contentType: 'application/json',
      data: JSON.stringify({
        mobile: mobile,
        code: code,
        captcha: captcha,
      }),
      success: function(data) {
        if (data.code === 200) {
          toast("登录成功", "success");
          setTimeout(function () {
            window.location.href = "/";
          }, 700);
        } else {
          toast(data.description);
        }
      }
    })
  }
</script>