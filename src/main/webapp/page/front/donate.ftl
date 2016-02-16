<#include "/page/front/common/_layout.ftl"/>
<@html title="首页 - ${siteTitle!}" description="捐助社区" page_tab="" sidebar_user_info="show">

<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">捐赠</li>
        </ol>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-6 text-center">
                <img src="${baseUrl!}/static/img/jz_alipay.jpg"/><br>
                <p>支付宝二维码扫描捐助</p>
            </div>
            <div class="col-md-6 text-center">
                <img src="${baseUrl!}/static/img/jz_weixin.jpg" /><br>
                <p>微信二维码扫描捐助</p>
            </div>
        </div>
    </div>
</div>
</@html>