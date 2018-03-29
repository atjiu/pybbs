<#include "../layout/layout.ftl">
<@html page_title="角色添加" page_tab="admin_user_role">
  <section class="content-header">
    <h1>
      角色
      <small>添加</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/role/list">角色</a></li>
      <li class="active">添加</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">角色添加</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <div class="row">
          <div class="col-sm-3">
            <div id="tree"></div>
          </div>
          <div class="col-sm-9">
            <form id="form">
              <div class="form-group">
                <label>角色名</label>
                <input type="text" id="name" class="form-control" placeholder="角色名">
              </div>
              <button type="submit" class="btn btn-sm btn-primary">保存</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
<script>
  var data = ${data!};
  var nodeIds = [];
  $(function() {
    var tree = $('#tree');
    tree.treeview({
      data: data,
      levels: 3,
      showCheckbox: true,
    });

    tree.on('nodeChecked', onNodeChecked);
    tree.on('nodeUnchecked', onNodeUnChecked);

    $("#form").submit(function() {
      nodeIds = [];
      getNodeIds(tree.treeview('getChecked'));
      var name = $("#name").val();
      if (!name) {
        toast('角色名不能为空');
        return false;
      }
      $.ajax({
        url: '/admin/role/add',
        async: true,
        cache: false,
        type: 'post',
        dataType: 'json',
        data: {
          name: name,
          nodeIds: nodeIds
        },
        success: function(data) {
          if (data.code === 200) {
            toast('添加成功');
            setTimeout(function() {
              window.location.href = "/admin/role/list";
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
      if (nodes) {
        first_index = node_data.nodeId;
        last_node = nodes[nodes.length - 1];
        while (last_node.nodes) {
          nodes = last_node.nodes;
          last_node = nodes[nodes.length - 1];
        }
        last_index = last_node.nodeId;
        range = [];
        for (i = first_index; i <= last_index; i++) {
          range.push(i);
        }
        return tree.treeview('checkNode', [range, {silent: true}]);
      }
    }

    function onNodeUnChecked(event, node_data) {
      var first_index, last_index, last_node, nodes, range, i;
      nodes = node_data.nodes;
      if (nodes) {
        first_index = node_data.nodeId;
        last_node = nodes[nodes.length - 1];
        while (last_node && last_node.nodes) {
          nodes = last_node.nodes;
          last_node = nodes[nodes.length - 1];
        }
        last_index = last_node.nodeId;
        range = [];
        for (i = first_index; i <= last_index; i++) {
          range.push(i);
        }
        return tree.treeview('uncheckNode', [range, {silent: true}]);
      }
    }

    function getNodeIds(nodes) {
      $.each(nodes, function(i, v) {
        if (!v.nodes) {
          nodeIds.push(v.id);
        }
      })
    }
  })
</script>
</@html>