var moveEnd = function (obj) {
  obj.focus();
  obj = obj.get(0);
  var len = obj.value.length;
  if (document.selection) {
    var sel = obj.createTextRange();
    sel.moveStart('character', len);
    sel.collapse();
    sel.select();
  } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
    obj.selectionStart = obj.selectionEnd = len;
  }
};
$(function () {
  var n = $("#goTop");
  n.click(function () {
    return $("html,body").animate({
      scrollTop: 0
    });
  });
});
function publishTopic() {
  var em = $("#error_message");
  var errors = 0;
  var title = $("#title").val();

  if (title.length == 0) {
    errors++;
    em.html("标题不能为空");
  }

  if (title.length > 120) {
    errors++;
    em.html("标题不能超过120个字符")
  }

  if (errors == 0) {
    var form = $("#topicForm");
    form.submit();
  }
}
function updateUserProfile() {
  var errors = 0;
  var em = $("#error_message");
  var signature = $("#signature").val();
  if (signature.length > 1000) {
    errors++;
    em.html("个性签名不能超过1000个字");
  }
  if (errors == 0) {
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
  if (name.length == 0) {
    errors++;
    em.html("权限标识不能为空");
  }
  if (pid > 0 && url.length == 0) {
    errors++;
    em.html("授权路径不能为空");
  }
  if (description.length == 0) {
    errors++;
    em.html("权限描述不能为空");
  }
  if (errors == 0) {
    var form = $("#permissionForm");
    form.submit();
  }
}
function roleSubmit() {
  var errors = 0;
  var em = $("#error_message");
  var name = $("#name").val();
  var description = $("#description").val();
  if (name.length == 0) {
    errors++;
    em.html("角色标识不能为空");
  }
  if (description.length == 0) {
    errors++;
    em.html("角色描述不能为空");
  }
  if (errors == 0) {
    var form = $("#roleForm");
    form.submit();
  }
}
function saveSection() {
  var errors = 0;
  var em = $("error_message");
  var name = $("name").val();
  var tab = $("tab").val();
  if (name.length == 0) {
    errors++;
    em.html("名称不能为空");
  }
  if (tab.length == 0) {
    errors++;
    em.html("tab不能为空");
  }
  if (errors == 0) {
    var form = $("sectionForm");
    form.submit();
  }
}

function replyUp(id) {
  var replyMedia = $("#reply" + id);
  var replyUpBtn = $("#reply_up_" + id);
  var replyDownBtn = $("#reply_down_" + id);
  if (replyUpBtn.hasClass("btn-default")) {
    $.ajax({
      url: "/reply/" + id + "/up",
      async: true,
      cache: false,
      type: "get",
      dataType: "json",
      success: function (data) {
        if (data.code == 200) {
          replyUpBtn.find("span").last().text(data.detail);
          replyUpBtn.removeClass("btn-default").addClass("btn-primary");
          if(replyDownBtn.hasClass("btn-primary")) {
            replyDownBtn.removeClass("btn-primary").addClass("btn-default");
          }

          if(data.detail >= 3) {
            replyMedia.addClass("reply-highlight")
          } else {
            replyMedia.removeClass("reply-highlight")
          }
        }
      }
    });
  } else {
    $.ajax({
      url: "/reply/" + id + "/cancelUp",
      async: true,
      cache: false,
      type: "get",
      dataType: "json",
      success: function (data) {
        if (data.code == 200) {
          replyUpBtn.find("span").last().text(data.detail);
          replyUpBtn.removeClass("btn-primary").addClass("btn-default");

          if(data.detail >= 3) {
            replyMedia.addClass("reply-highlight")
          } else {
            replyMedia.removeClass("reply-highlight")
          }
        }
      }
    });
  }
}

function replyDown(id) {
  var replyMedia = $("#reply" + id);
  var replyUpBtn = $("#reply_up_" + id);
  var replyDownBtn = $("#reply_down_" + id);
  if (replyDownBtn.hasClass("btn-default")) {
    $.ajax({
      url: "/reply/" + id + "/down",
      async: true,
      cache: false,
      type: "get",
      dataType: "json",
      success: function (data) {
        if (data.code == 200) {
          replyUpBtn.find("span").last().text(data.detail);
          replyDownBtn.removeClass("btn-default").addClass("btn-primary");
          if(replyUpBtn.hasClass("btn-primary")) {
            replyUpBtn.removeClass("btn-primary").addClass("btn-default");
          }

          if(data.detail >= 3) {
            replyMedia.addClass("reply-highlight")
          } else {
            replyMedia.removeClass("reply-highlight")
          }
        }
      }
    });
  } else {
    $.ajax({
      url: "/reply/" + id + "/cancelDown",
      async: true,
      cache: false,
      type: "get",
      dataType: "json",
      success: function (data) {
        if (data.code == 200) {
          replyUpBtn.find("span").last().text(data.detail);
          replyDownBtn.removeClass("btn-primary").addClass("btn-default");

          if(data.detail >= 3) {
            replyMedia.addClass("reply-highlight")
          } else {
            replyMedia.removeClass("reply-highlight")
          }
        }
      }
    });
  }
}