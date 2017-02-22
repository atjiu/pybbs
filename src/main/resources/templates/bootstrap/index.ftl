<#include "./common/layout.ftl">
<@html>
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <#--<#assign username=<@spring.message "site.tab.all"/>>-->
          <li <#if tab == tab_all>class="active"</#if>><a href="/?tab=<@spring.message "site.tab.all"/>"><@spring.message "site.tab.all"/></a></li>
          <li <#if tab == tab_good>class="active"</#if>><a href="/?tab=<@spring.message "site.tab.good"/>"><@spring.message "site.tab.good"/></a></li>
          <li <#if tab == tab_unanswered>class="active"</#if>><a href="/?tab=<@spring.message "site.tab.unanswered"/>"><@spring.message "site.tab.unanswered"/></a></li>
          <li class="dropdown <#if tab != tab_all && tab != tab_good && tab != tab_unanswered>active</#if>"
              style="margin-right: 8px;">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)" data-target="#">
            ${sectionName!} <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <#list sections as section>
                <li>
                  <a href="/?tab=${section.name!}">${section.name!}</a>
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