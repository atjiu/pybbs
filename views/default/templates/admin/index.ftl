<#include "layout/layout.ftl">
<@html page_title="首页" page_tab="index">
  <section class="content-header">
    <h1>
     首页
      <small>仪表盘</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li class="active">仪表盘</li>
    </ol>
  </section>
  <section class="content">
    <p>欢迎登录${site.name}管理系统</p>

    <#if sec.allGranted("admin_index:clear")>
      <button class="btn btn-sm btn-danger" onclick="clearRedisData(1)">清除前台Redis数据</button>&nbsp;
      <button class="btn btn-sm btn-danger" onclick="clearRedisData(2)">清除后台Redis数据</button>&nbsp;
    </#if>
    <#if sec.allGranted("admin_index:indexedTopic")>
      <button class="btn btn-sm btn-danger" onclick="indexTopic()">索引话题</button>
    </#if>
    <#if sec.allGranted("admin_index:indexedTag")>
      <button class="btn btn-sm btn-danger" onclick="indexedTag()">索引标签</button>
    </#if>
  </section>
<script>
  function clearRedisData(type) {
    $.ajax({
      url: '/admin/clear',
      async: false,
      cache: true,
      type: 'get',
      dataType: 'json',
      data: {type: type},
      success: function (data) {
        if(data.code === 200) {
          toast("清除成功", 'success');
        } else {
          toast(data.description);
        }
      }
    })
  }

  function indexTopic() {
    $.ajax({
      url: '/admin/indexedTopic',
      async: false,
      cache: true,
      type: 'get',
      dataType: 'json',
      data: {},
      success: function (data) {
        if(data.code === 200) {
          toast("索引成功", 'success');
        } else {
          toast(data.description);
        }
      }
    })
  }

  function indexedTag() {
    $.ajax({
      url: '/admin/indexedTag',
      async: false,
      cache: true,
      type: 'get',
      dataType: 'json',
      data: {},
      success: function (data) {
        if(data.code === 200) {
          toast("索引成功", 'success');
        } else {
          toast(data.description);
        }
      }
    })
  }
</script>
</@html>