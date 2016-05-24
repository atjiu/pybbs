<#macro wysiwyg content="">
<link rel="stylesheet" href="${baseUrl!}/static/bootstrap-wysiwyg/bower_components/google-code-prettify/src/prettify.css">
<link rel="stylesheet" href="${baseUrl!}/static/bootstrap-wysiwyg/bower_components/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${baseUrl!}/static/bootstrap-wysiwyg/css/style.css">
<div class="bootstrap-wysiwyg">
    <div id="alerts"></div>
    <div class="btn btn-raised-toolbar" data-role="editor-toolbar" data-target="#editor">
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default dropdown-toggle" data-toggle="dropdown" title="字体"><i class="fa fa-font"></i><b
                    class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a data-edit="fontName 宋体,SimSun" style="font-family:宋体,SimSun">宋体</a></li>
                <li><a data-edit="fontName 雅黑,'Microsoft Yahei'" style="font-family:雅黑,'Microsoft Yahei'">雅黑</a></li>
                <li><a data-edit="fontName 楷体,楷体_GB2312,SimKai" style="font-family:楷体,楷体_GB2312,SimKai">楷体</a></li>
                <li><a data-edit="fontName Courier New" style="font-family:Courier New">Courier New</a></li>
                <li><a data-edit="fontName Comic Sans MS" style="font-family:Comic Sans MS">Comic Sans MS</a></li>
            </ul>
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default dropdown-toggle" data-toggle="dropdown" title="字体大小"><i
                    class="fa fa-text-height"></i>&nbsp;<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a data-edit="fontSize 5" class="fs-Five">大号</a></li>
                <li><a data-edit="fontSize 3" class="fs-Three">中号</a></li>
                <li><a data-edit="fontSize 1" class="fs-One">小号</a></li>
            </ul>
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default" data-edit="bold" title="粗体 (Ctrl/Cmd+B)"><i class="fa fa-bold"></i></a>
            <a class="btn btn-raised btn-default" data-edit="italic" title="斜体 (Ctrl/Cmd+I)"><i class="fa fa-italic"></i></a>
            <a class="btn btn-raised btn-default" data-edit="strikethrough" title="删除线"><i class="fa fa-strikethrough"></i></a>
            <a class="btn btn-raised btn-default" data-edit="underline" title="下划线 (Ctrl/Cmd+U)"><i
                    class="fa fa-underline"></i></a>
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default" data-edit="insertunorderedlist" title="无序列表"><i class="fa fa-list-ul"></i></a>
            <a class="btn btn-raised btn-default" data-edit="insertorderedlist" title="有序列表"><i class="fa fa-list-ol"></i></a>
        <#--<a class="btn btn-raised btn-default" data-edit="outdent" title="反缩进 (Shift+Tab)"><i class="fa fa-outdent"></i></a>-->
        <#--<a class="btn btn-raised btn-default" data-edit="indent" title="缩进 (Tab)"><i class="fa fa-indent"></i></a>-->
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default" data-edit="justifyleft" data-placement="top" title="左对齐 (Ctrl/Cmd+L)"><i
                    class="fa fa-align-left"></i></a>
            <a class="btn btn-raised btn-default" data-edit="justifycenter" title="居中 (Ctrl/Cmd+E)"><i
                    class="fa fa-align-center"></i></a>
            <a class="btn btn-raised btn-default" data-edit="justifyright" title="右对齐 (Ctrl/Cmd+R)"><i
                    class="fa fa-align-right"></i></a>
        <#--<a class="btn btn-raised btn-default" data-edit="justifyfull" title="两端对齐 (Ctrl/Cmd+J)"><i class="fa fa-align-justify"></i></a>-->
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default dropdown-toggle" data-toggle="dropdown" title="超链接"><i class="fa fa-link"></i></a>
            <div class="dropdown-menu input-append">
                <input class="form-control" placeholder="URL" type="text" data-edit="createLink"/>
                <button class="btn btn-raised" type="button">添加</button>
            </div>
            <a class="btn btn-raised btn-default" data-edit="unlink" title="删除超链接"><i class="fa fa-unlink"></i></a>
        </div>
        <div class="btn btn-raised-group">
            <a class="btn btn-raised btn-default" title="插入图片(或拖动进来)" id="pictureBtn"><i class="fa fa-picture-o"></i></a>
            <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage"/>
        </div>
    <#--<div class="btn btn-raised-group">-->
    <#--<a class="btn btn-raised btn-default" data-edit="undo" title="撤销 (Ctrl/Cmd+Z)"><i class="fa fa-undo"></i></a>-->
    <#--<a class="btn btn-raised btn-default" data-edit="redo" title="重做 (Ctrl/Cmd+Y)"><i class="fa fa-repeat"></i></a>-->
    <#--</div>-->
        <input type="text" data-edit="inserttext" id="voiceBtn">
    </div>
    <textarea class="hidden" name="content" id="content"></textarea>
    <div id="editor">
    ${content!}
    </div>
</div>
<script src="${baseUrl!}/static/bootstrap-wysiwyg/bower_components/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="${baseUrl!}/static/bootstrap-wysiwyg/bower_components/google-code-prettify/src/prettify.js"></script>
<script src="${baseUrl!}/static/bootstrap-wysiwyg/src/bootstrap-wysiwyg.js"></script>
<script type="text/javascript">
    function initToolbarBootstrapBindings() {
        $('a[title]').tooltip();

        $('.dropdown-menu input').click(function () {
                    return false;
                })
                .change(function () {
                    $(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');
                }).keydown('esc', function () {
            this.value = '';
            $(this).change();
        });

        $('[data-role=magic-overlay]').each(function () {
            var overlay = $(this),
                    target = $(overlay.data('target'));
            overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
        });

        if ("onwebkitspeechchange" in document.createElement("input")) {
            var editorOffset = $('#editor').offset();
            //$('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
        } else {
            $('#voiceBtn').hide();
        }
    }
    function showErrorAlert(reason, detail) {
        var msg = '';
        if (reason === 'unsupported-file-type') {
            msg = "Unsupported format " + detail;
        } else {
            console.log("error uploading file", reason, detail);
        }

        $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
    }
    initToolbarBootstrapBindings();
    $('#editor').wysiwyg({
        fileUploadError: showErrorAlert
    });
    window.prettyPrint && prettyPrint();
</script>
</#macro>