<#include "../../common/layout.ftl"/>
<@html page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="section"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <@spring.message "site.panel.header.admin.section.list"/>
        <#if _roles?seq_contains("section:add")>
          <a href="/admin/section/add" class="pull-right"><@spring.message "site.panel.header.admin.section.add"/></a>
        </#if>
      </div>
      <div class="table-responsive">
        <table class="table table-striped table-responsive">
          <tbody>
            <#list sections as section>
            <tr>
              <td>${section.id}</td>
              <td>${section.name!}</td>
              <td>
                <#if _roles?seq_contains("section:edit")>
                  <a href="/admin/section/${section.id}/edit" class="btn btn-xs btn-warning"><@spring.message "site.button.edit"/></>
                </#if>
                <#if _roles?seq_contains("section:delete")>
                  <a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.delete"/>')) location.href='/admin/section/${section.id}/delete'"
                     class="btn btn-xs btn-danger"><@spring.message "site.button.delete"/></a>
                </#if>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</@html>