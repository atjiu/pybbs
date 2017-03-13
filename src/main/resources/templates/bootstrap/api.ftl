<#include "common/layout.ftl"/>
<@html page_tab="api" page_title="API - ${siteTitle!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><a href="/">主页</a> / API</div>
      <div class="panel-body">
        <h3 id="toc_0">版块列表</h3>
        <h4 id="toc_1">GET: /api/sections</h4>
        <p>示例: <a href="/api/sections">/api/sections</a></p>
        <hr>
        <h3 id="toc_2">话题列表</h3>
        <h4 id="toc_3">GET: /api/topics</h4>
        <ul style="padding-left: 20px;">
          <li>p <code>Integer</code> 页数</li>
          <li>tab <code>String</code> 版块标识</li>
        </ul>
        <p>示例: <a href="/api/topics">/api/topics</a></p>
        <hr>
        <h3 id="toc_4">话题详情</h3>
        <h4 id="toc_5">GET: /api/topic/:id</h4>
        <ul style="padding-left: 20px;">
          <li>id <code>Integer</code> 话题ID</li>
          <li>mdrender <code>Boolean</code> 当为 false 时，不渲染。默认为 true，将内容渲染成 html 格式文本</li>
        </ul>
        <p>示例: <a href="/api/topic/34">/api/topic/34</a></p>
        <hr>
        <h3 id="toc_6">用户首页</h3>
        <h4 id="toc_7">GET: /api/user/:nickname</h4>
        <ul style="padding-left: 20px;">
          <li>nickname <code>String</code> 昵称</li>
        </ul>
        <p>示例: <a href="/api/user/tomoya92">/api/user/tomoya92</a></p>
        <hr>
        <h3 id="toc_6">发布话题</h3>
        <h4 id="toc_7">GET: /api/topic/create</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
          <li>title <code>String</code> 话题标题 必填</li>
          <li>content <code>String</code> 话题内容</li>
          <li>tab <code>String</code> 话题板块</li>
        </ul>
        <hr>
        <h3 id="toc_6">收藏话题</h3>
        <h4 id="toc_7">GET: /api/topic/collect</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
          <li>tid <code>Integer</code> 话题ID</li>
        </ul>
        <hr>
        <h3 id="toc_6">取消收藏</h3>
        <h4 id="toc_7">GET: /api/topic/del_collect</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
          <li>tid <code>Integer</code> 话题ID</li>
        </ul>
        <hr>
        <h3 id="toc_6">收藏列表</h3>
        <h4 id="toc_7">GET: /api/collects/:nickname</h4>
        <ul style="padding-left: 20px;">
          <li>nickname <code>String</code> 用户昵称</li>
        </ul>
        <p>示例: <a href="/api/collects/tomoya92">/api/collects/tomoya92</a></p>
        <hr>
        <h3 id="toc_6">创建回复</h3>
        <h4 id="toc_7">GET: /api/reply/create</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
          <li>tid <code>Integer</code> 要回复的话题ID</li>
          <li>content <code>String</code> 回复内容</li>
        </ul>
        <hr>
        <h3 id="toc_6">未读通知数</h3>
        <h4 id="toc_7">GET: /api/notification/count</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
        </ul>
        <hr>
        <h3 id="toc_6">通知列表</h3>
        <h4 id="toc_7">GET: /api/notifications</h4>
        <ul style="padding-left: 20px;">
          <li>token <code>String</code> 用户令牌,在个人设置页面扫描二维码获取</li>
          <li>mdrender <code>Boolean</code> 当为 false 时，不渲染。默认为 true，将内容渲染成 html 格式文本</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>
