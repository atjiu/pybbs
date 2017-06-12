<#include "../common/layout.ftl">
<@html page_title="${topic.title}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body topic-detail-header">
        <div class="media">
          <div class="media-body">
            <h2 class="topic-detail-title">${topic.title!}</h2>
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
              <span>${topic.formatDate(topic.inTime)}</span>
              <span>•</span>
              <span>${topic.view!1}次点击</span>
              <span>•</span>
              <span>来自 <a href="/?tab=${topic.tab!}">${topic.tab!}</a></span>
              <#if sec.isAuthenticated() && !sec.isLock()>
                <#if sec.allGranted("topic:edit") || sec.getPrincipal() == topic.user.username>
                  <span>•</span>
                  <span><a href="/topic/${topic.id}/edit">编辑</a></span>
                </#if>
                <#if sec.allGranted("topic:delete")  || sec.getPrincipal() == topic.user.username>
                  <span>•</span>
                  <span><a
                      href="javascript:if(confirm('确定要删除吗？'))location.href='/topic/${topic.id}/delete'">删除</a></span>
                </#if>
                <#if sec.allGranted("topic:top")>
                  <span>•</span>
                  <#if topic.top == true>
                    <span><a
                        href="javascript:if(confirm('确定要取消置顶吗？'))location.href='/topic/${topic.id}/top'">取消置顶</a></span>
                  <#else>
                    <span><a
                        href="javascript:if(confirm('确定要置顶吗？'))location.href='/topic/${topic.id}/top'">置顶</a></span>
                  </#if>
                </#if>
                <#if sec.allGranted("topic:good")>
                  <span>•</span>
                  <#if topic.good == true>
                    <span><a href="javascript:if(confirm('确定要取消加精吗？'))location.href='/topic/${topic.id}/good'">取消加精</a></span>
                  <#else>
                    <span><a
                        href="javascript:if(confirm('确定要加精吗？'))location.href='/topic/${topic.id}/good'">加精</a></span>
                  </#if>
                </#if>

                <span>•</span>
                <#if topic.lock == true>
                  <span><a
                      href="javascript:if(confirm('确定要取消锁定吗？'))location.href='/topic/${topic.id}/lock'">取消锁定</a></span>
                <#else>
                  <span><a href="javascript:if(confirm('确定要锁定吗？'))location.href='/topic/${topic.id}/lock'">锁定</a></span>
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
        <div class="panel-body topic-detail-content">
        ${topic.markedNotAt(topic.content)}
        </div>
      </#if>
      <#if sec.isAuthenticated()>
        <div class="panel-footer">
          <a
              href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.baseUrl!}/topic/${topic.id!}?r=${sec.getPrincipal()!}&title=${topic.title!}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
          <#if collect??>
            <a href="/collect/${topic.id!}/delete">取消收藏</a>
          <#else>
            <a href="/collect/${topic.id!}/add">加入收藏</a>
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
          <@reply id=topic.id/>
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
            <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
          </div>
          <div class="panel-body">
            <#if !sec.isLock()>
              <form action="/reply/save" method="post" id="replyForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" value="${topic.id}" name="topicId"/>
                <div class="form-group">
                  <textarea name="content" id="content" rows="5" class="form-control" style="height: 150px;"></textarea>
                </div>
                <button type="button" onclick="replySubmit()" class="btn btn-default">回复</button>
                <span id="error_message"></span>
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

    <#include "../components/user_info.ftl"/>
    <@user_info username=topic.user.username text="作者"/>

    <#--author other topics-->
    <@other_topics_tag userId=topic.user.id>
      <div class="panel panel-default">
        <div class="panel-heading">作者其他话题</div>
        <div class="panel-body">
          <#list page.getContent() as topic>
            <p><a href="/topic/${topic.id!}">${topic.title!}</a></p>
            <#if topic_has_next>
              <div class="divide pad-bot-10"></div>
            </#if>
          </#list>
        </div>
      </div>
    </@other_topics_tag>

  </div>
</div>
  <#if sec.isAuthenticated() && !sec.isLock()>
  <script>
    function replySubmit() {
      var errors = 0;
      var em = $("#error_message");
      var content = $("#content").val();
      if (content.length === 0) {
        errors++;
        em.html("回复内容不能为空");
      }
      if (errors === 0) {
        $("#content").val(content);
        var form = $("#replyForm");
        form.submit();
      }
    }
  </script>
  </#if>
</@html>