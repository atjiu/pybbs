<#macro create_topic>
<div class="panel panel-default">
  <#if sec.isAuthenticated() && !sec.isLock()>
    <a href="/topic/create" class="btn btn-default btn-block">发布话题</a>
  <#else>
    <button type="button" class="btn btn-default btn-block" disabled="disabled" data-toggle="tooltip"
            data-placement="bottom" title="你的帐户已经被禁用了">发布话题
    </button>
    <script>
      $(function () {
        $('[data-toggle="tooltip"]').tooltip()
      })
    </script>
  </#if>
</div>
</#macro>