<#include "layout/layout.ftl"/>
<@html page_title="Top100" page_tab="">
  <b>Top100 积分排行</b>
  <@tag_score limit=100>
    <table border="0" style="width: 100%; margin-top: 10px;">
      <#list users as user>
        <tr>
          <td width="30">
            <img src="${user.avatar!}" style="vertical-align: middle;" width="24" alt="">
          </td>
          <td><a href="/user/${user.username}">${user.username}</a></td>
          <td align="right">${user.score!0}</td>
        </tr>
      </#list>
    </table>
  </@tag_score>
</@html>
