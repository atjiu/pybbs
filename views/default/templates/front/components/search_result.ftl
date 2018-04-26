<#macro searchresult topics>
  <#list topics as topic>
  <div class="media" style="position:relative;overflow: hidden;max-height:200px;">
    <div class="media-heading">
      <p style="font-size: 20px;"><b><a href="/topic/${topic.id!}" target="_blank" style="font-size: 16px;">${topic.title!}</a></b></p>
    </div>
    <div class="search-result">
      ${topic.content!}
    </div>
    <#if topic.content??><div class="more-box"></div></#if>
  </div>
  <#if topic_has_next><div class="divide" style="margin-top: 15px;"></div></#if>
  </#list>
</#macro>