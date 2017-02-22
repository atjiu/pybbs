<#include "../../common/layout.ftl"/>
<@html page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="section"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.section.add"/></div>
      <div class="panel-body">
        <form action="/admin/section/add" method="post" id="sectionForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="name"><@spring.message "site.form.admin.section.name"/></label>
            <input type="text" id="name" name="name" class="form-control"/>
          </div>
          <button type="button" id="sectionBtn" onclick="sectionSubmit()" class="btn btn-sm btn-default"><@spring.message "site.button.save"/></button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  function sectionSubmit() {
    var errors = 0;
    var em = $("#error_message");
    var name = $("#name").val();
    if (name.length == 0) {
      errors++;
      em.html("<@spring.message "site.prompt.text.nameNotEmpty"/>");
    }
    if (errors == 0) {
      var form = $("#sectionForm");
      form.submit();
    }
  }
</script>
</@html>