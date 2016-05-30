<#macro info>
<div class="panel panel-default">
    <div class="panel-heading">
        个人信息
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="/user/${userinfo.nickname!}">
                    <img src="${userinfo.avatar!}" title="${userinfo.nickname!}" class="avatar"/>
                </a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <a href="/user/${userinfo.nickname!}">${userinfo.nickname!}</a>
                </div>
                <p>积分: ${userinfo.score!}</p>
            </div>
            <#if userinfo.signature?? && userinfo.signature != "">
                <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
                    <i>“ ${userinfo.signature!} ” </i>
                </div>
            </#if>
        </div>
    </div>
</div>
</#macro>