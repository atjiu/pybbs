<#include "/page/front/common/_layout.ftl"/>
<@html title="服务 - ${siteTitle!}" description="服务" page_tab="service" sidebar_user_info="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">服务</li>
        </ol>
    </div>
    <div class="panel-body">
        <p>
            JFinalbbs从1.0发布到目前也有好几个版本的更新了,功能上有增加,有删除,还有替换<b>(反正就是瞎折腾)</b>,也感谢QQ群JFinal社区里的伙伴的陪伴
        </p>
        <p>
            在JFinalbbs1.x的时候,群里有几个朋友找到过我,想对JFinalbbs进行二次开发,当初因为工作,生活上的<b>(其实是借口,就是不想接)</b>种种原因,也都不了了之了
        </p>
        <p>
            现在借着功能上的增加,<b>朋也想接点二次开发的活来赚点外快</b>,有想法的朋友快快联系我吧 !
        </p>
        <p>
            <span class="glyphicon glyphicon-warning-sign"></span>
            <b>联系方式在页面底部</b>
        </p>
        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="well">
                    <h3>基础服务</h3>
                    <ul style="padding-left: 20px;">
                        <li>代码以Github上最新代码为准</li>
                        <li>编译JFinalbbs源码并安装在服务器上</li>
                        <li>配置好系统参数,并保证能顺利运行</li>
                        <li>自安装JFinalbbs到服务器成功起,朋也负责维护一周,排查一些访问异常问题</li>
                        <li>
                            <b>注:</b>
                            <ul style="padding-left: 20px;">
                                <li>要提供给我服务器的账户密码</li>
                                <li>费用100元</li>
                                <li>不涉及到代码的修改</li>
                                <li>安装成功,不再退款</li>
                                <li>到账后在条件允许的情况下开始安装,如果安装不成功,立即退款</li>
                            </ul>
                        </li>
                    </ul>
                    <p>
                        <a href="${baseUrl!}/donate" class="btn btn-raised btn-lg btn-outline">我要安装</a>
                    </p>
                </div>
            </div>
            <div class="col-md-6">
                <div class="well">
                    <h3>高级服务</h3>
                    <ul style="padding-left: 20px;">
                        <li>二次开发,具体请联系我详谈 !</li>
                        <li>
                            <b>注:</b>
                            <ul style="padding-left: 20px;">
                                <li>要提供给我服务器的账户密码</li>
                            </ul>
                        </li>
                    </ul>
                    <p>
                        <a href="mailto:liygheart@qq.com" class="btn btn-raised btn-lg btn-outline">联系我</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</@html>