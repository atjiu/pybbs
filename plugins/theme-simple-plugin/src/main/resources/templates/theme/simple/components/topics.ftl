<#macro topics page>
  <table style="border: 0; width: 100%;" class="topics">
    <#list page.records as topic>
      <tr><td style="padding-top: 10px;"></td></tr>
      <tr>
        <td rowspan="2" width="50"><img src="${topic.avatar!}" width="48" style="vertical-align: middle;" alt=""></td>
        <td><a class="title" href="/topic/${topic.id}">${topic.title}</a></td>
      </tr>
      <tr>
        <td style="font-size: 14px;">
          <#if topic.top>
            <span class="tag">置顶</span>
          <#elseif topic.good == true>
            <span class="tag">精华</span>
          </#if>
          <span>${topic.view} 次点击</span>&nbsp;
          <a href="/user/${topic.username}">${topic.username}</a>&nbsp;
          <span>发布于 ${model.formatDate(topic.inTime)}</span>&nbsp;|
          <a href="/topic/${topic.id}">${topic.commentCount} 评论</a>&nbsp;
        </td>
      </tr>
      <tr>
        <td colspan="2" class="divide" <#if !topic_has_next>style="border: 0;"</#if>></td>
      </tr>
    </#list>
  </table>
</#macro>
