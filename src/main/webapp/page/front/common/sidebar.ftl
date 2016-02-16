<#macro sidebar sidebar_user_info="" sidebar_topic_user="" sidebar_checkin="" sidebar_create="" sidebar_scoretop=""
    sidebar_create_info="" sidebar_about="" sidebar_other_topic=""
    sidebar_not_reply_topic="" sidebar_xgtopic="" sidebar_jfbbs_run_status="">
<#if sidebar_user_info == "show">
<div class="panel panel-default">
    <#if session.user??>
        <div class="panel-heading">
            <span class="glyphicon glyphicon-user"></span>
            <b>个人信息</b>
        </div>
        <div class="panel-body">
            <div class="media">
                <div class="media-left">
                    <a href="${baseUrl!}/user/${session.user.id!}" style="text-decoration: none;">
                        <img src="${session.user.avatar!}" title="${session.user.nickname!}" class="avatar">
                    </a>
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <a href="${baseUrl!}/user/${session.user.id!}">${session.user.nickname!}</a>
                    </div>
                    <p>积分: ${session.user.score!}</p>
                </div>
                <#if session.user.signature?? && session.user.signature != "">
                    <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
                        <i>“ ${session.user.signature!} ” </i>
                    </div>
                </#if>
            </div>
        </div>
    <#else>
        <div class="panel-body">
            <h5>属于Java语言的bbs</h5>
            <p>在这里，您可以提问，回答，分享，诉说，这是个属于Java程序员的社区，欢迎您的加入！</p>
        </div>
    </#if>
</div>
</#if>
<#if sidebar_topic_user == "show">
<div class="panel panel-default">
    <div class="panel-heading">
        <span class="glyphicon glyphicon-user"></span>
        <b>作者</b>
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="${baseUrl!}/user/${topic.author_id!}" style="text-decoration: none;">
                    <img src="${topic.avatar!}" title="${topic.nickname!}" class="avatar">
                </a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <a href="${baseUrl!}/user/${topic.author_id!}">${topic.nickname!}</a>
                </div>
                <p>积分: ${topic.score!}</p>
            </div>
            <#if topic.signature?? && topic.signature != "">
                <div style="margin-top:5px;" class="signature">
                    <i>“ ${topic.signature!} ” </i>
                </div>
            </#if>
        </div>
    </div>
</div>
</#if>
<#--签到-->
<#if sidebar_checkin == "show">
    <#if session.user?? && session.user.mission?date != session.today?date>
    <div class="panel panel-default">
        <div class="panel-body">
            <a href="${baseUrl!}/mission/daily" class="btn btn-raised btn-default ">领取今日的登录奖励</a>
        </div>
    </div>
    </#if>
</#if>
<#if sidebar_create == "show">
    <#if session.user??>
        <div class="panel panel-default">
            <div class="panel-body">
                <a href="${baseUrl!}/topic/create" class="btn btn-raised btn-default ">&nbsp;发布话题&nbsp;</a>
            </div>
        </div>
    </#if>
</#if>
<#if sidebar_not_reply_topic == "show">
    <#if notReplyTopics.size() &gt; 0>
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-th-list"></span>
            <b>等待回复</b>
        </div>
        <div class="panel-body">
            <#list notReplyTopics as topic>
                <div class="ellipsis-1 media">
                    <span class="dgray">• </span>
                    <a href="${baseUrl!}/topic/${topic.id!}.html">${topic.title!}</a>
                </div>
                <#if topic_has_next><div class="divide"></div></#if>
            </#list>
        </div>
    </div>
    </#if>
</#if>
<#if sidebar_xgtopic == "show">
    <#if xgTopics?? && xgTopics.size() &gt; 0>
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-th-list"></span>
            <b>相关话题</b>
        </div>
        <div class="panel-body">
            <#list xgTopics as topic>
                <div class="ellipsis-1 media">
                    <span class="dgray">• </span>
                    <a href="${baseUrl!}/topic/${topic.id!}.html">${topic.title!}</a>
                </div>
                <#if topic_has_next><div class="divide"></div></#if>
            </#list>
        </div>
    </div>
    </#if>
</#if>
<#if sidebar_scoretop == "show">
<div class="panel panel-default">
    <div class="panel-heading">
        <span class="glyphicon glyphicon-th-list"></span>
        <b>积分榜</b>&nbsp;&nbsp;<a href="${baseUrl!}/user/top100" class="small-gray">TOP100 &gt;&gt;</a>
    </div>
    <div class="panel-body">
        <#list scoreTopTen as top>
            <div class="media" style="margin-top: 5px;">
                <div class="media-body">
                    <div class="row">
                        <div class="col-sm-3">${top.score!}</div>
                        <div class="col-sm-9"><a href="${baseUrl!}/user/${top.id}">${top.nickname!}</a></div>
                    </div>
                </div>
            </div>
            <#if top_has_next><div class="divide"></div></#if>
        </#list>
    </div>
</div>
</#if>
<#if sidebar_create_info == "show">
    <#if session.user??>
        <div class="panel panel-default">
            <div class="panel-heading">
                <span class="glyphicon glyphicon-paperclip"></span>
                <b>话题发布指南</b>
            </div>
            <div class="panel-body">
                <p><span class="dgray">• </span>关于积分：发布话题奖励 3 积分，但是被管理员删除话题将会扣除作者 5 积分</p>
                <p><span class="dgray">• </span>问题标题: 请用准确的语言描述您发布的问题思想</p>
                <p><span class="dgray">• </span>添加标签: 添加一个或者多个合适的标签, 让您发布的问题得到更多有相同兴趣的人参与.</p>
                <p><span class="dgray">• </span>给话题选择合适的板块方便查找浏览</p>
            </div>
        </div>
    </#if>
</#if>
<#if sidebar_other_topic == "show">
    <#if otherTopics.size() &gt; 0>
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-th-list"></span>
            <b>作者其他话题</b>
        </div>
        <div class="panel-body">
            <#list otherTopics as topic>
                <div class="ellipsis-1 media">
                    <div class="media-body">
                        <span class="dgray">• </span>
                        <a href="${baseUrl!}/topic/${topic.id!}.html">${topic.title!}</a>
                    </div>
                </div>
                <#if topic_has_next><div class="divide"></div></#if>
            </#list>
        </div>
    </div>
    </#if>
</#if>
<#if sidebar_about == "show">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-paperclip"></span>
            <b>关于</b>
        </div>
        <div class="panel-body">
            <p>在这里你可以：</p>
            <p><span class="dgray">• </span>向别人提出你遇到的问题</p>
            <p><span class="dgray">• </span>帮助遇到问题的人</p>
            <p><span class="dgray">• </span>分享自己的知识</p>
            <p><span class="dgray">• </span>和其它人一起进步</p>
        </div>
    </div>
</#if>
<#if sidebar_jfbbs_run_status == "show">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-paperclip"></span>
            <b>社区运行状态</b>
        </div>
        <div class="panel-body">
            <div class="media">
                <div class="media-body">
                    <div class="row">
                        <div class="col-sm-5">注册用户</div>
                        <div class="col-sm-7">${userCount!0}</div>
                    </div>
                </div>
            </div>
            <div class="divide"></div>
            <div class="media">
                <div class="media-body">
                    <div class="row">
                        <div class="col-sm-5">话题</div>
                        <div class="col-sm-7">${topicCount!0}</div>
                    </div>
                </div>
            </div>
            <div class="divide"></div>
            <div class="media">
                <div class="media-body">
                    <div class="row">
                        <div class="col-sm-5">回复</div>
                        <div class="col-sm-7">${replyCount!0}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
</#macro>