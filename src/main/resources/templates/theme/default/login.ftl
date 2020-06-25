<#include "layout/layout.ftl"/>
<@html page_title="登录" page_tab="login">
    <div class="row">
        <div class="col-md-3 d-none d-md-block"></div>
        <div class="col-md-6">
            <div class="card" id="local_login_div">
                <div class="card-header">登录</div>
                <div class="card-body">
                    <form action="" onsubmit="return;">
                        <div class="form-group">
                            <label for="username">用户名</label>
                            <input type="text" id="username" name="username" class="form-control" placeholder="用户名"/>
                        </div>
                        <div class="form-group">
                            <label for="password">密码</label>
                            <input type="password" id="password" name="password" class="form-control" placeholder="密码"/>
                        </div>
                        <div class="form-group">
                            <label for="captcha">验证码</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="captcha" name="captcha" placeholder="验证码"/>
                                <span class="input-group-append">
                  <img style="border: 1px solid #ccc;" src="" class="captcha" id="changeCaptcha"/>
                </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="button" id="login_btn" class="btn btn-info">登录</button>
                            <#--<a href="javascript:;" id="forget_password_href" class="pull-right">忘记密码?</a>-->
                        </div>
                    </form>
                    <@tag_social_list>
                        <#if socialList?? ||!model.isEmpty(site.sms_access_key_id!) >
                            <hr>
                        </#if>
                        <div class="social">
                            社会化登录：
                            <#if socialList??>
                                <#list socialList as social>
                                    <a href="/oauth/redirect/${social}" style="text-decoration: none;">
                                        <img src="https://cdn.jsdelivr.net/gh/justauth/justauth-oauth-logo@1.1/${social}.png" alt="${social}授权登录" title="一键${social}授权登录" width="22" height="22">
                                    </a>
                                </#list>
                            </#if>
                            <#if !model.isEmpty(site.sms_access_key_id!)>
                                <button class="btn btn-primary btn-block" id="mobile_login_btn"><i class="fa fa-mobile"></i>&nbsp;&nbsp;通过手机号登录/注册
                                </button>
                            </#if>
                        </div>
                    </@tag_social_list>

                </div>
            </div>
            <#include "./components/mobile_login.ftl"/>
            <#include "./components/forget_password.ftl"/>
        </div>
        <div class="col-md-3 d-none d-md-block"></div>
    </div>
    <script>
        $(function () {
            $(".captcha").attr('src', "/common/captcha?ver=" + new Date().getTime());
            $(".captcha").click(function () {
                $(".captcha").each(function () {
                    var date = new Date();
                    $(this).attr("src", "/common/captcha?ver=" + date.getTime());
                });
            });
            $("#login_btn").click(function () {
                var username = $("#username").val();
                var password = $("#password").val();
                var captcha = $("#captcha").val();
                if (!username) {
                    err('请输入用户名');
                    return;
                }
                if (!password) {
                    err('请输入密码');
                    return;
                }
                if (!captcha) {
                    err('请输入验证码');
                    return;
                }
                $.ajax({
                    url: '/api/login',
                    type: 'post',
                    cache: false,
                    async: false,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        username: username,
                        password: password,
                        captcha: captcha,
                    }),
                    success: function (data) {
                        if (data.code === 200) {
                            suc("登录成功");
                            setTimeout(function () {
                                window.location.href = "/";
                            }, 700);
                        } else {
                            err(data.description);
                        }
                    }
                })
            });
            $("#mobile_login_btn").click(function () {
                $("#email_forget_password_div").addClass("hidden");
                $("#local_login_div").addClass("hidden");
                $("#mobile_login_div").removeClass("hidden");
            });
            $("#forget_password_href").click(function () {
                $("#email_forget_password_div").removeClass("hidden");
                $("#local_login_div").addClass("hidden");
                $("#mobile_login_div").addClass("hidden");
            })
        })
    </script>
</@html>
