<form onsubmit="return;" id="uploadImageForm">
    <input type="hidden" id="type" value="image"/>
    <input type="file" style="display: none;" onchange="uploadImageFileChange()" id="uploadImageFileEle" multiple
           accept="image/jpeg,image/jpg,image/png,image/gif,video/mp4"/>
</form>
<script>
    function uploadFile(type) {
        $("#type").val(type);
        $("#uploadImageFileEle").click();
    }

    function uploadImageFileChange() {
        var fd = new FormData();
        var type = $("#type").val();
        fd.append("file", document.getElementById("uploadImageFileEle").files[0]);
        fd.append("type", type);
        fd.append("token", "${_user.token!}");
        $.post({
            url: "/api/upload",
            data: fd,
            dataType: 'json',
            headers: {
                'token': '${_user.token!}'
            },
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.code === 200) {
                    var content = $("#content");
                    var text = content.val();
                    if (text.length > 0) text += '\n\n';
                    var insertContent = "";
                    for (var j = 0; j < data.detail.urls.length; j++) {
                        var url = data.detail.urls[j];
                        if (type === "topic") {
                            insertContent += "![image](" + url + ")\n\n"
                        } else if (type === "video") {
                            insertContent += "<video class='embed-responsive embed-responsive-16by9' controls><source src='" + url + "' type='video/mp4'></video>\n\n";
                        }
                    }
                    content.val(text + insertContent);
                    content.focus();
                    $("#uploadImageForm")[0].reset();
                } else {
                    alert(data.description);
                }
            }
        })
    }
</script>
