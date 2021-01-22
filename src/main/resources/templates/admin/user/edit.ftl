<#include "../layout/layout.ftl">
<@html page_title="用户编辑" page_tab="user">
    <section class="content-header">
        <h1>
            用户
            <small>编辑</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/admin/user/list">用户</a></li>
            <li class="active">编辑</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">用户编辑</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form id="form" onsubmit="return;">
                    <input type="hidden" name="id" value="${user.id}">
                    <div class="form-group">
                        <label>用户名</label>
                        <input type="text" id="username" name="username" value="${user.username!}"
                               class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" id="password" name="password" value="" class="form-control"
                               placeholder="密码(为空不修改)"/>
                    </div>
                    <div class="form-group">
                        <label>积分</label>
                        <input type="number" pattern="\d" id="score" name="score" value="${user.score!0}"
                               class="form-control"
                               placeholder="积分"/>
                    </div>
                    <div class="form-group">
                        <label>邮箱</label>
                        <input type="email" id="email" name="email" value="${user.email!}" class="form-control"
                               placeholder="邮箱"/>
                    </div>
                    <div class="form-group">
                        <label>githubName</label>
                        <input type="text" id="githubName" name="githubName" value="${user.githubName!}"
                               class="form-control"
                               placeholder="githubName"/>
                    </div>
                    <div class="form-group">
                        <label>telegramName</label>
                        <input type="text" id="telegramName" name="telegramName" value="${user.telegramName!}"
                               class="form-control"
                               placeholder="telegramName"/>
                    </div>
                    <div class="form-group">
                        <label>个人主页</label>
                        <input type="text" id="website" name="website" value="${user.website!}" class="form-control"
                               placeholder="个人主页"/>
                    </div>
                    <#if sec.hasPermission("user:refresh_token")>
                        <div class="form-group">
                            <label>Token</label>
                            <div class="input-group">
                                <input type="text" id="token" name="token" value="${user.token!}" class="form-control"
                                       placeholder="Token"/>
                                <span class="input-group-btn">
                  <button type="button" onclick="refreshToken(this)" class="btn btn-info" autocomplete="off"
                          data-loading-text="刷新中...">刷新Token</button>
                  <script>
                    function refreshToken(self) {
                        $(self).button("loading");
                        $.get("/admin/user/refreshToken?id=${user.id}", function (data) {
                            console.log(data)
                            if (data.code === 200) {
                                toast("成功", "success");
                                $("#token").val(data.detail);
                            } else {
                                toast(data.description);
                            }
                            $(self).button("reset");
                        });
                    }
                  </script>
                </span>
                            </div>
                        </div>
                    </#if>
                    <div class="form-group">
                        <label>个人简介</label>
                        <textarea name="bio" id="bio" rows="3" class="form-control"
                                  placeholder="个人简介">${user.bio!?html}</textarea>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" id="emailNotification" name="emailNotification" value="1"
                               <#if user.emailNotification>checked</#if>/>
                        <label for="emailNotification">有新消息接收邮件通知</label>
                    </div>
                    <button type="button" id="btn" class="btn btn-primary">提交</button>
                </form>
            </div>
        </div>
    </section>
    <script>
        $(function () {
            // 保存用户信息
            $("#btn").click(function () {
                $.post("/admin/user/edit", $("#form").serialize(), function (data) {
                    if (data.code === 200) {
                        toast("编辑成功", "success");
                        setTimeout(function () {
                            window.location.href = "/admin/user/list";
                        }, 700);
                    } else {
                        toast(data.description);
                    }
                })
            });
        })
    </script>
</@html>
