<#include "../layout/" + layoutName/>
<@html page_title="标签 - ${site.name!}" page_tab="tags">
<div class="row">
  <div class="panel panel-default">
    <div class="panel-body">
      <div class="row">
        <#list page.content as tag>
          <div class="col-md-3" style="margin-bottom: 10px; padding-left: 10px;">
            <#if tag.logo??>
              <img src="${tag.logo}" width="24" class="sponsor-tag-img" alt="${tag.name!}">
            </#if>
            <a data-pjax href="/topic/tag/${tag.name}">
              <span class="label label-success">${tag.name!}</span>
            </a>
            <span class="text-muted">x ${tag.topicCount}</span>
            <small class="excerpt text-muted" style="display: block; margin-top: 10px;">${tag.intro!}</small>
          </div>
        </#list>
      </div>
      <#include "../components/paginate.ftl"/>
      <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/tags" urlParas=""/>
    </div>
  </div>
</div>
</@html>