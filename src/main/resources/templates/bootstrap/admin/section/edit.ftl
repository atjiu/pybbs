<#include "../common/layout.ftl"/>
<@html page_tab="system" page_title="编辑板块">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 编辑板块
      </div>
      <div class="panel-body">
        <form action="/section/edit" method="post" id="sectionForm">
          <input type="hidden" name="id" value="${section.id!}">
          <div class="form-group">
            <label for="name">名称</label>
            <input type="text" name="name" id="name" value="${section.name}" placeholder="名称" class="form-control">
          </div>
          <div class="form-group">
            <label for="tab">tab(不能与已经存在的tab重复)</label>
            <input type="text" name="tab" id="tab" value="${section.tab}" placeholder="tab" class="form-control">
          </div>
          <div class="form-group">
            <label>是否显示</label>
            <div>
              <input type="radio" name="showStatus" id="showStatus_1" value="1">
              <label for="showStatus_1">是&nbsp;</label>
              <input type="radio" name="showStatus" id="showStatus_0" value="0">
              <label for="showStatus_0">否</label>
            </div>
            <script type="text/javascript">
                <#if section.showStatus(section) == 'true'>
                $("#showStatus_1").attr("checked", true);
                <#else>
                $("#showStatus_0").attr("checked", true);
                </#if>
            </script>
          </div>
          <button onclick="saveSection()" id="saveSectionBtn" class="btn btn-sm btn-default">保存</button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>