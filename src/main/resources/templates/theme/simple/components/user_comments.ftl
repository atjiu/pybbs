<#macro user_comments username pageNo pageSize paginate=false>
  <@tag_user_comments username=username pageNo=pageNo pageSize=pageSize>
    <ul>
      <#list comments.records as comment>
        <li>
          <div>
            <a href="/user/${comment.commentUsername}">${comment.commentUsername}</a>
            ${model.formatDate(comment.inTime)!}
            评论了
            <a href="/user/${comment.topicUsername}">${comment.topicUsername}</a>
            创建的话题 › <a href="/topic/${comment.topicId}">${comment.title!?html}</a>
          </div>
          <div class="content" style=" margin: 10px 0;">
            ${model.formatContent(comment.content)}
          </div>
        </li>
      </#list>
    </ul>
    <#if paginate && comments.current &lt; comments.pages>
      <a href="/user/${username}/comments?pageNo=${comments.current + 1}">查看更多</a>
    </#if>
  </@tag_user_comments>
</#macro>
