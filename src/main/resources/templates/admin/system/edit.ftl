<#include "../layout/layout.ftl">
<@html page_title="系统设置" page_tab="system">
  <section class="content-header">
    <h1>
      系统
      <small>设置</small> <small class="text-danger">是数字的千万不要填成字母，请务必按照格式填写</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/system/edit">系统</a></li>
      <li class="active">设置</li>
    </ol>
  </section>
  <section class="content">
    <form id="form" onsubmit="return;">
      <#list systems?keys as key>
        <div class="form-group">
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">${key} </h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <#list systems[key] as system>
                <div>
                  <h5>${system.description!}</h5>
                  <input type="hidden" name="key" value="${system.key}" class="form-control"/>
                  <input type="text" id="${system.key!}" name="value" value="${system.value!}" class="form-control"/>
                </div>
              </#list>
            </div>
          </div>
        </div>
      </#list>
      <div class="form-group">
        <button type="button" onclick="save()" class="btn btn-primary">提交</button>
      </div>
    </form>
  </section>
<script>
  function save() {
    var search = $("#search").val();
    var es_host = $("input[id='elasticsearch.host']").val();
    var es_port = $("input[id='elasticsearch.port']").val();
    var es_index = $("input[id='elasticsearch.index']").val();
    if (search === "1" && (es_host.length === 0 || es_port.length === 0 || es_index.length === 0)) {
      toast("开启搜索功能却不配置ES服务，你是想让网站隔屁吗？");
    } else {
      $.post("/admin/system/edit", $("#form").serialize(), function (data) {
        if (data.code === 200) {
          toast("成功", "success");
          setTimeout(function () {
            window.location.reload();
          }, 700);
        } else {
          toast(data.description);
        }
      });
    }
  }
</script>
</@html>
