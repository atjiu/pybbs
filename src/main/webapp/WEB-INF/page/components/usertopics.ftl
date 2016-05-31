<#macro usertopics topics>
    <#list topics as topic>
    <div class="media">
        <div class="media-body">
            <div class="title">
                <a href="/t/${topic.id!}">${topic.title!}</a>
            </div>
            <p>
                <a href="#">${topic.getNameByTab(topic.tab)}</a>
                <span>•</span>
                <span><a href="/user/${topic.author!}">${topic.author!}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.reply_count!0}个回复</span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.view!0}次浏览</span>
                <span>•</span>
                <span>${topic.formatDate(topic.in_time)}</span>
                <#if topic.last_reply_author?? && topic.last_reply_author != "">
                    <span>•</span>
                    <span>最后回复来自 <a href="#">${topic.last_reply_author!}</a></span>
                </#if>
            </p>
        </div>
    </div>
        <#if topic_has_next>
        <div class="divide mar-top-5"></div>
        </#if>
    </#list>
</#macro>