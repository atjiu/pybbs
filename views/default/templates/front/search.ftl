<#include "layout/" + layoutName/>
<@html page_tab="" page_title="搜索结果">
<div class="row">
  <div class="col-md-12">
    <div class="panel panel-default">
      <div class="panel-heading">搜索结果</div>
        <#include "components/search_result.ftl"/>
        <@searchresult topics=page.content/>
        <#include "./components/paginate.ftl"/>
        <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/search" urlParas="&keyword=${keyword!}" showdivide="no"/>
      </div>
  </div>
</div>
</@html>
