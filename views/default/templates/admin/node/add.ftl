<#include "../common/layout.ftl"/>
<@html page_tab="admin" page_title="添加节点">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="node"/>
  </div>
  <div class="col-md-10">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 添加节点
      </div>
      <div class="panel-body">
        <form action="/admin/node/add" method="post" id="nodeForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="pid">父节点</label>
            <select name="pid" id="pid" class="form-control">
              <option value="0">父节点</option>
                <#list pnodes as pnode>
                  <option <#if pid?? && pid == pnode.id>selected</#if> value="${pnode.id!}">${pnode.name!}</option>
                </#list>
            </select>
          </div>
          <div class="form-group">
            <label for="name">名称</label>
            <input type="text" name="name" id="name" placeholder="名称" class="form-control">
          </div>
          <div class="form-group">
            <label for="value">值</label>
            <input type="text" name="value" id="value" placeholder="值" class="form-control">
          </div>
          <div class="form-group">
            <label for="intro">描述</label>
            <textarea name="intro" id="intro" placeholder="描述，1000字以内" class="form-control"></textarea>
          </div>
          <button type="submit" class="btn btn-sm btn-default">保存</button>
          <span id="error_message">${errorMessage!}</span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
<script type="text/javascript">
  $(function () {
    $("#nodeForm").submit(function () {
      var em = $("#error_message");
      var name = $("#name").val();
      var value = $("#value").val();
      var pid = $("#pid").val();
      if (name.length === 0) {
        em.html("名称不能为空");
        return false;
      }
      if(pid !== '0' && value.length === 0) {
        em.html("值不能为空");
        return false;
      }
    })
  })
</script>
</@html>