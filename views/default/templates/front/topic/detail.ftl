<#include "../common/layout.ftl">
<@html page_title="${topic.title!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body topic-detail-header">
        <div class="media">
          <div class="media-body">
            <h2 class="topic-detail-title">
              <#if model.isEmpty(topic.url)>
                ${topic.title!?html}
              <#else>
                <a href="${topic.url!?html}" target="_blank">
                  <i class="glyphicon glyphicon-link"></i>
                  ${topic.title!?html}
                </a>
              </#if>
            </h2>
            <p class="gray">
              <#if topic.top == true>
                <span class="label label-primary">置顶</span>
                <span>•</span>
              <#elseif topic.good == true>
                <span class="label label-success">精华</span>
                <span>•</span>
              </#if>
              <span><a href="/user/${topic.user.username!}">${topic.user.username!}</a></span>
              <span>•</span>
              <span>${model.formatDate(topic.inTime)}</span>
              <span>•</span>
              <span>${topic.view!1}次点击</span>
              <span>•</span>
              <span>来自 <a href="/go/${topic.node.value!}">${topic.node.name!}</a></span>
              <#if sec.isAuthenticated() && !sec.isLock()>
                <#if sec.topicEditable(topic)>
                  <span>•</span>
                  <#if sec.allGranted("topic:edit")>
                    <span><a href="/admin/topic/${topic.id?c}/edit">编辑</a></span>
                  <#else>
                    <span><a href="/topic/${topic.id?c}/edit">编辑</a></span>
                  </#if>
                </#if>
                <#if sec.allGranted("topic:delete")>
                  <span>•</span>
                  <span>
                    <a href="javascript:if(confirm('确定要删除吗？'))location.href='/admin/topic/${topic.id?c}/delete'">删除</a>
                  </span>
                </#if>
                <#if sec.allGranted("topic:top")>
                  <span>•</span>
                  <#if topic.top == true>
                    <span><a
                        href="javascript:if(confirm('确定要取消置顶吗？'))location.href='/admin/topic/${topic.id?c}/top'">取消置顶</a></span>
                  <#else>
                    <span><a
                        href="javascript:if(confirm('确定要置顶吗？'))location.href='/admin/topic/${topic.id?c}/top'">置顶</a></span>
                  </#if>
                </#if>
                <#if sec.allGranted("topic:good")>
                  <span>•</span>
                  <#if topic.good == true>
                    <span><a href="javascript:if(confirm('确定要取消加精吗？'))location.href='/admin/topic/${topic.id?c}/good'">取消加精</a></span>
                  <#else>
                    <span><a
                        href="javascript:if(confirm('确定要加精吗？'))location.href='/admin/topic/${topic.id?c}/good'">加精</a></span>
                  </#if>
                </#if>

                <#if sec.allGranted("topic:good")>
                  <span>•</span>
                  <#if topic.lock == true>
                    <span><a
                        href="javascript:if(confirm('确定要取消锁定吗？'))location.href='/admin/topic/${topic.id?c}/lock'">取消锁定</a></span>
                  <#else>
                    <span><a
                        href="javascript:if(confirm('确定要锁定吗？'))location.href='/admin/topic/${topic.id?c}/lock'">锁定</a></span>
                  </#if>
                </#if>
              </#if>
            </p>
          </div>
          <div class="media-right">
            <img src="${topic.user.avatar}" class="avatar-lg"/>
          </div>
        </div>
      </div>
      <#if topic.content?? && topic.content != "">
        <div class="divide"></div>
        <div class="panel-body topic-detail-content show_big_image">
          ${model.marked(topic.content, false)}
        </div>
      </#if>
      <#if sec.isAuthenticated()>
        <div class="panel-footer">
          <a
              href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.baseUrl!}/topic/${topic.id?c}?r=${sec.getPrincipal()!}&title=${topic.title!?html}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
          <#if collect??>
            <a href="/collect/${topic.id?c}/delete">取消收藏</a>
          <#else>
            <a href="/collect/${topic.id?c}/add">加入收藏</a>
          </#if>
          <span class="pull-right">${collectCount!0}个收藏</span>
        </div>
      </#if>
    </div>
    <#if topic.replyCount == 0 && topic.lock == false>
      <div class="panel panel-default">
        <div class="panel-body text-center">目前暂无回复</div>
      </div>
    <#else>
      <div class="panel panel-default">
        <div class="panel-heading">${topic.replyCount!0} 条回复</div>
        <div class="panel-body paginate-bot panel-body-reply">
          <#include "../components/replies.ftl"/>
          <@reply id=topic.id topic_user=topic.user/>
        </div>
      </div>
    </#if>
    <#if sec.isAuthenticated()>
      <div class="panel panel-default">
        <#if topic.lock == true>
          <div class="panel-body text-center">该话题目前已经被锁定，无法添加新回复。</div>
        <#else>
          <div class="panel-heading">
            添加一条新回复
            <small class="text-danger">这会<#if site.createReplyScore gte 0>增加<#else>扣除</#if>你${site.createReplyScore?abs}积分</small>
            <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
          </div>
          <div class="panel-body">
            <#if !sec.isLock()>
              <form action="/reply/save" method="post" id="editorForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" value="${topic.id?c}" name="topicId"/>
                <#include "../components/editor.ftl"/>
                <@editor row=5 type="reply"/>
                <button type="submit" class="btn btn-default">回复</button>
              </form>
            <#else>
              <div class="text-center">你的帐户被禁用了，不能回复</div>
            </#if>
          </div>
        </#if>
      </div>
    </#if>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if sec.isAuthenticated()>
      <#include "../components/user_info.ftl"/>
      <@user_info/>
    <#else>
      <#include "../components/welcome.ftl"/>
      <@welcome/>
    </#if>
  </div>
  <#include "../components/show_big_image.ftl"/>
</div>
<script src="/static/js/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
</@html>