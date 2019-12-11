<form onsubmit="return;" id="uploadImageForm">
    <input type="hidden" id="type" value="image"/>
    <input type="file" class="d-none" id="uploadImageFileEle" multiple
           accept="image/jpeg,image/jpg,image/png,image/gif,video/mp4"/>
</form>
<style>
    .upload-progress-fade {
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #000;
        z-index: 9999;
        opacity: .3;
        vertical-align: middle;
        text-align: center;
        color: #000;
        padding-top: 200px;
    }

    .upload-progress {
        position: fixed;
        left: 0;
        right: 0;
        top: 220px;
        border: 1px solid #d3d4d3;
        background-color: #fff;
        margin: 0 auto;
        text-align: center;
        padding: 30px 20px;
        opacity: 1;
        z-index: 10000;
        width: 220px;
    }
</style>
<div class="upload-progress-div d-none">
    <div class="upload-progress"></div>
    <div class="upload-progress-fade"></div>
</div>
<script>
    var clientHeight = document.documentElement.clientHeight;

    function uploadFile(type) {
        $("#type").val(type);
        $("#uploadImageFileEle").click();
    }

    $("#uploadImageFileEle").change(function () {
        $(".upload-progress-div").removeClass("d-none");
        $(".upload-progress").css("top", clientHeight * .4 + "px");
        // var _m = layer.msg('上传中(' + upload_progress + '%)...', {icon: 16, shade: 0.5, time: -1});
        var fd = new FormData();
        var type = $("#type").val();
        var ele = $(this)[0];
        for (var i = 0; i < ele.files.length; i++) {
            fd.append("file", ele.files[i]);
        }
        fd.append("type", type);
        fd.append("token", "${_user.token}");
        $.post({
            url: "/api/upload",
            data: fd,
            dataType: 'json',
            headers: {
                'token': '${_user.token}'
            },
            processData: false,
            contentType: false,
            xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { //检查upload属性是否存在
                    //绑定progress事件的回调函数
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            },
            success: function (data) {
                // layer.close(_m);
                if (data.code === 200) {
                    if (data.detail.errors.length === 0) {
                        suc("上传成功");
                    } else {
                        var error = "";
                        for (var k = 0; k < data.detail.errors.length; k++) {
                            error += data.detail.errors[k] + "<br/>";
                        }
                        err(error);
                    }
                    var oldContent = window.editor.getDoc().getValue();
                    // if (oldContent) oldContent += '\n\n';
                    var insertContent = "";
                    for (var j = 0; j < data.detail.urls.length; j++) {
                        var url = data.detail.urls[j];
                        if (type === "topic") {
                            insertContent += "![image](" + url + ")\n\n"
                        } else if (type === "video") {
                            insertContent += "<video class='embed-responsive embed-responsive-16by9' controls><source src='" + url + "' type='video/mp4'></video>\n\n";
                        }
                    }
                    window.editor.getDoc().setValue(oldContent + insertContent);
                    window.editor.focus();
                    //定位到文档的最后一个字符的位置
                    window.editor.setCursor(window.editor.lineCount(), 0);
                    $("#uploadImageForm")[0].reset();
                } else {
                    err(data.description);
                }
            }
        })
    });

    //上传进度回调函数：
    function progressHandlingFunction(e) {
        if (e.lengthComputable) {
            var percent = e.loaded / e.total * 100;
            $(".upload-progress").text("正在上传(" + percent.toFixed(2) + "%)...");
            if (percent === 100) {
                $(".upload-progress-div").addClass("d-none");
                $(".upload-progress").text("");
            }
        }
    }
</script>
