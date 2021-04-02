<#include "../layout/layout.ftl"/>
<@html page_title=topic.title page_tab="">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/github.min.css">
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-body topic-detail-header">
                    <div class="media">
                        <div class="media-body">
                            <h2 class="topic-detail-title">
                                ${topic.title!?html}
                            </h2>
                            <p class="gray">
                                <#if _user??>
                                    <i id="vote_topic_icon_${topic.id}" style="font-size: 18px;"
                                       class="fa <#if model.getUpIds(topic.upIds)?seq_contains('${_user.id}')> fa-thumbs-up <#else> fa-thumbs-o-up </#if>"
                                       onclick="voteTopic('${topic.id}')"></i>
                                <#else>
                                    <i id="vote_topic_icon_${topic.id}" style="font-size: 18px;"
                                       class="fa fa-thumbs-o-up"
                                       onclick="voteTopic('${topic.id}')"></i>
                                </#if>
                                <span id="vote_topic_count_${topic.id}">${model.getUpIds(topic.upIds)?size}</span>
                                <span>•</span>
                                <#if topic.top == true>
                                    <span class="badge badge-info">置顶</span>
                                    <span>•</span>
                                <#elseif topic.good == true>
                                    <span class="badge badge-info">精华</span>
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
                            <img src="${topicUser.avatar!}" class="avatar avatar-lg"/>
                        </div>
                    </div>
                </div>
                <div class="divide"></div>
                <div class="card-body topic-detail-content">
                    <#if topic.style == "MD">
                        ${model.formatContent(topic.content)}
                    <#elseif topic.style == "RICH">
                        ${topic.content!}
                    </#if>
                    <div>
                        <#list tags as tag>
                            <a href="/topic/tag/${tag.name}"><span class="badge badge-info">${tag.name}</span></a>
                        </#list>
                    </div>
                </div>
                <#if _user??>
                    <div class="card-footer">
                        <a href="javascript:window.open('http://service.weibo.com/share/share.php?url=${site.base_url!}/topic/${topic.id}?r=${_user.username!}&title=${topic.title!?html}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
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
            <@topic_comments topicId=topic.id />

            <#if _user??>
                <div class="card">
                    <div class="card-header">
                        添加一条新评论
                        <span class="pull-right">
                            <#if site?? && site.content_style?? && site.content_style == "MD">
                                <a href="javascript:uploadFile('topic');">上传图片</a>&nbsp;|
                                <a href="javascript:uploadFile('video');">上传视频</a>&nbsp;|
                            </#if>
                            <a href="javascript:;" id="goTop">回到顶部</a>
                        </span>
                    </div>
                    <input type="hidden" name="commentId" id="commentId" value=""/>
                    <#include "../components/editor.ftl"/>
                    <@editor _type="topic" style="${site.content_style!'MD'}"/>
                    <div class="card-body">
                        <button id="comment_btn" class="btn btn-sm btn-info">
                            <span class="glyphicon glyphicon-send"></span> 评论
                        </button>
                    </div>
                </div>
                <style>
                    .CodeMirror {
                        border-top: 0;
                        height: 150px !important;
                    }

                    .w-e-toolbar {
                        border: 0;
                    }

                    .w-e-text-container {
                        height: 200px !important;
                    }
                </style>
            <#else>
                <div class="card">
                    <div class="card-header">
                        添加一条新评论
                    </div>
                    <div class="card-body text-center">
                        <br>
                        登录后可以发表评论 <a href="/login" style="text-decoration: underline;">去登录</a>
                        <br>
                        <br>
                    </div>
                </div>
            </#if>
        </div>
        <div class="col-md-3 d-none d-md-block">
            <#include "../components/author.ftl"/>
            <#include "../components/other_topic.ftl"/>
            <@other_topic userId=topic.userId topicId=topic.id limit=7/>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
    <script>
        <#if _user??>
        $(function () {
            hljs.initHighlightingOnLoad();

            $("#comment_btn").click(function () {
                var content = window.editor ? window.editor.getDoc().getValue() : window._E.txt.html();
                if (!content) {
                    err("请输入评论内容");
                    return;
                }
                var _this = this;
                $(_this).button("loading");
                req("post", "/api/comment", {
                    topicId: '${topic.id}',
                    content: content,
                    commentId: $("#commentId").val(),
                }, "${_user.token!}", function (data) {
                    if (data.code === 200) {
                        suc("评论成功");
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                    } else {
                        err(data.description);
                        $(_this).button("reset");
                    }
                });
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
                req(type, "/api/collect/${topic.id}", "${_user.token!}", function (data) {
                    if (data.code === 200) {
                        if (text === "加入收藏") {
                            suc("收藏成功");
                            $(_this).text("取消收藏");
                            $("#collectCount").text(parseInt(collectCount) + 1);
                        } else if (text === "取消收藏") {
                            suc("取消收藏成功");
                            $(_this).text("加入收藏");
                            $("#collectCount").text(parseInt(collectCount) - 1);
                        }
                    } else {
                        err(data.description);
                    }
                });
            });
            // 删除话题
            $("#deleteTopic").click(function () {
                if (confirm("确定要删除吗？这会清空跟这个话题所有相关的数据，再考虑考虑呗！！")) {
                    req("delete", "/api/topic/${topic.id}", "${_user.token!}", function (data) {
                        if (data.code === 200) {
                            suc("删除成功");
                            setTimeout(function () {
                                window.location.href = "/";
                            }, 700);
                        } else {
                            err(data.description);
                        }
                    });
                }
            });
        });

        // 点赞话题
        function voteTopic(id) {
            req("get", "/api/topic/" + id + "/vote", "${_user.token!}", function (data) {
                if (data.code === 200) {
                    var voteTopicIcon = $("#vote_topic_icon_" + id);
                    if (voteTopicIcon.hasClass("fa-thumbs-up")) {
                        suc("取消点赞成功");
                        voteTopicIcon.removeClass("fa-thumbs-up");
                        voteTopicIcon.addClass("fa-thumbs-o-up");
                    } else {
                        suc("点赞成功");
                        voteTopicIcon.addClass("fa-thumbs-up");
                        voteTopicIcon.removeClass("fa-thumbs-o-up");
                    }
                    $("#vote_topic_count_" + id).text(data.detail);
                } else {
                    err(data.description);
                }
            });
        }

        // 删除评论
        function deleteComment(id) {
            if (confirm("确定要删除这个评论吗？删了就没有了哦！")) {
                req("delete", "/api/comment/" + id, "${_user.token!}", function (data) {
                    if (data.code === 200) {
                        suc("删除成功");
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                    } else {
                        err(data.description);
                    }
                });
            }
        }

        // 回复评论
        function commentThis(username, commentId) {
            $("#commentId").val(commentId);
            if (window.editor) {
                var oldContent = window.editor.getDoc().getValue();
                if (oldContent) oldContent += '\n';
                window.editor.getDoc().setValue(oldContent + "@" + username + " ");
                window.editor.focus();
                //定位到文档的最后一个字符的位置
                window.editor.setCursor(window.editor.lineCount(), 0);
            } else {
                if (window._E.txt.text()) {
                    window._E.txt.append('<a href="${site.baseUrl!}/user/' + username + '">@' + username + '</a>&nbsp;');
                } else {
                    window._E.txt.html('<a href="${site.baseUrl!}/user/' + username + '">@' + username + '</a>&nbsp;');
                }
            }
        }

        </#if>

        $("#goTop").click(function () {
            $('html, body').animate({scrollTop: 0}, 500);
        })
    </script>
</@html>
