<#include "../layout/layout.ftl">
<@html page_title="权限列表" page_tab="admin_user_permission">
  <section class="content-header">
    <h1>
      权限
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/permission/list">权限</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">权限列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <div class="row">
          <div class="col-sm-3">
            <div id="tree"></div>
          </div>
          <div class="col-sm-9">
            <form id="form">
              <input type="hidden" id="pid" value="0">
              <div class="form-group">
                <label>权限描述</label>
                <input type="text" id="name" placeholder="权限描述" class="form-control">
              </div>
              <div class="form-group">
                <label>权限标识</label>
                <input type="text" id="value" placeholder="权限标识" class="form-control">
              </div>
              <div class="form-group">
                <label>授权地址</label>
                <input type="text" id="url" placeholder="授权地址" class="form-control">
              </div>
              <#if sec.allGranted('permission:add')>
                <button type="submit" class="btn btn-sm btn-primary">保存</button>
              </#if>
              <#if sec.allGranted('permission:edit')>
                <button type="button" id="edit" class="btn btn-sm btn-warning">编辑</button>
              </#if>
              <#if sec.allGranted('permission:delete')>
                <button type="button" id="delete" class="btn btn-sm btn-danger">删除</button>
              </#if>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
<script>
  var data = ${data!};
  var node = {};
  $(function() {
    var tree = $('#tree');
    tree.treeview({
      data: data,
      showCheckbox: true,
    });

    tree.on('nodeChecked', onNodeChecked);
    tree.on('nodeUnchecked', onNodeUnChecked);
    tree.on('nodeSelected', function(event, node_data) {
      $("#pid").val(node_data.id);
      node = node_data
    });
    tree.on('nodeUnselected', function(event, node_data) {
      node = {};
    });

    $("#edit").click(function() {
      if(node && node.nodeId > 0) {
        $("#name").val(node.text);
        $("#value").val(node.value);
        $("#url").val(node.url);
      } else {
        $("#form")[0].reset();
      }
    });

    $("#delete").click(function() {
      if(node && node.nodeId > 0) {
        if(confirm('确定要删除这个节点吗？')) {
          $.ajax({
            url: '/admin/permission/delete',
            async: true,
            cache: false,
            type: 'get',
            dataType: 'json',
            data: {
              id: node.id
            },
            success: function(data) {
              if(data.code === 200) {
                toast('删除成功');
                setTimeout(function() {
                  window.location.reload();
                }, 1000);
              } else {
                toast(data.description);
              }
            }
          })
        }
      }
    })

    $("#form").submit(function() {
      var pid = $("#pid").val();
      var name = $("#name").val();
      var value = $("#value").val();
      var url = $("#url").val();
      if(!name) {
        toast('权限描述不能为空');
        return false;
      }
      if(!value) {
        toast('权限标识不能为空');
        return false;
      }
      if(!url) {
        toast('授权地址不能为空');
        return false;
      }
      $.ajax({
        url: '/admin/permission/add',
        async: true,
        cache: false,
        type: 'post',
        dataType: 'json',
        data: {
          pid: pid,
          name: name,
          value: value,
          url: url
        },
        success: function(data) {
          if(data.code === 200) {
            toast('添加成功');
            setTimeout(function() {
              window.location.reload();
            }, 1000);
          } else {
            toast(data.description);
          }
        }
      })
      return false;
    })

    function onNodeChecked(event, node_data) {
      var first_index, last_index, last_node, nodes, range, i;
      nodes = node_data.nodes;
      if (nodes != null) {
        first_index = node_data.nodeId;
        last_node = nodes[nodes.length - 1];
        while (last_node.nodes != null) {
          nodes = last_node.nodes;
          last_node = nodes[nodes.length - 1];
        }
        last_index = last_node.nodeId;
        range = [];
        for (i = first_index; i <= last_index ; i++) {
          range.push(i);
        }
        return tree.treeview('checkNode', [range, {silent: true}]);
      }
    }

    function onNodeUnChecked(event, node_data) {
      var first_index, last_index, last_node, nodes, range, i;
      nodes = node_data.nodes;
      if (nodes != null) {
        first_index = node_data.nodeId;
        last_node = nodes[nodes.length - 1];
        while (last_node.nodes != null) {
          nodes = last_node.nodes;
          last_node = nodes[nodes.length - 1];
        }
        last_index = last_node.nodeId;
        range = [];
        for (i = first_index; i <= last_index ; i++) {
          range.push(i);
        }
        return tree.treeview('uncheckNode', [range, {silent: true}]);
      }
    }
  })
</script>
</@html>