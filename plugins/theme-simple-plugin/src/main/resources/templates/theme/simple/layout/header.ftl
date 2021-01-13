<#macro header page_tab>
    <header>
        <ul>
            <li><a href="/" class="title">${site.name}</a></li>
            <li <#if page_tab == "all">class="active"</#if>><a href="/?tab=all">全部</a></li>
            <li <#if page_tab == "good">class="active"</#if>><a href="/?tab=good">精华</a></li>
            <li <#if page_tab == "hot">class="active"</#if>><a href="/?tab=hot">最热</a></li>
            <li <#if page_tab == "newest">class="active"</#if>><a href="/?tab=newest">最新</a></li>
            <li <#if page_tab == "noanswer">class="active"</#if>><a href="/?tab=noanswer">无人问津</a></li>
            <li <#if page_tab == "tags">class="active"</#if>><a href="/tags">标签</a></li>
        </ul>
        <ul>
            <#if _user??>
                <li <#if page_tab == "create">class="active"</#if>><a href="/topic/create">创建话题</a></li>
                <li <#if page_tab == "notifications">class="active"</#if>><a href="/notifications">通知 <span
                                id="notReadCount">0</span></a></li>
                <li <#if page_tab == "user">class="active"</#if>><a href="/user/${_user.username}">${_user.username}</a>
                </li>
                <li <#if page_tab == "settings">class="active"</#if>><a href="/settings">设置</a></li>
                <li><a href="javascript:if(confirm('确定要登出吗？'))location.href='/logout'">登出</a></li>
            <#else>
                <li <#if page_tab == "login">class="active"</#if>><a href="/login">登录</a></li>
                <li <#if page_tab == "register">class="active"</#if>><a href="/register">注册</a></li>
                <li><a href="/oauth/github">Github 登录</a></li>
            </#if>
        </ul>
    </header>
    <#if _user??>
        <script>
            $.ajax({
                url: '/api/notification/notRead',
                cache: false,
                async: false,
                type: 'get',
                dataType: 'json',
                contentType: 'application/json',
                headers: {
                    'token': '${_user.token!}'
                },
                success: function (data) {
                    if (data.code === 200) {
                        if (data.detail > 0) {
                            document.title = "(" + data.detail + ") " + document.title;
                            $("#notReadCount").text(data.detail);
                            $("#notReadCount").show();
                        }
                    }
                }
            })
        </script>
    </#if>
</#macro>
