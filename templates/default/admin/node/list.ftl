<#include "../common/layout.ftl"/>
<@html page_tab="admin" page_title="节点管理">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="node"/>
  </div>
  <div class="col-md-9">
    <div class="row">
      <div class="col-md-9">
        <div class="panel panel-default">
          <div class="panel-heading">
            <a href="/">主页</a> / 节点列表
            <span class="pull-right"><a href="/admin/node/add?pid=${pid!}">添加节点</a></span>
          </div>
          <div class="table-responsive">
            <table class="table table-striped">
              <tr>
                <th>ID</th>
                <th>名称</th>
                <th>值</th>
                <th>添加日期</th>
                <th>操作</th>
              </tr>
              <#list nodes as node>
                <tr>
                  <td>${node.id!}</td>
                  <td>${node.name!}</td>
                  <td>${node.value!}</td>
                  <td>${model.formatDate(node.inTime)!}</td>
                  <td>
                    <a href="/admin/node/${node.id!}/edit" class="btn btn-xs btn-warning">编辑</a>
                    <a href="javascript:if(confirm('确定要删除吗？')) location.href='/admin/node/${node.id!}/delete'"
                       class="btn btn-xs btn-danger">删除</a>
                  </td>
                </tr>
              </#list>
            </table>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">父节点</div>
          <div class="list-group">
            <#list pnodes as node>
              <li class="list-group-item permission-item <#if pid?? && pid == node.id>active</#if>">
                <#if sec.allGranted("node:delete")>
                  <a href="javascript:if(confirm('确认删除吗?'))location.href='/admin/node/${node.id}/delete'">删除</a>
                </#if>
                <a href="/admin/node/list?pid=${node.id!}">
                  ${node.name!}
                </a>
              </li>
            </#list>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</@html>