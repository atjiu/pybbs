<#macro sidebar sidebar_user_info="" sidebar_topic_user="" sidebar_checkin="" sidebar_create="" sidebar_scoretop=""
    sidebar_create_info="" sidebar_about="" sidebar_other_topic=""
    sidebar_not_reply_topic="" sidebar_xgtopic="" sidebar_jfbbs_run_status="" sidebar_app="">
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
<#if sidebar_app == "show">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-phone"></span>
            <b>客户端二维码</b>
        </div>
        <div class="panel-body text-center">
            <img src="data:image/png;base64,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">
        </div>
    </div>
</#if>
</#macro>