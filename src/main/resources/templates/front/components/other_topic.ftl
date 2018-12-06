<#macro other_topic userId topicId limit>
  <div class="panel panel-default">
    <div class="panel-heading">作者其它话题</div>
    <table class="table">
      <@tag_otherTopic userId=userId topicId=topicId limit=limit>
        <#list topics as topic>
          <tr>
            <td><a href="/topic/${topic.id}">${topic.title}</a></td>
          </tr>
        </#list>
      </@tag_otherTopic>
    </table>
  </div>
</#macro>
