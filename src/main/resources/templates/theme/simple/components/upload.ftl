<form onsubmit="return;" id="uploadImageForm">
  <input type="file" style="display: none;" onchange="uploadImageFileChange()" id="uploadImageFileEle"/>
</form>
<script>
  function uploadImageBtn() {
    $("#uploadImageFileEle").click();
  }
  function uploadImageFileChange() {
    var fd = new FormData();
    fd.append("file", document.getElementById("uploadImageFileEle").files[0]);
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
          var content = $("#content");
          var text = content.val();
          if (text.length > 0) text += '\n\n';
          content.val(text + "![image](" + data.detail + ")")
          content.focus();
          $("#uploadImageForm")[0].reset();
        } else {
          alert(data.description);
        }
      }
    })
  }
</script>
