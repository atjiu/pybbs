<#macro user_comments username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_comments_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${currentUser.username}评论的话题</div>
    <#if page.totalCount == 0>
      <div class="panel-body">
        暂无评论
      </div>
    <#else>
      <table class="table table-striped">
        <#list page.getContent() as comment>
          <tr>
            <td>
              ${model.formatDate(comment.in_time)!}
              评论了
              <a data-pjax href="/user/${comment.username}">${comment.username}</a>
              创建的话题 › <a data-pjax href="/topic/${comment.topic_id}">${comment.topic_title!?html}</a>
            </td>
          </tr>
          <tr class="user_comments">
            <td class="show_big_image">
              ${comment.content!}
            </td>
          </tr>
        </#list>
      </table>
      <#if isPaginate>
        <div class="panel-body" style="padding: 0 15px;">
          <#include "paginate.ftl"/>
        <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/user/${username}/comments" urlParas="" showdivide="no"/>
        </div>
      </#if>
      <#if isFooter || page.totalCount &gt;= limit>
        <div class="panel-footer">
          <a data-pjax href="/user/${currentUser.username}/comments">${currentUser.username}更多评论&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  <#include "../components/show_big_image.ftl"/>
  </@user_comments_tag>
</#macro>