<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="mission">
<section class="content-header">
    <h1>
        签到
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/mission"><i class="fa fa-tag"></i> 签到</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>用户</th>
                    <th>签到积分</th>
                    <th>总积分</th>
                    <th>时间</th>
                    <th>连续签到天数</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as mission>
                        <tr>
                            <td>${mission.nickname!}</td>
                            <td>${mission.score!}</td>
                            <td>${mission.totalScore!}</td>
                            <td>${mission.in_time!}</td>
                            <td>${mission.day!}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">总签到数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/mission/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>