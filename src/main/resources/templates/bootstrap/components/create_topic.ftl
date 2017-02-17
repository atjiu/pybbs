<#macro create_topic>
<div class="panel panel-default">
  <#if user?? && user.block == true>
    <a href="javascript:;" class="btn btn-default btn-block" disabled="disabled" title="<@spring.message "site.prompt.cantCreateTopic"/>">发布话题</a>
  <#else>
    <a href="/topic/create" class="btn btn-default btn-block"><@spring.message "site.button.createTopic"/></a>
  </#if>
</div>
</#macro>