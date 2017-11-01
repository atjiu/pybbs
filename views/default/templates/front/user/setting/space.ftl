<#include "../../common/layout.ftl"/>
<@html page_title="个人空间" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="space"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        个人空间
        <span class="pull-right">
          <span class="text-warning">${count}</span> /
          <span class="text-danger">${user.spaceSize}</span>(MB)
        </span>
      </div>
      <div class="panel-body">
        <#list list as m>
          <p>${m.dirName}</p>
          <div class="row">
            <#list m.fileList as n>
              <div class="col-xs-6 col-md-3">
                <div class="thumbnail show_big_image">
                    <span class="file-delete" data-dirName="${m.dirName}"
                          data-fileName="${n.fileName}">&times;</span>
                  <img src="${n.fileUrl}">
                </div>
              </div>
            </#list>
          </div>
          <#if m_has_next>
            <div class="divide mar-bot-10"></div>
          </#if>
        </#list>
      </div>
    </div>
  </div>
</div>
<#include "../../components/show_big_image.ftl"/>
<script>
  $(function () {
    $(".file-delete").click(function () {
      if (confirm("确定要删除这张图片吗？有可能会导致话题或者回复里图片不能正常展示")) {
        var self = $(this);
        var dirName = $(this).attr('data-dirName');
        var fileName = $(this).attr('data-fileName');
        $.ajax({
          url: "/user/space/deleteFile",
          async: false,
          cache: false,
          type: "post",
          dataType: "json",
          data: {
            dirName: dirName,
            fileName: fileName
          },
          success: function (data) {
            if (data.code === 200) {
              self.parent().parent().remove();
            } else {
              alert(data.description);
            }
          }
        });
      }
    });
  })
</script>
</@html>