<#include "../common/layout.ftl"/>
<@html page_title="标签管理" page_tab="admin">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="label"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        标签管理
      </div>
      <@labels_tag p=p>
        <table class="table table-striped">
          <thead>
          <tr>
            <th>名称</th>
            <th>关联话题</th>
            <th>创建时间</th>
            <th>编辑时间</th>
            <th>话题数量</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
            <#list page.getContent() as label>
            <tr <#if label.topicCount == 0>style="background-color: greenyellow"</#if>>
              <td>
                <a href="/label/${label.name!}" target="_blank">${label.name!}</a>
              </td>
              <td>
                <#if label.topicCount != 0>
                  <a href="/admin/label/${label.name}">关联话题</a>
                </#if>
              </td>
              <td>${model.formatDate(label.inTime)}</td>
              <td>${model.formatDate(label.modifyTime)}</td>
              <td>${label.topicCount!0}个话题</td>
              <td>
                <a href="/admin/label/${label.id}/edit" class="btn btn-xs btn-warning">编辑</a>
                <a href="javascript:deleteLabel(${label.id});" class="btn btn-xs btn-danger">删除</a>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </@labels_tag>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/amdin/label/list" showdivide="no"/>
      </div>
    </div>
  </div>
</div>
<script>
  function deleteLabel(id) {
    if (confirm("确定要删除这个标签吗？")) {
      $.ajax({
        url: "/admin/label/" + id + "/delete",
        async: false,
        cache: false,
        type: "get",
        dataType: "json",
        success: function (data) {
          if (data.code === 200) {
            location.reload();
          } else {
            alert(data.description);
          }
        }
      });
    }
  }
</script>
</@html>