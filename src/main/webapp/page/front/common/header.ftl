<#macro header page_tab="">
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color:#fff;font-family: &#39;comic sans ms&#39;;" href="/">${siteTitle!}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <#if page_tab == 'topic'> class="active" </#if>><a href="${baseUrl!}/">社区</a></li>
                <li <#if page_tab == 'label'> class="active" </#if>><a href="${baseUrl!}/label">标签</a></li>
                <#--<li <#if page_tab == 'api'> class="active" </#if>><a href="${baseUrl!}/api">Api</a></li>-->
            </ul>
            <span class="hidden-xs hidden-sm">
                <form class="navbar-form navbar-left" id="search_form" role="search" method="get" action="${baseUrl!}/">
                    <div class="form-group has-feedback">
                        <input type="text" name="q" class="form-control" style="width: 240px;" value="${q!}"
                               placeholder="回车搜索" onkeypress="enterSearch(event)">
                        <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true" style="line-height: 28px;"></span>
                    </div>
                </form>
                <script type="text/javascript">
                    function enterSearch(e) {
                        var e = e || window.event;
                        if(e.keyCode == 13) {
                            if($.trim($("input[name='q']").val()) != "") {
                                $("#search_form").submit();
                            }
                        }
                    }
                </script>
            </span>
            <ul class="nav navbar-nav navbar-right">
                <#if session.user??>
                    <li <#if page_tab == 'message'> class="active" </#if>><a href="${baseUrl!}/user/message/${session.user.id!}">通知 <span class="badge" id="badge"></span></a></li>
                    <li class="dropdown">
                        <a href="${baseUrl!}/user/${session.user.id!}" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
                            <img src="${session.user.avatar!}" width="20" style="border-radius: 20px;"/>
                            ${session.user.nickname!}
                            <span class="caret"></span>
                        </a>
                        <span class="dropdown-arrow"></span>
                        <ul class="dropdown-menu">
                            <li><a href="${baseUrl!}/user/${session.user.id!}"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;我的</a></li>
                            <li><a href="${baseUrl!}/message"><span class="glyphicon glyphicon-envelope"></span>&nbsp;&nbsp;私信</a></li>
                            <li><a href="${baseUrl!}/user/setting"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;设置</a></li>
                            <li><a href="${baseUrl!}/logout"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;退出</a></li>
                        </ul>
                    </li>
                <#else>
                    <li><a href="javascript:void(0);" data-toggle="modal" data-target="#loginModal">登录</a></li>
                    <li <#if page_tab == 'reg'> class="active" </#if>><a href="${baseUrl!}/reg.html">注册</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" id="loginModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">登录</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-8">
                            <input type="email" class="form-control" id="email" placeholder="邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="password" placeholder="密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-8">
                            <span id="errMsg"></span>
                            <div class="pull-right">
                                <a href="${baseUrl!}/forgetpwd">忘记密码?</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="padding-top: 20px">
                <span class="pull-left" style="margin: 8px; 0 0;">
                    <a href="${baseUrl!}/oauth/qqlogin">
                        <img src="${baseUrl!}/static/img/QQ_Logo_wiki.png" width="24" alt="QQ 登录">
                    </a>&nbsp;&nbsp;
                    <a href="${baseUrl!}/oauth/weibologin">
                        <img src="http://www.sinaimg.cn/blog/developer/wiki/LOGO_24x24.png" alt="微博登录">
                    </a>
                </span>
                <button type="button" class="btn btn-raised  btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-raised  btn-default" onclick="login()">登录</button>
            </div>
        </div>
    </div>
</div>
<script>
    function login() {
        $.ajax({
            url: "${baseUrl!}/login",
            async: false,
            cache: false,
            type: 'post',
            dataType: "json",
            data: {
                email: $("#email").val(),
                password: md5($("#password").val())
            },
            success: function (data) {
                if (data.code == '200') {
                    location.href="${baseUrl!}/";
                } else {
                    $("#errMsg").css("color", "red").html(data.description);
                }
            }
        });
    }
</script>
</#macro>