<#macro topic_list topics>
    <#list topics as topic>
    <div class="media">
        <div class="media-left">
            <a href="${baseUrl!}/user/${topic.author_id}">
                <img src="${topic.avatar!}" alt="avatar" class="media-object avatar">
            </a>
        </div>
        <div class="media-body">
            <div class="media-heading item-title">
                <a href="${baseUrl!}/topic/${topic.id!}.html">${topic.title!}</a>
            </div>
            <p class="small-fade">
                <#if topic.top == 1>
                    <a class="node" href="javascript:;">置顶</a>
                <#elseif topic.good == 1>
                    <a class="node" href="${baseUrl!}/?tab=good">精华</a>
                <#else>
                    <a class="node" href="${baseUrl!}/?tab=${topic.tab!}">${topic.sectionName!}</a>
                </#if>
                • ${topic.reply_count!}个回复 • ${topic.view!}次浏览 • ${topic.formatDate(topic.last_reply_time)!}
            </p>
        </div>
        <#if topic.last_reply_author_avatar??>
            <div class="media-right">
                <a href="${baseUrl!}/user/${topic.last_reply_author_id}" title="最后回复">
                    <img src="${topic.last_reply_author_avatar}" width="25" style="border-radius: 25px;" />
                </a>
            </div>
        </#if>
    </div>
        <#if topic_has_next>
        <div class="divide"></div>
        </#if>
    </#list>
</#macro>