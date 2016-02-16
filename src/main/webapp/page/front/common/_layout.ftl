<#macro html title description page_tab="" sidebar_user_info="" sidebar_topic_user="" sidebar_checkin="" sidebar_create=""
    sidebar_scoretop="" sidebar_create_info="" sidebar_about="" sidebar_other_topic="" sidebar_not_reply_topic=""
    sidebar_xgtopic="" sidebar_jfbbs_run_status="">
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <title>${title!"JFinalbbs"}</title>
    <meta name="description" content="${description!} - ${siteTitle!}">
    <meta name="keywords" content="java,社区,jfinal,jfinalbbs,JFinalbbs,javabbs,java论坛,论坛,bootstrap,flatui"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    ${qq_meta!}
    ${sina_meta!}
    ${baidu_site_meta!}
    ${google_site_meta!}
    ${bing_site_meta!}
    <link rel="icon" href="${baseUrl!}/static/favicon.ico">
    <link href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap-material-design/0.5.8/css/bootstrap-material-design.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap-material-design/0.5.8/css/ripples.min.css" rel="stylesheet">
    <link href="${baseUrl!}/static/css/style.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap-material-design/0.5.8/js/material.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap-material-design/0.5.8/js/ripples.min.js"></script>
    <script type="text/javascript" src="${baseUrl!}/static/bootstrap/js/bootstrap-paginator.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap-hover-dropdown/2.2.1/bootstrap-hover-dropdown.min.js"></script>
    <script src="http://cdn.bootcss.com/blueimp-md5/2.1.0/js/md5.min.js"></script>
</head>
<body>
<#--页面头部信息-->
    <#include "/page/front/common/header.ftl"/>
    <@header page_tab=page_tab/>
<div class="container">
    <div class="row">
        <div class="content-wrap">
            <div class="col-md-9">
                <#nested>
            </div>
            <div class="col-md-3 hidden-sm hidden-xs">
                <div id="jf_sidebar">
                    <#include "/page/front/common/sidebar.ftl"/>
                <@sidebar sidebar_user_info=sidebar_user_info sidebar_topic_user=sidebar_topic_user sidebar_checkin=sidebar_checkin
                sidebar_create=sidebar_create sidebar_scoretop=sidebar_scoretop
                sidebar_create_info=sidebar_create_info sidebar_about=sidebar_about sidebar_other_topic=sidebar_other_topic
                sidebar_not_reply_topic=sidebar_not_reply_topic sidebar_xgtopic=sidebar_xgtopic sidebar_jfbbs_run_status=sidebar_jfbbs_run_status/>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="backtotop">回到顶部</div>
<script type="text/javascript">
    $(document).ready(function () {
        var n = $("#backtotop");
        n.click(function () {
            return $("html,body").animate({
                scrollTop: 0
            });
        });
        $(window).scroll(function () {
            var t = $(window).scrollTop();
            t > 200 ? n.fadeIn() : n.fadeOut()
        });
        <#if session.user??>
            // 定时取未读消息
            var htmlTitle = document.title;
            getCountnotread(htmlTitle);
            setInterval(function () {
                getCountnotread(htmlTitle);
            }, 3 * 60 * 1000);
        </#if>

        function getCountnotread(_htmlTitle) {
            $.ajax({
                url: "${baseUrl!}/notification/countnotread",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {},
                success: function (data) {
                    if (data.code == '200') {
                        if (data.detail > 0) {
                            $("#badge").html(data.detail);
                            if (data.detail == 0) {
                                document.title = _htmlTitle;
                            } else if (data.detail > 0) {
                                document.title = "(" + data.detail + ") " + _htmlTitle;
                            }
                        }
                    }
                }
            });
        }
    });
</script>
<#--footer-->
    <#include "/page/front/common/footer.ftl"/>
    <@footer/>
</body>
</html>
</#macro>