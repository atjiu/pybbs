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
                <h4 id="toc_5">GET: /api/t/:id</h4>
                <ul style="padding-left: 20px;">
                    <li>id <code>Integer</code> 话题ID</li>
                    <li>mdrender <code>Boolean</code> 当为 false 时，不渲染。默认为 true，将内容渲染成 html 格式文本</li>
                </ul>
                <p>示例: <a href="/api/t/34">/api/t/34</a></p>
                <hr>
                <h3 id="toc_6">用户首页</h3>
                <h4 id="toc_7">GET: /api/user/:nickname</h4>
                <ul style="padding-left: 20px;">
                    <li>nickname <code>String</code> 昵称</li>
                </ul>
                <p>示例: <a href="/api/user/tomoya92">/api/user/tomoya92</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>
