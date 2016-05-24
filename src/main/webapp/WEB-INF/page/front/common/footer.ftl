<#macro footer>
<div class="footer">
    <div class="container">
        <div style="padding-bottom: 10px; text-align: left;" class="hidden-sm hidden-xs">
            <ul class="nav nav-pills" id="links">
                <li>友情链接：</li>
                <#list links as link>
                    <li><a href="${link.url!}" target="_blank">${link.name!}</a></li>
                </#list>
            </ul>
        </div>
        <a href="http://git.oschina.net/20110516/jfinalbbs" target="_blank">源码地址</a>
        <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=c130a2aea2fa297b67d39eca4531bcf878735eecd3fe7645d49d8c7f5458147e">QQ</a>&nbsp;
        <a target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=AW1oeGZpZGBzdUF3aHEvcHAvYm5s">Email</a>&nbsp;
        <br>
        <#--感谢使用JFinalbbs代码，为了能让社区被更多人知晓，请不要修改下面一行的代码，谢谢！-->
        &copy;2015 Powered by <a href="http://jfinalbbs.com" target="_blank">JFinalbbs</a>
        <a href="http://www.miitbeian.gov.cn/">${beian_name!}</a>
        ${tongji_js!}
    </div>
</div>
</#macro>