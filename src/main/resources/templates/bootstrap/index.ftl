<#include "./common/layout.ftl">
<@html page_title="首页 - ${siteTitle!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <li <#if tab == '全部'>class="active"</#if>><a href="/?tab=全部">全部</a></li>
          <li <#if tab == '精华'>class="active"</#if>><a href="/?tab=精华">精华</a></li>
          <li <#if tab == '等待回复'>class="active"</#if>><a href="/?tab=等待回复">等待回复</a></li>
          <li class="dropdown <#if tab != '全部' && tab != '精华' && tab != '等待回复'>active</#if>"
              style="margin-right: 8px;">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)" data-target="#">
            ${sectionName!} <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <#list sections as section>
                <li>
                  <a href="/?tab=${section!}">${section!}</a>
                </li>
              </#list>
            </ul>
          </li>
        </ul>
      </div>
      <div class="panel-body paginate-bot">
        <#include "./components/topics.ftl"/>
        <@topics/>
        <#include "./components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/" urlParas="&tab=${tab!}"/>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if user??>
      <#include "components/user_info.ftl">
      <@info/>
      <#include "components/create_topic.ftl">
      <@create_topic/>
    <#else>
      <#include "./components/welcome.ftl">
      <@welcome/>
    </#if>
      <#--<#include "./components/qrcode.ftl"/>-->
    <#--<@qrcode/>-->
  </div>
</div>
</@html>