<#include "layout/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-header">
                    新消息
                    <a id="markRead" href="javascript:markRead()" class="pull-right" style="display: none">标记已读</a>
                </div>
                <div class="card-body">
                    <#include "components/notification.ftl"/>
                    <#if _user??><@notification userId=_user.id read=0 limit=-1/>
                    <#else>error: no such user
                    </#if>
                </div>
            </div>
            <div class="card">
                <div class="card-header">已读消息</div>
                <div class="card-body">
                    <#include "components/notification.ftl"/>
                    <#if _user??> <@notification userId=_user.id read=1 limit=20/>
                    <#else>error: no such user
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-md-3 d-none d-md-block">
            <#if _user??>
                <#include "components/user_info.ftl"/>
            <#else>
                <#include "components/welcome.ftl"/>
            </#if>
        </div>
    </div>
    <script>
        $(function () {
            if ($(".notification_0").length > 0) {
                $("#markRead").show();
            }
        });

        function markRead() {
         <#if _user??>
            req("get", "/api/notification/markRead", '${_user.token!}', function (data) {
                if (data.code === 200) {
                    window.location.reload();
                }
            });
          </#if>
        }
    </script>
</@html>
