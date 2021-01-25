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
                            <a href="javascript:;;" id="login_btn" class="btn btn-dark btn-block">${i18n.getMessage("login")}</a>
                        </div>
                        <div class="form-group">
                            <#--                            <a href="javascript:;" id="forget_password_href" class="text-primary">${i18n.getMessage("forget_password")}</a>-->
                            <#--                            |-->
                            没有社区账号？<a href="/register" class="text-primary">${i18n.getMessage("register")}</a>
                        </div>
                    </form>
                    <@tag_social_list>
                        <#if socialList?? ||!model.isEmpty(site.sms_access_key_id!) >
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
                            <#if !model.isEmpty(site.sms_access_key_id!)>
                                <button class="btn btn-light btn-block" id="mobile_login_btn"><i class="fa fa-mobile"></i>&nbsp;&nbsp;通过手机号登录/注册
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
            document.getElementById("captcha").addEventListener("keypress", function (e) {
                if (e.code.indexOf("Enter") !== -1) {
                    document.getElementById("login_btn").click();
                }
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
                req("post", "/api/login", {
                    username: username,
                    password: password,
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
            });
            $("#mobile_login_btn").click(function () {
                $("#email_forget_password_div").addClass("d-none");
                $("#local_login_div").addClass("d-none");
                $("#mobile_login_div").removeClass("d-none");
            });
            $("#forget_password_href").click(function () {
                $("#email_forget_password_div").removeClass("d-none");
                $("#local_login_div").addClass("d-none");
                $("#mobile_login_div").addClass("d-none");
            })
        })
    </script>
</@html>
