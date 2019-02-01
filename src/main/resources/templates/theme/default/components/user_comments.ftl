<#macro user_comments pageNo pageSize username isPaginate=false isFooter=false>
  <div class="panel panel-info">
    <@tag_user_comments username=username pageNo=pageNo pageSize=pageSize>
      <div class="panel-heading">${username}评论的话题</div>
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
                ${model.formatDate(comment.inTime)!}
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
            <#include "paginate.ftl"/>
            <@paginate currentPage=comments.current totalPage=comments.pages actionUrl="/user/${username}/comments" urlParas=""/>
          </div>
        </#if>
        <#if isFooter>
          <div class="panel-footer">
            <a href="/user/${username}/comments">${username}更多评论&gt;&gt;</a>
          </div>
        </#if>
      </#if>
    </@tag_user_comments>
  </div>
</#macro>
