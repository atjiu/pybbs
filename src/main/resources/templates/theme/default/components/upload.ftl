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
    var uploadProgress = document.querySelector(".upload-progress");
    var uploadImageFileEle = document.getElementById("uploadImageFileEle");

    function uploadFile(type) {
        $("#type").val(type);
        uploadImageFileEle.click();
    }

    uploadImageFileEle.addEventListener("change", function () {
        $(".upload-progress-div").removeClass("d-none");
        uploadProgress.css("top", clientHeight * .4 + "px");
        // var _m = layer.msg('上传中(' + upload_progress + '%)...', {icon: 16, shade: 0.5, time: -1});
        var fd = new FormData();
        var type = $("#type").val();
        var ele = $(this)[0];
        for (var i = 0; i < ele.files.length; i++) {
            fd.append("file", ele.files[i]);
        }
        fd.append("type", type);

        var xhr = new XMLHttpRequest();
        xhr.responseType = "json";
        xhr.open('POST', '/api/upload');
        xhr.setRequestHeader("token", "${_user.token!}");
        xhr.onload = function () {
            var data = xhr.response;
            if (data.errno !== 0) {
                suc("上传成功");
            } else {
                var error = "";
                for (var k = 0; k < data.errors.length; k++) {
                    error += data.errors[k] + "<br/>";
                }
                err(error);
            }
            var oldContent = window.editor.getDoc().getValue();
            // if (oldContent) oldContent += '\n\n';
            var insertContent = "";
            for (var j = 0; j < data.data.length; j++) {
                var url = data.data[j].url;
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
            document.getElementById("uploadImageForm").reset();
        };
        // 获取上传进度
        xhr.upload.onprogress = function (event) {
            if (event.lengthComputable) {
                var percent = event.loaded / event.total * 100;
                uploadProgress.text("正在上传(" + percent.toFixed(2) + "%)...");
                if (percent === 100) {
                    $(".upload-progress-div").addClass("d-none");
                    $(".upload-progress").text("");
                }
            }
        };
        xhr.send(fd);
    })
</script>
