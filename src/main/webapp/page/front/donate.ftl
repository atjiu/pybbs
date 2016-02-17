<#include "/page/front/common/_layout.ftl"/>
<@html title="首页 - ${siteTitle!}" description="捐赠" page_tab="" sidebar_user_info="show">

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
        <br>
        <div class="well">
            <table class="table">
                <thead>
                <tr>
                    <th>捐赠者</th>
                    <th>金额</th>
                    <th>时间</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>admin</td>
                    <td>10元</td>
                    <td>2016-02-17</td>
                </tr>
                </tbody>
            </table>
            <p>
                2015年也有几位捐赠者, 抱歉我找不到记录了, 在此感谢 !
            </p>
        </div>
    </div>
</div>
</@html>