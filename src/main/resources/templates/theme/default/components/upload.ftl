<form onsubmit="return;" id="uploadImageForm">
  <input type="file" class="hidden" id="uploadImageFileEle"/>
</form>
<script>
  $("#uploadImageBtn").click(function () {
    $("#uploadImageFileEle").click();
  });
  $("#uploadImageFileEle").change(function () {
    var fd = new FormData();
    fd.append("file", $(this)[0].files[0]);
    fd.append("type", "topic");
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
        if (data.code === 200) {
          toast("上传成功", "success");
          var oldContent = window.editor.getDoc().getValue();
          if (oldContent) oldContent += '\n\n';
          window.editor.getDoc().setValue(oldContent + "![image](" + data.detail + ")");
          window.editor.focus();
          //定位到文档的最后一个字符的位置
          window.editor.setCursor(window.editor.lineCount(), 0);
          $("#uploadImageForm")[0].reset();
        } else {
          toast(data.description);
        }
      }
    })
  });
</script>
