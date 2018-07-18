<#macro user_comments username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_comments_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${currentUser.username}评论的话题</div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无评论
      </div>
    <#else>
      <table class="table table-striped">
        <#list page.getContent() as map>
          <tr>
            <td>
              ${model.formatDate(map.comment.inTime)!}
              评论了
              <a href="/user/${map.user.username}">${map.user.username}</a>
              创建的话题 › <a href="/topic/${map.topic.id}">${map.topic.title!?html}</a>
            </td>
          </tr>
          <tr class="user_comments">
            <td class="show_big_image">
              ${map.comment.content!}
            </td>
          </tr>
        </#list>
      </table>
      <#if isPaginate>
        <div class="panel-body" style="padding: 0 15px;">
          <#include "paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/comments" urlParas="" showdivide="no"/>
        </div>
      </#if>
      <#if isFooter || page.getTotalElements() &gt;= limit>
        <div class="panel-footer">
          <a href="/user/${currentUser.username}/comments">${currentUser.username}更多评论&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  <#include "../components/show_big_image.ftl"/>
  </@user_comments_tag>
</#macro>