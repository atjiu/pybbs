<#include "../layout/layout.ftl"/>
<@html page_title=topic.title page_tab="">
  <table style="width: 100%;">
    <tr>
      <td class="title">${topic.title!?html}</td>
      <td rowspan="2">
        <img src="${topicUser.avatar!}" class="pull-right" width="64" height="64"/>
      </td>
    </tr>
    <tr>
      <td style="font-size: 14px;">
        <#if _user??>
          <i id="vote_topic_icon_${topic.id}" style="font-size: 18px; cursor: pointer;"
             class="fa <#if model.getUpIds(topic.upIds)?seq_contains('${_user.id}')> fa-thumbs-up <#else> fa-thumbs-o-up </#if>"
             onclick="voteTopic('${topic.id}')"></i>
        <#else>
          <i id="vote_topic_icon_${topic.id}" style="font-size: 18px;" class="fa fa-thumbs-o-up"
             onclick="voteTopic('${topic.id}')"></i>
        </#if>
        <span id="vote_topic_count_${topic.id}">${model.getUpIds(topic.upIds)?size}</span>
        <span>•</span>
        <#if topic.top == true>
          <span class="label label-info">置顶</span>
          <span>•</span>
        <#elseif topic.good == true>
          <span class="label label-info">精华</span>
          <span>•</span>
        </#if>
        <span><a href="/user/${topicUser.username!}">${topicUser.username!}</a></span>
        <span>•</span>
        <span>${model.formatDate(topic.inTime)}</span>
        <span>•</span>
        <span>${topic.view!1}次点击</span>
        <span>•</span>
        <span>${topic.commentCount!0}条评论</span>
        <#if _user?? && topic.userId == _user.id>
          <span>•</span>
          <span><a href="/topic/edit/${topic.id}">编辑</a></span>
          <span>•</span>
          <span><a href="javascript:;" id="deleteTopic">删除</a></span>
        </#if>
      </td>
    </tr>
  </table>
  <hr>
  <div class="content">
    ${model.formatContent(topic.content)}
    <div>
      <#list tags as tag>
        <a href="/topic/tag/${tag.name}" class="tag">${tag.name}</a>
      </#list>
      <div style="margin-top: 15px; font-size: 14px;">
        <#if _user??>
          <a href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.base_url!}/topic/${topic.id}?r=${_user.username!}&title=${topic.title!?html}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
          <span class="pull-right"><span id="collectCount">${topic.collectCount!0}</span>个收藏</span>
          <span class="pull-right">
            <#if collect??>
              <a href="javascript:;" class="collectTopic">取消收藏</a>
            <#else>
              <a href="javascript:;" class="collectTopic">加入收藏</a>
            </#if>
            &nbsp;&nbsp;
          </span>
        </#if>
      </div>
    </div>
  </div>
  <hr>
  <#if _user??>
    <div>
      <div style="margin-bottom: 10px;">
        添加评论
        <a href="javascript:uploadImageBtn();" class="pull-right">上传图片</a>
      </div>
      <input type="hidden" name="commentId" id="commentId" value=""/>
      <textarea name="content" id="content" rows="4" placeholder="添加一条评论，支持Markdown语法"></textarea>
      <button id="comment_btn">评论</button>
    </div>
  <#else>
    <a href="/login">登录</a> 添加评论
  </#if>
  <hr>
<#--评论列表-->
  <@tag_topic_comments topicId=topic.id>
    <#list comments as comment>
      <div class="comments" style="margin-left: ${comment.layer * 25}px">
        <div class="top">
          <img width="24" style="vertical-align: middle;" src="${comment.avatar!}" alt="">
          <span style="font-size: 14px;">
            <a href="/user/${comment.username}">${comment.username}</a>
            ${model.formatDate(comment.inTime)}
            <span class="pull-right">
              <#if _user??>
                <i id="vote_icon_${comment.id}" class="fa
                                  <#if model.getUpIds(comment.upIds)?seq_contains('${_user.id}')> fa-thumbs-up <#else> fa-thumbs-o-up </#if>"
                   onclick="voteComment('${comment.id}')"></i>
              <#else>
                <i id="vote_icon_${comment.id}" class="fa fa-thumbs-o-up" onclick="voteComment('${comment.id}')"></i>
              </#if>
              <span id="vote_count_${comment.id}">${model.getUpIds(comment.upIds)?size}</span>&nbsp;
              <#if _user??>
                <#if _user.id == comment.userId>
                  <a href="/comment/edit/${comment.id}"><span class="fa fa-pencil-square-o"></span></a>
                  <a href="javascript:;" onclick="deleteComment(${comment.id})"><span
                        class="fa fa-trash-o"></span></a>
                </#if>
                <i class="fa fa-reply" onclick="commentThis('${comment.username}', '${comment.id}')"></i>
              </#if>
            </span>
          </span>
        </div>
        <div style="padding-left: 25px;">${model.formatContent(comment.content)}</div>
      </div>
    </#list>
  </@tag_topic_comments>
  <script>
    <#if _user??>
    $(function () {
      $("#comment_btn").click(function () {
        var content = $("#content").val();
        if (!content) {
          alert("请输入评论内容");
          return;
        }
        $(".loading").show();
        $.ajax({
          url: '/api/comment',
          type: 'post',
          cache: false,
          async: false,
          contentType: 'application/json',
          dataType: 'json',
          data: JSON.stringify({
            topicId: '${topic.id}',
            content: content,
            commentId: $("#commentId").val(),
          }),
          headers: {
            'token': '${_user.token}'
          },
          success: function (data) {
            if (data.code === 200) {
              window.location.reload();
            } else {
              alert(data.description);
            }
            $(".loading").hide();
          }
        })
      })

      $(".collectTopic").click(function () {
        var _this = this;
        var text = $(_this).text();
        var collectCount = $("#collectCount").text();
        var type = '';
        if (text === "加入收藏") {
          type = 'post';
        } else if (text === "取消收藏") {
          type = 'delete';
        }
        $.ajax({
          url: '/api/collect/${topic.id}',
          type: type,
          cache: false,
          async: false,
          dataType: 'json',
          contentType: 'application/json',
          headers: {
            "token": "${_user.token}",
          },
          success: function (data) {
            if (data.code === 200) {
              if (text === "加入收藏") {
                $(_this).text("取消收藏");
                $("#collectCount").text(parseInt(collectCount) + 1);
              } else if (text === "取消收藏") {
                $(_this).text("加入收藏");
                $("#collectCount").text(parseInt(collectCount) - 1);
              }
            } else {
              alert(data.description);
            }
          }
        })
      });
      // 删除话题
      $("#deleteTopic").click(function () {
        if (confirm("确定要删除吗？这会清空跟这个话题所有相关的数据，再考虑考虑呗！！")) {
          $.ajax({
            url: '/api/topic/${topic.id}',
            type: 'delete',
            cache: false,
            async: false,
            dataType: 'json',
            contentType: 'application/json',
            headers: {
              "token": "${_user.token}",
            },
            success: function (data) {
              if (data.code === 200) {
                window.location.href = "/";
              } else {
                alert(data.description);
              }
            }
          })
        }
      });
    });

    // 点赞话题
    function voteTopic(id) {
      $.ajax({
        url: '/api/topic/' + id + '/vote',
        type: 'get',
        cache: false,
        async: false,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
          'token': '${_user.token}'
        },
        success: function (data) {
          if (data.code === 200) {
            var voteTopicIcon = $("#vote_topic_icon_" + id);
            if (voteTopicIcon.hasClass("fa-thumbs-up")) {
              voteTopicIcon.removeClass("fa-thumbs-up");
              voteTopicIcon.addClass("fa-thumbs-o-up");
            } else {
              voteTopicIcon.addClass("fa-thumbs-up");
              voteTopicIcon.removeClass("fa-thumbs-o-up");
            }
            $("#vote_topic_count_" + id).text(data.detail);
          } else {
            alert(data.description);
          }
        }
      })
    }

    // 删除评论
    function deleteComment(id) {
      if (confirm("确定要删除这个评论吗？删了就没有了哦！")) {
        $.ajax({
          url: '/api/comment/' + id,
          cache: false,
          async: false,
          type: 'delete',
          dataType: 'json',
          contentType: 'application/json',
          headers: {
            'token': '${_user.token}'
          },
          success: function (data) {
            if (data.code === 200) {
              window.location.reload();
            } else {
              alert(data.description);
            }
          }
        })
      }
    }

    // 回复评论
    function commentThis(username, commentId) {
      $("#commentId").val(commentId);
      var oldContent = $("#content").val();
      if (oldContent) oldContent += '\n';
      $("#content").val(oldContent + "@" + username + " ");
      $("#content").focus();
    }

    // 点赞评论
    function voteComment(id) {
      $.ajax({
        url: '/api/comment/' + id + '/vote',
        cache: false,
        async: false,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
          'token': '${_user.token}'
        },
        success: function (data) {
          if (data.code === 200) {
            var voteIcon = $("#vote_icon_" + id);
            if (voteIcon.hasClass("fa-thumbs-up")) {
              voteIcon.removeClass("fa-thumbs-up");
              voteIcon.addClass("fa-thumbs-o-up");
            } else {
              voteIcon.addClass("fa-thumbs-up");
              voteIcon.removeClass("fa-thumbs-o-up");
            }
            $("#vote_count_" + id).text(data.detail);
          } else {
            alert(data.description);
          }
        }
      })
    }

    </#if>
    $("#goTop").click(function () {
      $('html, body').animate({scrollTop: 0}, 500);
    })
  </script>
  <#if _user??>
    <#include "../components/upload.ftl"/>
  </#if>
</@html>
