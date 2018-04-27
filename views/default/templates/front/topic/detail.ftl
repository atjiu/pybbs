<#include "../layout/layout.ftl">
<@html page_title="${topic.title!}">
<link rel="stylesheet" href="/static/wangEditor/wangEditor.min.css">
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
          <i class="fa fa-chevron-up
              <#if user?? && topic.upIds?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
             onclick="voteTopic(this, '${topic.id}', 'up')"></i>
          <i class="fa fa-chevron-down
              <#if user?? && topic.downIds?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
             onclick="voteTopic(this, '${topic.id}', 'down')"></i>
          <span id="up_down_vote_count_${topic.id}">${topic.up - topic.down}</span>
          <span>•</span>
          <#if topic.top == true>
            <span class="label label-primary">置顶</span>
            <span>•</span>
          <#elseif topic.good == true>
            <span class="label label-success">精华</span>
            <span>•</span>
          </#if>
          <span><a href="/user/${topicUser.username!}">${topicUser.username!}</a></span>
          <span>•</span>
          <span>${model.formatDate(topic.inTime)}</span>
          <span>•</span>
          <span>${topic.view!1}次点击</span>
          <#if user?? && topic.userId == user.id>
            <span>•</span>
            <span><a href="/topic/edit?id=${topic.id}">编辑</a></span>
            <span>•</span>
            <span><a href="javascript:if(confirm('确定要删除吗？'))location.href='/topic/delete?id=${topic.id}'">删除</a></span>
          </#if>
        </p>
      </div>
      <div class="media-right">
        <img src="${topicUser.avatar}" class="avatar-lg"/>
      </div>
    </div>
  </div>
    <div class="divide"></div>
    <div class="panel-body topic-detail-content show_big_image">
      ${model.formatContent(topic.content)}
      <div>
        <#list tags as tag>
          <a href="/topic/tag/${tag.name}"><span class="label label-success">${tag.name}</span></a>
        </#list>
      </div>
    </div>
  <#if user??>
    <div class="panel-footer">
      <a href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.baseUrl!}/topic/${topic.id}?r=${user.username!}&title=${topic.title!?html}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
      <#if collect??>
        <a href="javascript:;" class="collectTopic">取消收藏</a>
      <#else>
        <a href="javascript:;" class="collectTopic">加入收藏</a>
      </#if>
      <span class="pull-right"><span id="collectCount">${collectCount!0}</span>个收藏</span>
    </div>
  </#if>
</div>
<#if topic.commentCount == 0>
  <div class="panel panel-default">
    <div class="panel-body text-center">目前暂无评论</div>
  </div>
<#else>
  <div class="panel panel-default">
    <div class="panel-heading">${topic.commentCount} 条评论</div>
    <div class="panel-body paginate-bot panel-body-comment">
      <#include "../components/comments.ftl"/>
      <@comment id=topic.id topic_user=topicUser/>
    </div>
  </div>
</#if>
<#if user??>
  <div class="panel panel-default">
    <div class="panel-heading">
      添加一条新评论
      <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
    </div>
    <div class="panel-body">
      <#if !user.getBlock()>
        <#include "../components/editor.ftl"/>
        <@editor height="200px"/>
        <button id="btn" class="btn btn-sm btn-default">
          <span class="glyphicon glyphicon-send"></span> 评论
        </button>
      <#else>
        <div class="text-center">你的帐户被禁用了，不能评论</div>
      </#if>
    </div>
  </div>
</#if>
<#include "../components/show_big_image.ftl"/>
<script>
  $(function() {
    $("#btn").click(function() {
      var contentHtml = editor.txt.html();
      var contentText = editor.txt.text();
      if(!contentText) {
        toast("评论内容不能为空");
      } else {
        $.ajax({
          url: '/api/comment/save',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            topicId: '${topic.id}',
            commentId: $("#commentId").val(),
            content: contentHtml
          },
          success: function(data){
            if(data.code === 200) {
              window.location.href = "/topic/${topic.id}";
            } else {
              toast(data.description);
            }
          },
          error: function() {
            toast('服务器出错了', 'error');
          }
        })
      }
    });
    $(".collectTopic").click(function() {
      var txt = $(this).text();
      var url = '/api/collect/delete?topicId=${topic.id}';
      if(txt === '加入收藏') {
        url = '/api/collect/add?topicId=${topic.id}';
      }
      $.ajax({
        url: url,
        type: 'get',
        async: false,
        cache: false,
        dataType: 'json',
        data: {},
        success: function(data){
          if(data.code === 200) {
            if(txt === '加入收藏') {
              toast('收藏成功');
              $(".collectTopic").text('取消收藏');
            } else {
              toast('取消成功');
              $(".collectTopic").text('加入收藏');
            }
            $("#collectCount").text(data.detail);
          } else {
            toast(data.description);
          }
        }
      })
    })
  });
  function voteTopic(dom, id, action) {
    $.ajax({
      url: '/api/topic/' + id + '/vote',
      async: true,
      cache: false,
      type: "get",
      dataType: "json",
      data: {
        action: action
      },
      success: function (data) {
        if (data.code === 200) {
          $("#up_down_vote_count_" + id).text(data.detail.vote);
          if ($(dom).hasClass("up-down-enable")) {
            $(dom).removeClass("up-down-enable");
            $(dom).addClass("up-down-disable");
          } else if ($(dom).hasClass("up-down-disable")) {
            $(dom).removeClass("up-down-disable");
            $(dom).addClass("up-down-enable");
          }
        } else {
          toast(data.description);
        }
      }
    });
  }
</script>
</@html>