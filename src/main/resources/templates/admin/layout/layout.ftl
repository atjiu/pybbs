<#macro html page_title page_tab>
    <!doctype html>
    <html lang="zh-CN">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>${page_title}</title>
        <link rel="icon" href="https://yiiu.co/favicon.ico">
        <#--css-->
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/css/AdminLTE.min.css"/>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/css/skins/_all-skins.min.css"/>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.min.css"/>
        <style>
            @media screen and (max-width: 768px) {
                .content-header {
                    position: relative;
                    padding: 65px 15px 0 15px;
                }
            }

            .content {
                overflow: hidden;
                padding-bottom: 25px;
            }
        </style>

        <#--javascript-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
        <script src="/static/admin/js/app.min.js"></script>

        <script>
            function toast(txt, icon) {
                $.toast({
                    text: txt, // Text that is to be shown in the toast
                    heading: '系统提醒', // Optional heading to be shown on the toast
                    icon: icon || 'error', // Type of toast icon warning, info, success, error
                    showHideTransition: 'slide', // fade, slide or plain
                    allowToastClose: true, // Boolean value true or false
                    hideAfter: 2000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
                    stack: false, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
                    position: 'top-right', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
                });
            }

            jQuery.ajaxSetup({
                complete: function (xmlHttp) {
                    if (/^4\d+?/.test(xmlHttp.status)) {
                        if (confirm("登录信息过期，请重新登录")) {
                            window.location.href = "/adminlogin";
                        }
                    }
                }
            })
        </script>
    </head>
    <body class="hold-transition skin-blue sidebar-mini" style="background-color: #ecf0f5;">
    <div>
        <#include "header.ftl">
        <#include "menu.ftl">
        <@menu page_tab=page_tab/>
        <div class="content-wrapper" style="padding: 50px 0 40px;">
            <#nested>
        </div>
        <#include "footer.ftl"/>
    </div>
    </body>
    </html>
</#macro>
