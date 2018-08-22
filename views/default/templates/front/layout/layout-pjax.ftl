<#macro html page_title page_tab="">
  <#nested />
  <script>
    (function(){
      document.title = '${page_title!site.name}';
      $(".nav>li").each(function (i,v) {
        $(v).removeClass("active");
      });
      $("li[data-tab='${page_tab}']").addClass("active");
    })();
  </script>
</#macro>