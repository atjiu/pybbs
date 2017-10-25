<#include "../../common/layout.ftl"/>
<@html page_title="修改头像" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="changeAvatar"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">修改头像</div>
      <div class="panel-body">
        <p>
          <button class="btn btn-primary" id="choiceAvatarBtn">选择头像</button>
          <button class="btn btn-success" id="confirmAvatarBtn">确认头像</button>
          <input type="file" class="hidden" id="newAvatarFile" name="newAvatarFile"/>
        </p>
        <div class="row">
          <div class="col-md-9">
            <img src="" id="newAvatarImg" class="hidden origin-avatar" alt="">
          </div>
          <div class="col-md-3">
            <div class="preview"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<link rel="stylesheet" href="/static/cropper/cropper.css">
<script src="/static/cropper/cropper.min.js"></script>
<script type="text/javascript">
  $(function () {
    var newAvatarImg = $("#newAvatarImg");
    $("#choiceAvatarBtn").click(function () {
      $("#newAvatarFile").click();
    });
    $("#newAvatarFile").change(function () {
      $("#newAvatarImg").attr("src", "");
      var reader = new FileReader();
      reader.onload = function (e) {
        $("#newAvatarImg").attr("src", e.target.result).removeClass('hidden');
      };
      reader.readAsDataURL($(this)[0].files[0]);

      //cropper load image
      setTimeout(function () {
        newAvatarImg.cropper('destroy');
        var $previews = $(".preview");
        newAvatarImg.cropper({
          ready: function (e) {
            var $clone = $(this).clone().removeClass('cropper-hidden');
            $clone.css({
              display: 'block',
              width: '100%',
              minWidth: 0,
              minHeight: 0,
              maxWidth: 'none',
              maxHeight: 'none'
            });
            $previews.css({
              width: '100%',
              overflow: 'hidden'
            }).html($clone);
          },
          viewMode: 1,
          aspectRatio: 1,
          scalable: false,
          cropBoxResizable: false,
          crop: function (e) {
            var imageData = $(this).cropper('getImageData');
            var previewAspectRatio = e.width / e.height;
            $previews.each(function () {
              var $preview = $(this);
              var previewWidth = $preview.width();
              var previewHeight = previewWidth / previewAspectRatio;
              var imageScaledRatio = e.width / previewWidth;
              $preview.height(previewHeight).find('img').css({
                width: imageData.naturalWidth / imageScaledRatio,
                height: imageData.naturalHeight / imageScaledRatio,
                marginLeft: -e.x / imageScaledRatio,
                marginTop: -e.y / imageScaledRatio
              });
            });
          }
        });
      }, 200);
    });
    $("#confirmAvatarBtn").click(function() {
      if(!$("#newAvatarFile").val()) {
        alert("请先选择图片");
      } else {
        var avatarBase64 = newAvatarImg.cropper('getCroppedCanvas', {width: 100, height: 100}).toDataURL();
        $.ajax({
          url: '/user/changeAvatar',
          async: false,
          cache: false,
          method: 'post',
          dataType: 'json',
          data: {
            '${_csrf.parameterName}': '${_csrf.token}',
            avatar: avatarBase64
          },
          success: function (data) {
            if(data.code === 200) {
              alert("修改成功");
            } else {
              alert(data.description);
            }
          }
        })
      }
    })
  })
</script>
</@html>