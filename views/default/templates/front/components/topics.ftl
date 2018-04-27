<#macro topics topics>
  <#list topics as map>
    <div class="media">
      <div class="media-left">
        <a href="/user/${map.user.username!}"><img src="${map.user.avatar}" class="avatar" alt=""></a>
      </div>
      <div class="media-body">
        <div class="title">
          <#if model.isEmpty(map.topic.url)>
            <a href="/topic/${map.topic.id}">
              ${map.topic.title!?html}
            </a>
          <#else>
            <a href="${map.topic.url!?html}" target="_blank">
              ${map.topic.title!?html}
              <i class="glyphicon glyphicon-link"></i>
            </a>
          </#if>
        </div>
        <p class="gray">
          <#if (map.topic.up - map.topic.down) &gt; 0>
            <i class="fa fa-chevron-up"></i>
            <span>${map.topic.up - map.topic.down}</span>
            <span>•</span>
          </#if>
          <#if map.topic.top == true>
            <span class="label label-primary">置顶</span>
            <span>•</span>
          <#elseif map.topic.good == true>
            <span class="label label-success">精华</span>
            <span>•</span>
          </#if>
          <span><a href="/user/${map.user.username!}">${map.user.username!}</a></span>
          <span class="hidden-sm hidden-xs">•</span>
          <span class="hidden-sm hidden-xs"><a href="/topic/${map.topic.id!c}">${map.topic.commentCount!0}个评论</a></span>
          <span class="hidden-sm hidden-xs">•</span>
          <span class="hidden-sm hidden-xs">${map.topic.view!0}次浏览</span>
          <span>•</span>
          <span>${model.formatDate(map.topic.inTime)}</span>
          <span>•</span>
          <#list map.topic.tag?split(",") as tag>
            <a href="/topic/tag/${tag}"><span class="label label-success">${tag}</span></a>
          </#list>
        </p>
      </div>
    </div>
    <#if map_has_next>
      <div class="divide mar-top-5"></div>
    </#if>
  </#list>
</#macro>