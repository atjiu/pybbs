<#include "../layout/layout.ftl"/>
<@html page_title="标签" page_tab="tags">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body">
        <div class="row">
          <#list page.records as tag>
            <div class="col-md-3 tag-item">
              <#if tag.icon??>
                <img src="${tag.icon}" width="24" class="sponsor-tag-img" alt="${tag.name}">
              </#if>
              <a href="/topic/tag/${tag.name}">
                <span class="label label-success">${tag.name}</span>
              </a>
              <span class="text-muted">x ${tag.topicCount}</span>
              <small class="excerpt text-muted" style="">${tag.description!}</small>
            </div>
          </#list>
        </div>
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=page.current totalPage=page.pages!1 actionUrl="/tags"/>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <#if _user??>
      <#include "../components/user_info.ftl"/>
    <#else>
      <#include "../components/welcome.ftl"/>
    </#if>
  </div>
</div>
</@html>
