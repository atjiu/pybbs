<form onsubmit="return;" id="uploadImageForm">
  <input type="hidden" id="type" value="image"/>
  <input type="file" class="d-none" id="uploadImageFileEle"
         accept="image/jpeg,image/jpg,image/png,image/gif,video/mp4"/>
</form>
<script>
  function uploadFile(type) {
    $("#type").val(type);
    $("#uploadImageFileEle").click();
  }

  $("#uploadImageFileEle").change(function () {
    var _m = layer.msg('上传中...', {icon: 16, shade: 0.5, time: -1});
    var fd = new FormData();
    var type = $("#type").val();
    fd.append("file", $(this)[0].files[0]);
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
      success: function (data) {
        layer.close(_m);
        if (data.code === 200) {
          suc("上传成功");
          var oldContent = window.editor.getDoc().getValue();
          // if (oldContent) oldContent += '\n\n';
          var insertContent = "";
          if (type === "topic") {
            insertContent = "![image](" + data.detail + ")\n\n"
          } else if (type === "video") {
            insertContent = "<video class='embed-responsive embed-responsive-16by9' controls><source src='" + data.detail + "' type='video/mp4'></video>\n\n";
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
</script>
