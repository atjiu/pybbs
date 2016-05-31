<#include "common/layout.ftl"/>
<@html page_tab="about" page_title="关于 - ${siteTitle!}">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">关于</div>
            <div class="panel-body">
                <p>朋也社区是用Java语言编写的社区（论坛）系统.</p>
                <p>感谢 <a href="http://jfinal.com" target="_blank">JFinal</a> - Java极速开发框架</p>
                <p>界面布局参照
                    <a href="https://cnodejs.org" target="_blank">cnodejs</a>,
                    <a href="https://v2ex.com" target="_blank">v2ex</a>
                </p>
                <p>
                    欢迎提 <a href="https://github.com/liygheart/pybbs/issues" target="_blank">issues</a>
                </p>
                <p>开源地址:
                <ul>
                    <li>
                        Github: <a href="http://github.com/liygheart/pybbs"
                                   target="_blank">http://github.com/liygheart/pybbs</a>
                    </li>
                    <li>
                        Gitosc: <a href="http://git.oschina.net/20110516/pybbs"
                                   target="_blank">http://git.oschina.net/20110516/pybbs</a>
                    </li>
                </ul>
                <p>使用到的技术:
                <ul>
                    <li>JFinal</li>
                    <li>MySQL(druid数据源)</li>
                    <li>Freemarker</li>
                    <li>Ehcache</li>
                    <li>Bootstrap</li>
                </ul>
                <p>特性:
                <ul>
                    <li>社区兼容性（IE9+）</li>
                    <li>不用session,选用cookie,为了集群方便</li>
                    <li>大量使用了缓存（ehcache）减轻服务器压力（后续还会改成redis作为缓存,集群更加方便）</li>
                    <li>不做本地账户,不会出现用户密码泄露问题（目前就Github登录,后续可以陆续增加其他第三方登录）</li>
                    <li>权限配置简单,轻松管理用户</li>
                    <li>使用textarea作为编辑器,配置快捷键,书写更方便,ie11+/chrome/firefox/safari 还支持截图粘贴上传</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>