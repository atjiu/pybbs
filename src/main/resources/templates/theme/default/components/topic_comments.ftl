<#macro topic_comments topicId>
    <@tag_topic_comments topicId=topicId>
        <#if comments?size == 0>
            <div class="nocomment-tip">目前还没有评论</div>
        <#else>
            <div class="card comments">
                <div class="card-header">共 ${comments?size} 条评论</div>
                <div class="card-body">
                    <#list comments as comment>
                        <div class="media" id="comment${comment.id}"
                             style="padding-left: ${comment.layer * 30}px;">
                            <div class="media-body">
                                <div class="gray d-flex justify-content-between">
                                    <div>
                                        <a href="/user/${comment.username}">
                                            <img src="${comment.avatar!}" class="avatar avatar-sm" alt=""/>
                                        </a>
                                        <a href="/user/${comment.username!}">${comment.username!} </a>
                                        <#if topicUser?? && topicUser.id == comment.userId>
                                            <span class="text-success">[楼主]</span>
                                        </#if>
                                        ${model.formatDate(comment.inTime)}
                                    </div>
                                    <div>
                                        <#if _user??>
                                            <i id="vote_icon_${comment.id}" class="fa
                                              <#if model.getUpIds(comment.upIds)?seq_contains('${_user.id}')> fa-thumbs-up <#else> fa-thumbs-o-up </#if>"
                                               onclick="vote('${comment.id}')"></i>
                                        <#else>
                                            <i id="vote_icon_${comment.id}" class="fa fa-thumbs-o-up"
                                               onclick="vote('${comment.id}')"></i>
                                        </#if>
                                        <span id="vote_count_${comment.id}">${model.getUpIds(comment.upIds)?size}</span>&nbsp;
                                        <#if _user??>
                                            <#if _user.id == comment.userId>
                                                <a href="/comment/edit/${comment.id}"><span
                                                            class="fa fa-pencil-square-o"></span></a>
                                                <a href="javascript:;" onclick="deleteComment(${comment.id})">
                                                    <span class="fa fa-trash-o"></span>
                                                </a>
                                            </#if>
                                            <i class="fa fa-reply"
                                               onclick="commentThis('${comment.username}', '${comment.id}')"></i>
                                        </#if>
                                    </div>
                                </div>
                                <div class="comment-detail-content ml-4 mt-3">
                                    <#if comment.style == 'MD'>
                                        ${model.formatContent(comment.content)}
                                    <#elseif comment.style == 'RICH'>
                                        ${comment.content!}
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <#if comment?has_next>
                            <div class="divide"></div>
                        </#if>
                    </#list>
                    <#if _user??>
                        <script>
                            function vote(id) {
                                req("get", "/api/comment/" + id + "/vote", "${_user.token!}", function (data) {
                                    if (data.code === 200) {
                                        var voteIcon = $("#vote_icon_" + id);
                                        if (voteIcon.hasClass("fa-thumbs-up")) {
                                            suc("取消点赞成功");
                                            voteIcon.removeClass("fa-thumbs-up");
                                            voteIcon.addClass("fa-thumbs-o-up");
                                        } else {
                                            suc("点赞成功");
                                            voteIcon.addClass("fa-thumbs-up");
                                            voteIcon.removeClass("fa-thumbs-o-up");
                                        }
                                        $("#vote_count_" + id).text(data.detail);
                                    } else {
                                        err(data.description);
                                    }
                                });
                            }
                        </script>
                    </#if>
                </div>
            </div>
        </#if>
    </@tag_topic_comments>
</#macro>
