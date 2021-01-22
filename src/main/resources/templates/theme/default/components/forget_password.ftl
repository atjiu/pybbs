<div class="card d-none" id="email_forget_password_div">
    <div class="card-header">找回密码</div>
    <div class="card-body">
        <form action="" onsubmit="return;">
            <div class="form-group">
                <label for="email">邮箱</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="邮箱"/>
            </div>
            <div class="form-group">
                <label for="captcha">验证码</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="forget_password_captcha" name="captcha"
                           placeholder="验证码"/>
                    <span class="input-group-append">
                <img style="border: 1px solid #ccc;" src="" class="captcha" id="emailCaptcha"/>
              </span>
                </div>
            </div>
            <div class="form-group">
                <button type="button" id="email_forget_password" onclick="email_forget_password()" class="btn btn-dark btn-block">
                    找回密码<i class="fa fa-question-circle ml-1" data-toggle="tooltip" data-placement="right" title=""
                           data-original-title="手机号登录系统会判断手机号是否注册过，如果没有注册过，会创建帐号"></i>
                </button>
            </div>
            <div class="form-group">
                已有社区帐号？<a href="/login" class="text-primary">${i18n.getMessage("login")}</a>
            </div>
        </form>
    </div>
</div>
<script>
    function email_forget_password() {
        var email = $("#email").val();
        var captcha = $("#email_captcha").val();
        if (!email) {
            err("请输入邮箱");
            return;
        }
        if (!captcha) {
            err("请输入验证码");
            return;
        }
        req("post", "/api/forget_password", {
            email: email,
            captcha: captcha,
        }, function (data) {
            if (data.code === 200) {
                suc("邮件发送成功");
                setTimeout(function () {
                    window.location.href = "/";
                }, 700);
            } else {
                err(data.description);
            }
        });
    }
</script>
