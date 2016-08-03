<#include "../common/layout.ftl"/>
<@html page_title="${pageTitle!}" page_tab="user">
    <div class="row">
        <div class="col-md-9">
            <#if currentUser??>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img src="${currentUser.avatar}" class="avatar-lg" alt=""/>
                            </div>
                            <div class="media-body">
                                <h3 style="margin-top: 0">${currentUser.nickname!}</h3>
                                <#if currentUser.signature??>
                                    <p><i class="gray">${currentUser.signature!}</i></p>
                                </#if>
                                <div>收藏话题: <a href="/user/collects/${currentUser.nickname!}">${currentUser.collectCount!0}</a></div>
                                <div>积分: ${currentUser.score!0}</div>
                                <div>主页: <a href="${currentUser.url!}" target="_blank">${currentUser.url!}</a></div>
                                <div>入驻时间: ${currentUser.in_time!}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">${currentUser.nickname}创建的话题</div>
                    <div class="panel-body">
                        <#include "../components/usertopics.ftl"/>
                        <@usertopics topics=topicPage.getList()/>
                    </div>
                    <div class="panel-footer">
                        <a href="/user/topics/${currentUser.nickname!}">${currentUser.nickname!}更多话题&gt;&gt;</a>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">${currentUser.nickname}回复的话题</div>
                    <#include "../components/userreplies.ftl"/>
                    <@userreplies replies=replyPage.getList()/>
                    <div class="panel-footer">
                        <a href="/user/replies/${currentUser.nickname!}">${currentUser.nickname!}更多回复&gt;&gt;</a>
                    </div>
                </div>
            <#else>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>用户未找到</p>
                        <a href="/">返回首页</a>
                    </div>
                </div>
            </#if>
        </div>
        <div class="col-md-3 hidden-sm hidden-xs">
            <#if userinfo??>
                <#include "../components/userinfo.ftl">
                <@info/>
                <#include "../components/createtopic.ftl">
                <@createtopic/>
            </#if>
        </div>
    </div>
</@html>