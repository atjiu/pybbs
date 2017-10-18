$(function () {
  var n = $("#goTop");
  n.click(function () {
    return $("html,body").animate({
      scrollTop: 0
    });
  });
});

function updateUserProfile() {
  var errors = 0;
  var em = $("#error_message");
  var bio = $("#bio").val();
  if (bio.length > 1000) {
    errors++;
    em.html("个性签名不能超过1000个字");
  }
  if (errors === 0) {
    var form = $("#userProfileForm");
    form.submit();
  }
}
function permissionSubmit() {
  var errors = 0;
  var em = $("#error_message");
  var pid = $("#pid").val();
  var name = $("#name").val();
  var url = $("#url").val();
  var description = $("#description").val();
  if (name.length === 0) {
    errors++;
    em.html("权限标识不能为空");
  }
  if (pid > 0 && url.length === 0) {
    errors++;
    em.html("授权路径不能为空");
  }
  if (description.length === 0) {
    errors++;
    em.html("权限描述不能为空");
  }
  if (errors === 0) {
    var form = $("#permissionForm");
    form.submit();
  }
}
function roleSubmit() {
  var errors = 0;
  var em = $("#error_message");
  var name = $("#name").val();
  var description = $("#description").val();
  if (name.length === 0) {
    errors++;
    em.html("角色标识不能为空");
  }
  if (description.length === 0) {
    errors++;
    em.html("角色描述不能为空");
  }
  if (errors === 0) {
    var form = $("#roleForm");
    form.submit();
  }
}