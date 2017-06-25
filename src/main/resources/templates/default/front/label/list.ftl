<#include "../common/layout.ftl"/>
<@html page_title="标签" page_tab="label">
<div class="row">
  <div class="col-md-9">
    <@labels_tag p=p>
      <div class="panel panel-default">
        <div class="panel-heading">
          <a href="/">主页</a> / 标签
        </div>
        <div class="panel-body">
          <div class="row">
            <#list page.getContent() as label>
              <div class="col-md-6">
                <div class="media" style="padding: 0 0 10px 0;">
                  <div class="media-left">
                    <#if label.image??>
                      <img src="${label.image!}" class="label-image">
                    <#else>
                      <div class="topic-label">
                        <span class="glyphicon glyphicon-tags"></span>
                      </div>
                    </#if>
                  </div>
                  <div class="media-body">
                    <div class="title">
                      <a href="/label/${label.name}/topic">${label.name}</a>
                      <small>${label.topicCount!0}个话题</small>
                    </div>
                    <p class="gray label-intro">${label.intro!"&nbsp;"}</p>
                  </div>
                </div>
              </div>
            </#list>
          </div>
          <#include "../components/paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/label/list"/>
        </div>
      </div>
    </@labels_tag>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>