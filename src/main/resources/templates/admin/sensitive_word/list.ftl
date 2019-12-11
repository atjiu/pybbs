<#include "../layout/layout.ftl">
<@html page_title="敏感词列表" page_tab="sensitive_word">
    <section class="content-header">
        <h1>
            敏感词
            <small>列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i>首页</a></li>
            <li><a href="/admin/sensitive_word/list">敏感词</a></li>
            <li class="active">列表</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">敏感词列表</h3>
                <#if sec.hasPermission('sensitive_word:add')>
                    <button type="button" class="btn btn-xs btn-danger pull-right" onclick="addSensitiveWord()">增加敏感词
                    </button>
                </#if>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form action="/admin/sensitive_word/list" class="form-inline">
                    <div class="form-group" style="margin-bottom: 10px;">
                        <input type="text" name="word" value="${word!}" class="form-control" placeholder="敏感词">
                        <button type="submit" class="btn btn-primary btn-sm">搜索</button>
                        <#if sec.hasPermission("sensitive_word:import")>
                            <button type="button" onclick="openExcel()" class="btn btn-primary btn-sm">导入</button>
                            <input type="file" accept="application/vnd.ms-excel" class="hidden" id="excelFileInput"
                                   onchange="excelChange()">
                        </#if>
                    </div>
                </form>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>敏感词</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list page.records as item>
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.word!}</td>
                            <td>
                                <#if sec.hasPermission('sensitive_word:edit')>
                                    <button onclick="editSensitiveWord(${item.id}, '${item.word!}')"
                                            class="btn btn-xs btn-warning">编辑
                                    </button>
                                </#if>
                                <#if sec.hasPermission('sensitive_word:delete')>
                                    <button onclick="deleteBtn('${item.id}')" class="btn btn-xs btn-danger">删除</button>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <#if sec.hasPermissionOr("sensitive_word:add", "sensitive_word:edit")>
            <button class="hidden" id="openModal" data-toggle="modal" data-target="#myModal"></button>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog modal-md" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">添加/编辑敏感词</h4>
                        </div>
                        <div class="modal-body">
                            <form onsubmit="return;" id="form">
                                <input type="hidden" name="id" id="id" value=""/>
                                <div class="from-group">
                                    <label for="name">敏感词</label>
                                    <input type="text" id="word" value="" class="form-control" placeholder="敏感词"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
                            <button type="button" onclick="saveSensitiveWord()" class="btn btn-primary">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
        <#include "../layout/paginate.ftl">
        <@paginate currentPage=page.current totalPage=page.pages actionUrl="/admin/sensitive_word/list" urlParas="&word=${word!}"/>
    </section>
    <script>
        <#if sec.hasPermission("sensitive_word:import")>

        function openExcel() {
            if (confirm('在导入过程中会处理重复的数据，所以如果数据量比较大，导入过程会较长，请耐心等待!')) {
                $("#excelFileInput").click();
            }
        }

        function excelChange() {
            var excelFileDom = document.getElementById("excelFileInput");
            var fd = new FormData();
            fd.append("file", excelFileDom.files[0]);
            $.post({
                url: "/admin/sensitive_word/import",
                data: fd,
                dataType: 'json',
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code === 200) {
                        toast("成功", "success");
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                    } else {
                        toast(data.description);
                    }
                }
            })
        }

        </#if>
        <#if sec.hasPermissionOr("sensitive_word:add", "sensitive_word:edit")>

        function addSensitiveWord() {
            $("#form")[0].reset();
            $("#openModal").click();
        }

        function editSensitiveWord(id, word) {
            $("#form")[0].reset();
            $("#id").val(id);
            $("#word").val(word);
            $("#openModal").click();
        }

        function saveSensitiveWord() {
            var id = $("#id").val();
            var word = $("#word").val();
            if (!word) {
                toast("请输入敏感词");
                return;
            }
            var url = '/admin/sensitive_word/add';
            if (id) {
                url = '/admin/sensitive_word/edit';
            }
            var data = {
                id: id,
                word: word
            }
            $.post(url, data, function (data) {
                if (data.code === 200) {
                    toast("成功", "success");
                    setTimeout(function () {
                        window.location.reload();
                    }, 700);
                } else {
                    toast(data.description);
                }
            })
        }

        </#if>
        <#if sec.hasPermission('sensitive_word:delete')>

        function deleteBtn(id) {
            if (confirm('确定要删除这个敏感词吗？')) {
                $.get("/admin/sensitive_word/delete?id=" + id, function (data) {
                    if (data.code === 200) {
                        toast("成功", "success");
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                    } else {
                        toast(data.description);
                    }
                })
            }
        }

        </#if>
    </script>
</@html>
