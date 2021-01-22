<#macro paginate currentPage totalPage actionUrl urlParas="" showdivide="yes">
    <#if (totalPage <= 0) || (currentPage > totalPage)><#return></#if>
    <#local startPage = currentPage - 2>
    <#if (startPage < 1)><#local startPage = 1></#if>

    <#local endPage = currentPage + 2>
    <#if (endPage > totalPage)><#local endPage = totalPage></#if>
    <#if totalPage &gt; 1 && showdivide == "yes">
        <div class="divide mt-3 mb-3"></div>
    </#if>
    <nav class="Page navigation">
        <ul class="pagination mb-0 <#if totalPage == 1>d-none</#if>">
            <#if (currentPage <= 3)>
                <#local startPage = 1>
            </#if>
            <#if ((totalPage - currentPage) < 2)>
                <#local endPage = totalPage>
            </#if>

            <#if (currentPage == 1)>
                <!--<li>上页</li>-->
            <#else>
                <li class="page-item"><a class="page-link" href="${actionUrl}?pageNo=1${urlParas!}">&lt;&lt;</a></li>
                <li class="page-item"><a class="page-link" href="${actionUrl}?pageNo=#{currentPage - 1}${urlParas!}">&lt;</a>
                </li>
            </#if>

            <#list startPage..endPage as i>
                <#if currentPage == i>
                    <li class="page-item active"><a class="page-link">#{i}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="${actionUrl}?pageNo=#{i}${urlParas!}">#{i}</a></li>
                </#if>
            </#list>

            <#if (currentPage == totalPage)>
                <!--<li>下页</li>-->
            <#else>
                <li class="page-item"><a class="page-link" href="${actionUrl}?pageNo=#{currentPage + 1}${urlParas!}">&gt;</a>
                </li>
                <li class="page-item"><a class="page-link" href="${actionUrl}?pageNo=#{totalPage}${urlParas!}">&gt;&gt;</a>
                </li>
            </#if>
        </ul>
    </nav>
</#macro>
