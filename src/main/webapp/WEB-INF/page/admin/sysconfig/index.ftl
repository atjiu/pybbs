<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="sysconfig">
<section class="content-header">
    <h1>
        设置
        <small>系统参数设置</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li class="active">设置</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <form class="form-horizontal" action="${baseUrl!}/admin/sysconfig/save" method="POST">
            <div class="box-header with-border">
                <h3 class="box-title">系统变量</h3>
            </div>
            <div class="box-body">
                <div class="box-body">
                    <div class="form-group">
                        <label for="siteTitle" class="col-sm-2 control-label">站点标题</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="siteTitle" name="siteTitle" value="${siteTitle!}" placeholder="站点标题,例:JFinalbbs">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pageSize" class="col-sm-2 control-label">分页条数</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="pageSize" name="pageSize" value="${pageSize!}" placeholder="分页条数,例:20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="baidu_site_meta" class="col-sm-2 control-label">百度站长meta</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="baidu_site_meta" name="baidu_site_meta" value='${baidu_site_meta!}' placeholder='百度站长meta,例:&lt;meta property="baidu-site-verification" content="" /&gt;'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="google_site_meta" class="col-sm-2 control-label">谷歌站长meta</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="google_site_meta" name="google_site_meta" value='${google_site_meta!}' placeholder='谷歌站长meta,例:&lt;meta property="google-site-verification" content="" /&gt;'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bing_site_meta" class="col-sm-2 control-label">Bing站长meta</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="bing_site_meta" name="bing_site_meta" value='${bing_site_meta!}' placeholder='Bing站长meta,例:&lt;meta name="msvalidate.01" content="" /&gt;'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="beian_name" class="col-sm-2 control-label">备案名称</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="beian_name" name="beian_name" value='${beian_name!}' placeholder='备案名称'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tongji_js" class="col-sm-2 control-label">统计JS</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="tongji_js" name="tongji_js" value='${tongji_js!}' placeholder='统计JS'>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-header with-border">
                <h3 class="box-title">第三方</h3>
            </div>
            <div class="box-body">
                <div class="box-body">
                    <h5>QQ登录</h5>
                    <div class="form-group">
                        <label for="qq_appId" class="col-sm-2 control-label">APPID</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qq_appId" name="qq_appId" value="${qq_appId!}" placeholder="APPID">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="qq_appKey" class="col-sm-2 control-label">APPKEY</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qq_appKey" name="qq_appKey" value="${qq_appKey!}" placeholder="APPKEY">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="qq_redirect_URI" class="col-sm-2 control-label">REDIRECT_URI</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qq_redirect_URI" name="qq_redirect_URI" value="${qq_redirect_URI!}" placeholder="REDIRECT_URI,例:http://jfinalbbs.com/oauth/qqlogincallback">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="qq_meta" class="col-sm-2 control-label">meta</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qq_meta" name="qq_meta" value='${qq_meta!}' placeholder='meta,例:&lt;meta property="qc:admins" content="" /&gt;'>
                        </div>
                    </div>
                    <br>
                    <h5>新浪微博</h5>
                    <div class="form-group">
                        <label for="sina_clientId" class="col-sm-2 control-label">CLIENT_ID</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="sina_clientId" name="sina_clientId" value="${sina_clientId!}" placeholder="CLIENT_ID">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sina_clientSercret" class="col-sm-2 control-label">CLIENT_SERCRET</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="sina_clientSercret" name="sina_clientSercret" value="${sina_clientSercret!}" placeholder="CLIENT_SERCRET">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sina_redirect_URI" class="col-sm-2 control-label">REDIRECT_URI</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="sina_redirect_URI" name="sina_redirect_URI" value="${sina_redirect_URI!}" placeholder="REDIRECT_URI,例:http://jfinalbbs.com/oauth/weibologincallback">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sina_meta" class="col-sm-2 control-label">meta</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="sina_meta" name="sina_meta" value='${sina_meta!}' placeholder='meta,例:&lt;meta property="wb:webmaster" content="" /&gt;'>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-header with-border">
                <h3 class="box-title">邮箱</h3>
            </div>
            <div class="box-body">
                <div class="box-body">
                    <div class="form-group">
                        <label for="emailSmtp" class="col-sm-2 control-label">SMTP</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="emailSmtp" name="emailSmtp" value="${emailSmtp!}" placeholder="STMP,例:smtp.qq.com">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emailSender" class="col-sm-2 control-label">发件人名称</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="emailSender" name="emailSender" value="${emailSender!}" placeholder="发件人名称,例:JFinalbbs">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emailUsername" class="col-sm-2 control-label">邮箱地址</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="emailUsername" name="emailUsername" value="${emailUsername!}" placeholder="Email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emailPassword" class="col-sm-2 control-label">邮箱密码</label>

                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="emailPassword" name="emailPassword" value="${emailPassword!}" placeholder="Password">
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <button type="submit" class="btn btn-raised btn-default ">保存</button>
                </div>
            </div>
        </form>
    </div>
</section>
<script>

</script>
</@layout>