<#include "../layout/layout.ftl">
<@html page_title="系统设置" page_tab="system">
  <section class="content-header">
    <h1>
      系统
      <small>设置</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/system/edit">系统</a></li>
      <li class="active">设置</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">系统设置 <small class="text-danger">是数字的千万不要填成字母，请务必按照格式填写</small></h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form id="form" onsubmit="return;">
          <#list systems as system>
            <div class="form-group">
              <h4>${system.key} <small>${system.description!}</small></h4>
              <input type="hidden" name="key" value="${system.key}" class="form-control"/>
              <input type="text" name="value" value="${system.value}" class="form-control"/>
            </div>
          </#list>
          <button type="button" onclick="save()" class="btn btn-primary">提交</button>
        </form>
      </div>
    </div>
  </section>
<script>
  function save() {
    $.post("/admin/system/edit", $("#form").serialize(), function (data) {
      if (data.code === 200) {
        toast("成功", "success");
        setTimeout(function () {
          window.location.reload();
        }, 700);
      } else {
        toast(data.description);
      }
    })
  }
</script>
</@html>
