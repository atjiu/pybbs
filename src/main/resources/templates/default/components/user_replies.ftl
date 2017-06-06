<#macro user_replies username p=1 limit=site.pageSize isPaginate=false>
  <@user_replies_tag username=username p=p limit=limit>
    <#if page.getTotalElements() == 0>
    <div class="panel-body">
      暂无回复
    </div>
    <#else>
    <table class="table table-striped">
      <#list page.getContent() as reply>
        <tr>
          <td>
          ${reply.formatDate(reply.inTime)!}
            回复了
            <a href="/user/${reply.user.username}">${reply.user.username}</a>
            创建的话题 › <a href="/topic/${reply.topic.id}">${reply.topic.title}</a>
          </td>
        </tr>
        <tr class="user_replies">
          <td>
            <#if reply.editor?? && reply.editor  == 'markdown'>
          ${reply.marked(reply.content!)}
        <#elseif reply.editor?? && reply.editor  == 'wangeditor'>
            ${reply.content!}
            </#if>
          </td>
        </tr>
      </#list>
    </table>
      <#if isPaginate>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "./paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/replies" urlParas="" showdivide="no"/>
      </div>
      </#if>
    </#if>
  </@user_replies_tag>
</#macro>