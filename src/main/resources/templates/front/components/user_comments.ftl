<#macro user_comments isPaginate=false isFooter=false>
  <div class="panel panel-default">
    <div class="panel-heading">${user.username}评论的话题</div>
    <#if comments.total == 0>
      <div class="panel-body">
        暂无评论
      </div>
    <#else>
      <table class="table table-striped">
        <#list comments.records as comment>
          <tr>
            <td>
              <a href="/user/${comment.commentUsername}">${comment.commentUsername}</a>
              ${model.formatDate(comment.in_time)!}
              评论了
              <a href="/user/${comment.topicUsername}">${comment.topicUsername}</a>
              创建的话题 › <a href="/topic/${comment.topicId}">${comment.title!?html}</a>
            </td>
          </tr>
          <tr class="user_comments">
            <td>
              ${model.formatContent(comment.content)}
            </td>
          </tr>
        </#list>
      </table>
      <#if isPaginate>
        <div class="panel-body" style="padding: 0 15px;">
          <#include "./paginate.ftl"/>
          <@paginate currentPage=comments.current totalPage=comments.pages!1 actionUrl="/user/${user.username}/comments" urlParas=""/>
        </div>
      </#if>
      <#if isFooter>
        <div class="panel-footer">
          <a href="/user/${user.username}/comments">${user.username}更多评论&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
</#macro>
