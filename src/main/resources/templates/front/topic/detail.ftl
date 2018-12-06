<#include "../layout/layout.ftl"/>
<@html page_title=topic.title page_tab="">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/github.min.css">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body topic-detail-header">
        <div class="media">
          <div class="media-body">
            <h2 class="topic-detail-title">
              ${topic.title!?html}
            </h2>
            <p class="gray">
            <#--<i id="up_icon_${topic.id}" class="fa fa-chevron-up
              <#if _user?? && topic.upIds?split(",")?seq_contains('${_user.id}')> up-down-enable <#else> up-down-disable </#if>"
                 onclick="voteTopic('UP')"></i>
              <i id="down_icon_${topic.id}" class="fa fa-chevron-down
              <#if _user?? && topic.downIds?split(",")?seq_contains('${_user.id}')> up-down-enable <#else> up-down-disable </#if>"
                 onclick="voteTopic('DOWN')"></i>
              <span id="up_down_vote_count_${topic.id}">${topic.up - topic.down}</span>
              <span>•</span>-->
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
              <#if _user?? && topic.userId == _user.id>
                <span>•</span>
                <span><a href="/topic/edit/${topic.id}">编辑</a></span>
                <span>•</span>
                <span><a href="javascript:;" id="deleteTopic">删除</a></span>
              </#if>
            </p>
          </div>
          <div class="media-right">
            <img src="${topicUser.avatar}" class="avatar avatar-lg"/>
          </div>
        </div>
      </div>
      <div class="divide"></div>
      <div class="panel-body topic-detail-content">
        ${model.formatContent(topic.content)}
        <div>
        <#list tags as tag>
          <a href="/topic/tag/${tag.name}"><span class="label label-success">${tag.name}</span></a>
        </#list>
        </div>
      </div>
      <#if _user??>
        <div class="panel-footer">
          <a href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.baseUrl!}/topic/${topic.id}?r=${_user.username!}&title=${topic.title!?html}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
          <#if collect??>
            <a href="javascript:;" class="collectTopic">取消收藏</a>
          <#else>
            <a href="javascript:;" class="collectTopic">加入收藏</a>
          </#if>
          <span class="pull-right"><span id="collectCount">${collects?size}</span>个收藏</span>
        </div>
      </#if>
    </div>

    <#--评论列表-->
    <#include "../components/topic_comments.ftl"/>

    <#if _user??>
      <div class="panel panel-default">
        <div class="panel-heading">
          添加一条新评论
          <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
        </div>
        <input type="hidden" name="commentId" id="commentId" value=""/>
        <textarea name="content" id="content" class="form-control" placeholder="添加一条评论，支持Markdown语法"></textarea>
        <div class="panel-body">
          <button id="comment_btn" class="btn btn-sm btn-default">
            <span class="glyphicon glyphicon-send"></span> 评论
          </button>
        </div>
      </div>
      <link href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/codemirror.min.css" rel="stylesheet">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/codemirror.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/mode/markdown/markdown.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/addon/display/placeholder.min.js"></script>
      <style>
        .CodeMirror {
          border-top: 0;
          height: 150px;
        }
      </style>
    </#if>
  </div>
  <div class="col-md-3">
    <#include "../components/author.ftl"/>
    <#include "../components/other_topic.ftl"/>
    <@other_topic userId=topic.userId topicId=topic.id limit=7/>
  </div>
</div>
<script>
  var editor;
  $(function () {
    CodeMirror.keyMap.default["Shift-Tab"] = "indentLess";
    CodeMirror.keyMap.default["Tab"] = "indentMore";
    editor = CodeMirror.fromTextArea(document.getElementById("content"), {
      lineNumbers: true,     // 显示行数
      indentUnit: 4,         // 缩进单位为4
      tabSize: 4,
      matchBrackets: true,   // 括号匹配
      mode: 'markdown',     // Markdown模式
      lineWrapping: true,    // 自动换行
    });

    $("#goTop").click(function () {
      $(window).scrollTo({top: 0}, 1500);
    })

    $("#comment_btn").click(function () {
      var content = editor.getDoc().getValue();
      if (!content) {
        toast("请输入评论内容");
        return;
      }
      $.post("/api/comment/create", {
        topicId: ${topic.id},
        content: content,
        commentId: $("#commentId").val()
      }, function (data) {
        if (data.code === 200) {
          toast("评论成功", "success");
          setTimeout(function () {
            window.location.reload();
          }, 700);
        } else {
          toast(data.description);
        }
      })
    })

    $(".collectTopic").click(function() {
      var _this = this;
      var text = $(_this).text();
      var collectCount = $("#collectCount").text();
      var url = "";
      if (text === "加入收藏") {
        url = "/api/collect/get";
      } else if (text === "取消收藏") {
        url = "/api/collect/delete";
      }
      $.get(url, { topicId: ${topic.id} }, function(data) {
        if (data.code === 200) {
          if (text === "加入收藏") {
            toast("收藏成功", "success");
            $(_this).text("取消收藏");
            $("#collectCount").text(parseInt(collectCount) + 1);
          } else if (text === "取消收藏") {
            toast("取消收藏成功", "success");
            $(_this).text("加入收藏");
            $("#collectCount").text(parseInt(collectCount) - 1);
          }
        } else {
          toast(data.description);
        }
      });
    });
    // 删除话题
    $("#deleteTopic").click(function () {
      if (confirm("确定要删除吗？这会清空跟这个话题所有相关的数据，再考虑考虑呗！！")) {
        $.get("/api/topic/delete", {id: ${topic.id} }, function (data) {
          if (data.code === 200) {
            toast("删除成功", "success");
            setTimeout(function () {
              window.location.href = "/";
            }, 700);
          } else {
            toast(data.description);
          }
        })
      }
    });
  });

  // 删除评论
  function deleteComment(id) {
    if(confirm("确定要删除这个评论吗？删了就没有了哦！")) {
      $.get("/api/comment/delete?id=" + id, function (data) {
        if (data.code === 200) {
          toast("删除成功", "success");
          setTimeout(function () {
            window.location.reload();
          }, 700);
        } else {
          toast(data.description);
        }
      })
    }
  }
  // 回复评论
  function commentThis(username, commentId) {
    $("#commentId").val(commentId);
    var oldContent = editor.getDoc().getValue();
    if (oldContent) oldContent += '\n';
    editor.getDoc().setValue(oldContent + "@" + username + " ");
    editor.focus();
    //定位到文档的最后一个字符的位置
    editor.setCursor(editor.lineCount(), 0);
  }
</script>
</@html>
