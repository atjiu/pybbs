<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="index">
<section class="content-header">
    <h1>
        首页
        <small>概览</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li class="active">概览</li>
    </ol>
</section>
<section class="content">
    <div class="row">
        <div class="col-md-6">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">今日话题</h3>
                    <small>共${topics.size()}条</small>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tbody>
                            <#list topics as topic>
                            <tr>
                                <td><a href="${baseUrl!}/topic/${topic.id!}.html" target="_blank">${topic.title!}</a></td>
                                <td width="62">${topic.in_time?time}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">今日签到</h3>
                    <small>共${missions.size()}条</small>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>得分</th>
                            <th>总分</th>
                            <th>天数</th>
                            <th>时间</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list missions as mission>
                            <tr>
                                <td>${mission.nickname!}</td>
                                <td>${mission.score}</td>
                                <td>${mission.totalScore}</td>
                                <td>连续${mission.day}天</td>
                                <td width="62">${mission.in_time?time}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">今日用户</h3>
                    <small>共${users.size()}条</small>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>邮箱</th>
                            <th>QQ昵称</th>
                            <th>微博昵称</th>
                            <th>时间</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list users as user>
                            <tr>
                                <td>${user.nickname!}</td>
                                <td><a href="mailto:${user.email!}">${user.email}</a></td>
                                <td>${user.qq_nickname!}</td>
                                <td>${user.sina_nickname!}</td>
                                <td width="62">${user.in_time?time}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">今日回复</h3>
                    <small>共${replies.size()}条</small>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tbody>
                            <#list replies as reply>
                            <tr>
                                <td <#if reply.isdelete == 1> class="np-label-del" </#if>>
                                    <a href="javascript:;" data-toggle="modal" data-target="#reply_${reply.id!}">${reply.title!}</a>
                                    <div class="modal fade" id="reply_${reply.id!}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title" id="myModalLabel">${reply.title!}</h4>
                                                </div>
                                                <div class="modal-body">
                                                ${reply.content!}
                                                </div>
                                                <div class="modal-footer">
                                                    <a href="${baseUrl!}/topic/${reply.tid!}.html#${reply.id!}" target="_blank" class="btn btn-raised btn-default">查看</a>
                                                    <button type="button" class="btn btn-raised btn-default" data-dismiss="modal">关闭</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td width="62">${reply.in_time?time}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>
