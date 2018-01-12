(function ($) {
  $.fn.getCursorPosition = function () {
    if (this.lengh === 0) return -1;
    return $(this).getSelectionStart();
  };
  $.fn.setCursorPosition = function (position) {
    if (this.lengh === 0) return this;
    return $(this).setSelection(position, position);
  };
  $.fn.getSelection = function () {
    if (this.lengh === 0) return -1;
    var s = $(this).getSelectionStart();
    var e = $(this).getSelectionEnd();
    return this[0].value.substring(s, e);
  };
  $.fn.getSelectionStart = function () {
    if (this.lengh === 0) return -1;
    input = this[0];

    var pos = input.value.length;

    if (input.createTextRange) {
      var r = document.selection.createRange().duplicate();
      r.moveEnd('character', input.value.length);
      if (r.text === '')
        pos = input.value.length;
      pos = input.value.lastIndexOf(r.text);
    } else if (typeof(input.selectionStart) != "undefined")
      pos = input.selectionStart;
    return pos;
  };
  $.fn.getSelectionEnd = function () {
    if (this.lengh === 0) return -1;
    input = this[0];

    var pos = input.value.length;

    if (input.createTextRange) {
      var r = document.selection.createRange().duplicate();
      r.moveStart('character', -input.value.length);
      if (r.text === '')
        pos = input.value.length;
      pos = input.value.lastIndexOf(r.text);
    } else if (typeof(input.selectionEnd) != "undefined")
      pos = input.selectionEnd;
    return pos;
  };
  $.fn.setSelection = function (selectionStart, selectionEnd) {
    if (this.lengh === 0) return this;
    input = this[0];

    if (input.createTextRange) {
      var range = input.createTextRange();
      range.collapse(true);
      range.moveEnd('character', selectionEnd);
      range.moveStart('character', selectionStart);
      range.select();
    } else if (input.setSelectionRange) {
      input.focus();
      input.setSelectionRange(selectionStart, selectionEnd);
    }
    return this;
  };
  $.fn.insertAtCousor = function (myValue) {
    var $t = $(this)[0];
    if (document.selection) {
      this.focus();
      sel = document.selection.createRange();
      sel.text = myValue;
      this.focus();
    } else if ($t.selectionStart || $t.selectionStart === 0) {
      var startPos = $t.selectionStart;
      var endPos = $t.selectionEnd;
      var scrollTop = $t.scrollTop;
      $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
      this.focus();
      $t.selectionStart = startPos + myValue.length;
      $t.selectionEnd = startPos + myValue.length;
      $t.scrollTop = scrollTop;
    } else {
      this.value += myValue;
      this.focus();
    }
  };

  var contentE = $("#content");

  // use at.js add emoji support
  contentE.atwho({
    at: ":",
    data: [
      {'name': 'Grinning Face', 'emoji': '😀'},
      {'name': 'Grinning Face With Smiling Eyes', 'emoji': '😁'},
      {'name': 'Face With Tears of Joy', 'emoji': '😂'},
      {'name': 'Rolling on the Floor Laughing', 'emoji': '🤣'},
      {'name': 'Smiling Face With Open Mouth', 'emoji': '😃'},
      {'name': 'Smiling Face With Open Mouth & Smiling Eyes', 'emoji': '😄'},
      {'name': 'Smiling Face With Open Mouth & Cold Sweat', 'emoji': '😅'},
      {'name': 'Winking Face', 'emoji': '😉'},
      {'name': 'Smiling Face With Smiling Eyes', 'emoji': '😊'},
      {'name': 'Face Savouring Delicious Food', 'emoji': '😋'},
      {'name': 'Smiling Face With Sunglasses', 'emoji': '😎'},
      {'name': 'Smiling Face With Heart-Eyes', 'emoji': '😍'},
      {'name': 'Face Blowing a Kiss', 'emoji': '😘'},
      {'name': 'Kissing Face', 'emoji': '😗'},
      {'name': 'Kissing Face With Smiling Eyes', 'emoji': '😙'},
      {'name': 'Kissing Face With Closed Eyes', 'emoji': '😚'},
      {'name': 'Smiling Face', 'emoji': '☺️'},
      {'name': 'Slightly Smiling Face', 'emoji': '🙂'},
      {'name': 'Hugging Face', 'emoji': '🤗'},
      {'name': 'Thinking Face', 'emoji': '🤔'},
      {'name': 'Neutral Face', 'emoji': '😐'},
      {'name': 'Expressionless Face', 'emoji': '😑'},
      {'name': 'Face Without Mouth', 'emoji': '😶'},
      {'name': 'Face With Rolling Eyes', 'emoji': '🙄'},
      {'name': 'Smirking Face', 'emoji': '😏'},
      {'name': 'Persevering Face', 'emoji': '😣'},
      {'name': 'Disappointed but Relieved Face', 'emoji': '😥'},
      {'name': 'Face With Open Mouth', 'emoji': '😮'},
      {'name': 'Zipper-Mouth Face', 'emoji': '🤐'},
      {'name': 'Hushed Face', 'emoji': '😯'},
      {'name': 'Sleepy Face', 'emoji': '😪'},
      {'name': 'Tired Face', 'emoji': '😫'},
      {'name': 'Sleeping Face', 'emoji': '😴'},
      {'name': 'Relieved Face', 'emoji': '😌'},
    ],
    displayTpl: function (data) {
      return '<li>' + data.emoji + ' ' + data.name + "</li>";
    },
    insertTpl: function (data) {
      return data.emoji;
    }
  });

  $("#menu").find(".fa").click(function () {
    if ($(this).hasClass("fa-bold")) {
      var selectVal = contentE.getSelection();
      if (selectVal.length > 0) {
        contentE.insertAtCousor("**" + selectVal + "**");
      } else {
        contentE.insertAtCousor("****");
        contentE.setCursorPosition(contentE.getCursorPosition() - 2);
      }
      contentE.focus();
    } else if ($(this).hasClass("fa-italic")) {
      var selectVal = contentE.getSelection();
      if (selectVal.length > 0) {
        contentE.insertAtCousor("*" + selectVal + "*");
      } else {
        contentE.insertAtCousor("**");
        contentE.setCursorPosition(contentE.getCursorPosition() - 1);
      }
    } else if ($(this).hasClass("fa-quote-left")) {
      var selectVal = contentE.getSelection();
      if (selectVal.length > 0) {
        contentE.insertAtCousor("> " + selectVal);
      } else {
        contentE.insertAtCousor(contentE.val().length === 0 ? "> " : "\r\n> ");
        contentE.setCursorPosition(contentE.getCursorPosition());
      }
    } else if ($(this).hasClass("fa-list")) {
      contentE.insertAtCousor(contentE.val().length === 0 ? "- " : "\r\n- ");
      contentE.setCursorPosition(contentE.getCursorPosition());
    } else if ($(this).hasClass("fa-list-ol")) {
      contentE.insertAtCousor(contentE.val().length === 0 ? "1. " : "\r\n1. ");
      contentE.setCursorPosition(contentE.getCursorPosition());
    } else if ($(this).hasClass("fa-code")) {
      if (contentE.val().length === 0) {
        contentE.insertAtCousor("```\r\n代码内容\r\n```");
      } else {
        contentE.insertAtCousor("\r\n```\r\n代码内容\r\n```");
      }
      var currentPosition = contentE.getCursorPosition();
      contentE.setSelection(currentPosition - 8, currentPosition - 4);
    } else if ($(this).hasClass("fa-link")) {
      if (contentE.val().length === 0) {
        contentE.insertAtCousor("[链接标题](链接地址)");
      } else {
        contentE.insertAtCousor("\r\n[链接标题](链接地址)");
      }
      var currentPosition = contentE.getCursorPosition();
      contentE.setSelection(currentPosition - 11, currentPosition - 7);
    } else if ($(this).hasClass("fa-picture-o")) {

    } else if ($(this).hasClass("fa-eye")) {
      if ($("#pre_div").hasClass("hidden")) {
        $("#pre_div").html(marked(contentE.val()));
        $("#pre_div").removeClass("hidden");
        contentE.addClass("hidden");
      } else {
        $("#pre_div").html("");
        $("#pre_div").addClass("hidden");
        contentE.removeClass("hidden");
      }
    }
  });

  $("#selectPicBtn").click(function () {
    $("#selectFileInput").click();
  });

  $("#selectFileInput").fileupload({
    url: '/common/upload',
    dataType: 'json',
    done: function (e, data) {
      $("#upload-modal-btn").click();
      $('.progress-bar').css('width', '0');
      $('.progress').addClass('hidden');
      $('.percentage').addClass('hidden');
      $("#error_message").text("");
      console.log(data.result);
      if (data.result.code === 200) {
        if (contentE.val().length === 0) {
          contentE.insertAtCousor("![image](" + data.result.detail + ")\r\n");
        } else {
          contentE.insertAtCousor("\r\n![image](" + data.result.detail + ")");
        }
        var currentPosition = contentE.getSelectionEnd();
        contentE.setSelection(currentPosition);
      } else {
        $("#error_message").text(data.result.description);
      }
    }
  });

  $('#selectFileInput').bind('fileuploadprogress', function (e, data) {
    $("#error_message").text("正在上传...");
    $(".progress").removeClass("hidden");
    var progress = parseInt(data.loaded / data.total * 100, 10);
    $('.progress-bar').css(
      'width',
      progress + '%'
    );
    $('.percentage').removeClass("hidden").text(progress + '%');
  });

  $(".upload-area").bind('dragover', function (e) {
    e.preventDefault();
    $(".upload-area").addClass("upload-area-active");
  });

  $(".upload-area").bind('dragleave', function (e) {
    e.preventDefault();
    $(".upload-area").removeClass("upload-area-active");
  });

  contentE.keyup(function (e) {
    if ((e.ctrlKey || e.metaKey) && e.keyCode === 13) {
      $("#editorForm").submit();
    }
  });

})(jQuery);