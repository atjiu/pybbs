<#include "common/layout.ftl"/>
<@html page_title="积分前100 - ${site.name!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / Top100 积分榜
      </div>

      <@score_tag p=1 limit=100>
        <table class="table">
          <tbody>
            <#list page.getContent() as user>
            <tr>
              <td rowspan="3" width="80">
                <img src="${user.avatar}" class="avatar-lg" alt="">
              </td>
              <td style="font-size: 20px;">${user_index + 1}. <a href="/user/${user.username}">${user.username}</a>
              </td>
              <td rowspan="3" style="font-size: 30px; width:70px; text-align: center;">
                    <span class="label label-default"
                          style="background-color: #f5f5f5;color:#666;">${user.score!0}</span>
              </td>
            </tr>
            <tr>
              <td>
                <i>
                  <#if user.bio??>
                    ${user.bio?html}
                  <#else>
                    "这家伙很懒，什么都没有留下"
                  </#if>
                </i>
              </td>
            </tr>
            <tr>
              <td class="gray">第 ${user.id} 号会员</td>
            </tr>
            </#list>
          </tbody>
        </table>
      </@score_tag>
    </div>

  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>