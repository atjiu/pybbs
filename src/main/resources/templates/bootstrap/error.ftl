<#include "common/layout.ftl"/>
<@html page_tab="" page_title="Ooooops, 出错了~~">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body">
        <h1>: (</h1>
        <h3>${errorCode}</h3>
        <p>${exception.message!}</p>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>
