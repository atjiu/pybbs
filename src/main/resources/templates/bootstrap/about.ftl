<#include "common/layout.ftl"/>
<@html page_tab="about" page_title="关于 - ${siteTitle!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><a href="/">主页</a> / 关于</div>
      <div class="panel-body topic-detail-content">
        <p>朋也社区是用Java语言编写的社区（论坛）系统.</p>
        <p>界面布局参照
          <a href="https://cnodejs.org" target="_blank">cnodejs</a>,
          <a href="https://v2ex.com" target="_blank">v2ex</a>
        </p>
        <p>
          欢迎提 <a href="https://github.com/tomoya92/pybbs/issues" target="_blank">issues</a>
        </p>
        <p>开源地址:
        <ul>
          <li>
            Github: <a href="http://github.com/tomoya92/pybbs"
                       target="_blank">http://github.com/tomoya92/pybbs</a>
          </li>
          <li>
            Gitosc: <a href="http://git.oschina.net/20110516/pybbs"
                       target="_blank">http://git.oschina.net/20110516/pybbs</a>
          </li>
        </ul>
        <p>使用到的技术:
        <ul>
          <li>jdk8</li>
          <li>tomcat8</li>
          <li>springboot</li>
          <li>springsecurity</li>
          <li>springdata</li>
          <li>freemarker</li>
          <li>mysql</li>
          <li>bootstrap</li>
        </ul>
        <p>特性:
        <ul>
          <li>社区兼容性（IE9+）</li>
          <li>页面自适应布局</li>
          <li>本地登录，注册</li>
          <li>使用springboot开发</li>
          <li>权限使用springsecurity，基于url做的权限方便配置管理</li>
          <li>使用的springdata操作数据存储</li>
          <li>使用 <a href="https://github.com/lepture/editor" target="_blank">editor</a> 作为 Markdown编辑器, 书写更方便, 还支持截图粘贴上传</li>
          <li>页面与 <a href="http://bbs.tomoya.cn" target="_blank">http://bbs.tomoya.cn</a> 一样</li>
        </ul>
        <p>使用者</p>
        <ul>
          <li><a href="http://bbs.narutogis.com/" target="_blank">http://bbs.narutogis.com/</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>
