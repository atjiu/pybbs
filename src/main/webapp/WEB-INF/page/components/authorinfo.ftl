<#macro info>
<div class="panel panel-default">
    <div class="panel-heading">
        作者
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="/user/${authorinfo.nickname!}">
                    <img src="${authorinfo.avatar!}" title="${authorinfo.nickname!}" class="avatar"/>
                </a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <a href="/user/${authorinfo.nickname!}">${authorinfo.nickname!}</a>
                </div>
                <p>积分: ${authorinfo.score!}</p>
            </div>
            <#if authorinfo.signature?? && authorinfo.signature != "">
                <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
                    <i>“ ${authorinfo.signature!} ” </i>
                </div>
            </#if>
        </div>
    </div>
</div>
</#macro>