<#macro othertopics>
<div class="panel panel-default">
  <div class="panel-heading"><@spring.message "site.panel.header.authorOtherTopic"/></div>
  <div class="panel-body">
    <#list otherTopics.getContent() as topic>
      <p><a href="/topic/${topic.id!}">${topic.title!}</a></p>
      <#if topic_has_next>
        <div class="divide pad-bot-10"></div>
      </#if>
    </#list>
  </div>
</div>
</#macro>