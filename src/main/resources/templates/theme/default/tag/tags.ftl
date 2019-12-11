<#include "../layout/layout.ftl"/>
<@html page_title="标签" page_tab="tags">
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-body">
                    <@tag_tags pageNo=pageNo pageSize=40>
                        <div class="row" style="overflow: hidden;">
                            <#list page.records as tag>
                                <div class="col-md-3 tag-item">
                                    <#if tag.icon??>
                                        <img src="${tag.icon}" width="24" class="sponsor-tag-img" alt="${tag.name}">
                                    </#if>
                                    <a href="/topic/tag/${tag.name}">
                                        <span class="badge badge-info">${tag.name}</span>
                                    </a>
                                    <span class="text-muted">x ${tag.topicCount}</span>
                                    <small class="excerpt text-muted" style="">${tag.description!}</small>
                                </div>
                            </#list>
                        </div>
                        <#include "../components/paginate.ftl"/>
                        <@paginate currentPage=page.current totalPage=page.pages actionUrl="/tags"/>
                    </@tag_tags>
                </div>
            </div>
        </div>
        <div class="col-md-3 hidden-xs">
            <#if _user??>
                <#include "../components/user_info.ftl"/>
            <#else>
                <#include "../components/welcome.ftl"/>
            </#if>
        </div>
    </div>
</@html>
