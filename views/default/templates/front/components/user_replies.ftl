<#macro user_replies username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_replies_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${currentUser.username}评论的话题</div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无评论
      </div>
    <#else>
      <table class="table table-striped">
        <#list page.getContent() as comment>
          <tr>
            <td>
              ${model.formatDate(comment.inTime)!}
              评论了
              <a href="/user/${comment.user.username}">${comment.user.username}</a>
              创建的话题 › <a href="/topic/${comment.topic.id}">${comment.topic.title!?html}</a>
            </td>
          </tr>
          <tr class="user_replies">
            <td class="show_big_image">
              ${model.marked(comment.content!, true)}
            </td>
          </tr>
        </#list>
      </table>
      <#if isPaginate>
        <div class="panel-body" style="padding: 0 15px;">
          <#include "paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/replies" urlParas="" showdivide="no"/>
        </div>
      </#if>
      <#if isFooter || page.getTotalElements() &gt;= limit>
        <div class="panel-footer">
          <a href="/user/${currentUser.username}/replies">${currentUser.username}更多评论&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  <#include "../components/show_big_image.ftl"/>
  </@user_replies_tag>
</#macro>