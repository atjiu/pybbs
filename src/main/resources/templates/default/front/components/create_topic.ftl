<#macro create_topic>
<div class="panel panel-default">
  <#if user?? && user.block == true>
    <a href="javascript:;" class="btn btn-default btn-block" disabled="disabled" title="你的帐户已经被禁用了">发布话题</a>
  <#else>
    <a href="/topic/create" class="btn btn-default btn-block">发布话题</a>
  </#if>
</div>
</#macro>