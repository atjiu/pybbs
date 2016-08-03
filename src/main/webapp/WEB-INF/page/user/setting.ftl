<#include "../common/layout.ftl"/>
<@html page_title="修改个人资料" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 个人设置
            </div>
            <div class="panel-body">
                <#if msg??>
                    <div class="alert alert-success" role="alert">
                    ${msg!}
                    </div>
                </#if>
                <form action="/user/setting" method="post" id="userProfileForm">
                    <div class="form-group">
                        <label for="nickname">昵称</label>
                        <input type="text" disabled class="form-control" id="nickname" value="${userinfo.nickname!}"/>
                    </div>
                    <div class="form-group">
                        <label for="email">邮箱</label>
                        <input type="text" disabled class="form-control" id="email" value="${userinfo.email!}"/>
                    </div>
                    <div class="form-group">
                        <label for="url">Github主页</label>
                        <input type="text" class="form-control" id="url" name="url" value="${userinfo.url!}"/>
                    </div>
                    <div class="form-group">
                        <label for="signature">个性签名</label>
                        <textarea class="form-control" name="signature" id="signature">${userinfo.signature!}</textarea>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" id="receive_msg" name="receive_msg" value="1"
                               <#if userinfo.receive_msg>checked</#if>/>
                        <label for="receive_msg">是否接收系统邮件</label>
                    </div>
                    <button type="button" id="userProfileUpdateBtn" onclick="updateUserProfile()"
                            class="btn btn-default">保存设置
                    </button>
                    <span id="error_message"></span>
                </form>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Access Token</div>
            <div class="panel-body">
                <p>AccessToken: ${userinfo.access_token!}</p>
                <p id="qrcode"></p>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">

    </div>
</div>
<script src="//cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script>
    $(function () {
        $('#qrcode').qrcode("${userinfo.access_token!}");
    });
</script>
</@html>