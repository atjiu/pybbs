<#macro user_replies username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_replies_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${currentUser.username}回复的话题</div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无回复
      </div>
    <#else>
      <table class="table table-striped">
        <#list page.getContent() as reply>
          <tr>
            <td>
              ${model.formatDate(reply.inTime)!}
              回复了
              <a href="/user/${reply.user.username}">${reply.user.username}</a>
              创建的话题 › <a href="/topic/${reply.topic.id}">${reply.topic.title!?html}</a>
            </td>
          </tr>
          <tr class="user_replies">
            <td class="show_big_image">
              ${model.marked(reply.content!, true)}
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
          <a href="/user/${currentUser.username}/replies">${currentUser.username}更多回复&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  <#include "../components/show_big_image.ftl"/>
  </@user_replies_tag>
</#macro>