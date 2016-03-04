<#include "/page/front/common/_layout.ftl"/>
<@html title="首页 - ${siteTitle!}" description="社区接口" page_tab="api" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">接口</li>
        </ol>
    </div>
    <div class="panel-body">
        <h4>用户令牌获取: 先登录,然后打开设置,页面底部二维码</h4>
        <div class="divide"></div>
        <h3>话题列表 GET</h3>
        <p>请求URL: /api/index</p>
        <p>请求参数</p>
        <ul>
            <li>tab <code>string</code> 模块</li>
            <li>p <code>number</code> 页数</li>
            <li>size <code>number</code> 每页显示条数</li>
        </ul>
        <p>实例: <a href="http://jfinalbbs.com/api/index?tab=good&p=1&size=20">/api/index</a></p>
        <div class="divide"></div>
        <h3>话题详情 GET</h3>
        <p>请求URL: /api/topic/:id</p>
        <p>请求参数</p>
        <ul>
            <li>id <code>string</code> 话题id</li>
            <li>token <code>string</code> 用户令牌（可选）</li>
        </ul>
        <p>实例: <a href="http://jfinalbbs.com/api/topic/99ecf557bda64550a78a627522c72055">/api/topic/99ecf557bda64550a78a627522c72055</a></p>
        <div class="divide"></div>
        <h3>模块列表 GET</h3>
        <p>请求URL: /api/section/index</p>
        <p>实例: <a href="http://jfinalbbs.com/api/section/index">/api/section/index</a></p>
        <div class="divide"></div>
        <h3>用户信息 GET</h3>
        <p>请求URL: /api/user/userinfo</p>
        <ul>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>个人信息（包含用户发布的话题, 收藏的话题） GET</h3>
        <p>请求URL: /api/user/index</p>
        <ul>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>发布话题 POST</h3>
        <p>请求URL: /api/topic/create</p>
        <ul>
            <li>sid <code>number</code> 模块ID</li>
            <li>title <code>string</code> 标题</li>
            <li>content <code>string</code> 内容（html语法格式的内容）</li>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>回复话题 POST</h3>
        <p>请求URL: /api/reply/create</p>
        <ul>
            <li>tid <code>string</code> 话题ID</li>
            <li>content <code>string</code> 内容（html语法格式的内容）</li>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>通知 GET</h3>
        <p>请求URL: /api/notification/index</p>
        <ul>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>通知数量 GET</h3>
        <p>请求URL: /api/notification/countnotread</p>
        <ul>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>收藏话题 GET</h3>
        <p>请求URL: /api/collect/index</p>
        <ul>
            <li>tid <code>string</code> 话题ID</li>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
        <div class="divide"></div>
        <h3>取消收藏 GET</h3>
        <p>请求URL: /api/collect/delete</p>
        <ul>
            <li>tid <code>string</code> 话题ID</li>
            <li>token <code>string</code> 用户令牌</li>
        </ul>
    </div>
</div>
</@html>