<#include "/page/front/common/_layout.ftl"/>
<@html title="编辑话题 - ${siteTitle!}" description="编辑话题" sidebar_create_info="show" page_tab="topic">
<link rel="stylesheet" href="${baseUrl!}/static/bootstrap/css/jquery-ui.css"/>
<link rel="stylesheet" href="${baseUrl!}/static/wangEditor/css/wangEditor.css">
<script src="${baseUrl!}/static/bootstrap/js/jquery-ui.js"></script>
<script src="${baseUrl!}/static/wangEditor/js/wangEditor.js"></script>

<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">编辑话题</li>
        </ol>
    </div>
    <div class="panel-body">
        <form id="create_form" action="${baseUrl!}/topic/update" method="post">
            <input type="hidden" name="tid" value="${topic.id!}"/>
            <div class="form-group label-floating">
                <select name="sid" id="sid" class="form-control" style="width: 30%; margin-bottom: 5px;">
                    <#list sections as section>
                        <option <#if topic.tab == '${section.tab!}'> selected </#if> value="${section.id!}">${section.name!}</option>
                    </#list>
                </select>
            </div>
            <div class="form-group label-floating">
                <div id="labels">
                    <#list labels as label>
                        <span class="label label-info label-item" name="${label.name!}">${label.name!}<a href="javascript:;" onclick="$(this).parents('.label').remove();">&nbsp;x</a></span>
                    </#list>
                </div>
                <input type="hidden" name="label" value=""/>
                <input type="text" class="form-control" id="label" style="width: 40%;margin-bottom: 5px; display: inline-block;" placeholder="标签"/>
                <input type="button" class="btn btn-raised btn-default " onclick="addLabel()" value="添加">
            </div>
            <div class="form-group label-floating">
                <input type="text" placeholder="标题字数10字以上" id="title" name="title" value="${topic.title!}" class="form-control" style="margin-bottom: 5px;"/>
            </div>
            <div class="form-group label-floating">
                <textarea id="temp_content" class="hidden">${topic.content}</textarea>
                <textarea id="content" name="content" class="form-control" style="height: 400px;"></textarea>
            </div>
            <div class="form-group label-floating">
                <input type="button" onclick="submitForm()" value="提  交" class="btn btn-raised btn-default ">
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function submitForm() {
        //将标签格式化放入隐藏表单里
        var labelName = '';
        $("#labels span").each(function (i, item) {
            labelName += $(this).attr("name") + ",";
        });
        $("input[name='label']").val(labelName);
        if($.trim($("#title").val()) == "") {
            alert("标题不能为空");
            $("#title").focus();
        } else if($.trim($("#content").val()) == "") {
            alert("内容不能为空");
        } else {
            $("#create_form").submit();
        }
    }

    $(function () {
        $( "#label" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "${baseUrl!}/label/search",
                    dataType: "json",
                    data: {
                        q: request.term
                    },
                    success: function( data ) {
                        response( data );
                    }
                });
            },
            minLength: 2,
            select: function( event, ui ) {
                if($("#labels span").size() >= 5) {
                    alert("每个话题最多添加5个标签");
                } else {
                    appendLabel(ui.item.label);
                }
                $("#label").val("");
            },
            open: function() {
                $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
            },
            close: function() {
                $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
            }
        });

        //==========wangEditor Start============

        var editor = new wangEditor("content");
        // 自定义菜单
        editor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'forecolor',
            'bgcolor',
            'quote',
            'fontfamily',
            'fontsize',
            'head',
            'unorderlist',
            'orderlist',
            'link',
            'table',
            'img',
            'insertcode',
            '|',
            'fullscreen'
        ];
        editor.config.uploadImgUrl = '${baseUrl!}/upload';
        editor.create();

        //==========wangEditor End============

        editor.$txt.html($("#temp_content").val());
        $("#temp_content").remove();
    });

    function addLabel() {
        if($("#labels span").size() >= 5) {
            alert("每个话题最多添加5个标签");
        } else {
            if($.trim($("#label").val()).length > 0) {
                appendLabel($('#label').val());
            }
        }
        $("#label").val("");
        $('#label').focus();
    }

    function appendLabel(labelName) {
        $("#labels").append('<span class="label label-info label-item" name="'+labelName+'">'+labelName+'<a href="javascript:;" onclick="$(this).parents(\'.label\').remove();">&nbsp;x</a></span>');
    }
</script>
</@html>