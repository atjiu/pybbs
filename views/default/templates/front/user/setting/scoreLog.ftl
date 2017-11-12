<#include "../../common/layout.ftl"/>
<@html page_title="个人积分记录" page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="scoreLog"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 个人积分记录
      </div>
      <div class="table-responsive">
        <table class="table table-striped">
          <tr>
            <th>ID</th>
            <th>详情</th>
            <th>积分变动</th>
            <th>剩余积分</th>
            <th>添加日期</th>
          </tr>
          <#list scoreLogs.getContent() as scoreLog>
            <tr>
              <td>${scoreLog.id!}</td>
              <td>${scoreLog.eventDescription!}</td>
              <td>${scoreLog.changeScore!}</td>
              <td>${scoreLog.score!}</td>
              <td>${model.formatDate(scoreLog.inTime)!}</td>
            </tr>
          </#list>
        </table>
      </div>
    </div>
  </div>
</div>
</@html>