<div class="card d-none" id="mobile_login_div">
    <div class="card-header">手机号登录/注册</div>
    <div class="card-body">
        <form action="" onsubmit="return;">
            <div class="form-group">
                <label for="mobile">手机号</label>
                <div class="input-group">
                    <input type="tel" id="mobile" name="mobile" class="form-control" placeholder="手机号"/>
                    <span class="input-group-append">
            <button type="button" class="btn btn-default" onclick="send_sms_code()"
                    id="send_sms_code_btn">获取验证码</button>
          </span>
                </div>
            </div>
            <div class="form-group">
                <label for="code">手机验证码</label>
                <input type="text" id="code" name="code" class="form-control" placeholder="手机验证码"/>
            </div>
            <div class="form-group">
                <label for="captcha">验证码</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="mobile_captcha" name="captcha" placeholder="验证码"/>
                    <span class="input-group-append">
            <img style="border: 1px solid #ccc;" src="" class="captcha" id="mobileCaptcha"/>
          </span>
                </div>
            </div>
            <div class="form-group">
                <button type="button" id="mobile_login" onclick="to_mobile_login()" class="btn btn-dark btn-block">
                    登录/注册<i class="fa fa-question-circle ml-1" data-toggle="tooltip" data-placement="right" title="" data-original-title="手机号登录系统会判断手机号是否注册过，如果没有注册过，会创建帐号"></i>
                </button>

            </div>
        </form>
        <@tag_social_list>
            <#if socialList??>
                <hr>
            </#if>
            <div class="social">
                <#if socialList??>
                    <#list socialList as social>
                        <a href="/oauth/redirect/${social}" class="btn btn-light btn-block">
                            <img src="https://cdn.jsdelivr.net/gh/justauth/justauth-oauth-logo@1.1/${social}.png" alt="${social}授权登录" width="15" height="15">
                            通过 ${social?cap_first} 登录/注册
                        </a>
                    </#list>
                </#if>
                <button class="btn btn-light btn-block" id="local_login_btn" onclick="local_login_btn()"><i
                            class="fa fa-sign-in"></i>&nbsp;&nbsp;帐号登录/注册
                </button>
            </div>
        </@tag_social_list>
    </div>
</div>
<script>
    $('[data-toggle="tooltip"]').tooltip();

    var count = 60;

    function send_sms_code() {
        var mobile = $("#mobile").val();
        var captcha = $("#mobile_captcha").val();
        if (!mobile) {
            err("请输入手机号");
            return;
        }
        if (!captcha) {
            err("请输入验证码");
            return;
        }
        if (count < 60) return;
        $("#send_sms_code_btn").attr('disabled', true);
        req("get", "/api/sms_code", {
            mobile: mobile,
            captcha: captcha,
        }, function (data) {
            if (data.code === 200) {
                var interval = setInterval(function () {
                    $("#send_sms_code_btn").text(count-- + 's');
                    if (count <= 0) {
                        count = 60;
                        $("#send_sms_code_btn").attr('disabled', false);
                        $("#send_sms_code_btn").text('获取验证码');
                        clearInterval(interval);
                    }
                }, 1000);
                suc("发送成功");
            } else {
                $("#send_sms_code_btn").attr('disabled', false);
                err(data.description);
            }
            $(".captcha").click();
        });
    }

    function to_mobile_login() {
        var mobile = $("#mobile").val();
        var code = $("#code").val();
        var captcha = $("#mobile_captcha").val();
        if (!mobile) {
            err("请输入手机号");
            return;
        }
        if (!code) {
            err("请输入手机验证码");
            return;
        }
        if (!captcha) {
            err("请输入验证码");
            return;
        }
        req("post", "/api/mobile_login", {
            mobile: mobile,
            code: code,
            captcha: captcha,
        }, function (data) {
            if (data.code === 200) {
                suc("登录成功");
                setTimeout(function () {
                    window.location.href = "/";
                }, 700);
            } else {
                err(data.description);
            }
        });
    }

    function local_login_btn() {
        $("#email_forget_password_div").addClass("d-none");
        $("#mobile_login_div").addClass("d-none");
        $("#local_login_div").removeClass("d-none");
        $("#local_register_div").removeClass("d-none");
    }
</script>
