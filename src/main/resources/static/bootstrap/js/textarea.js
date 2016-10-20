(function ($) {
    $.fn.getCursorPosition = function () {
        if (this.lengh == 0) return -1;
        return $(this).getSelectionStart();
    };
    $.fn.setCursorPosition = function (position) {
        if (this.lengh == 0) return this;
        return $(this).setSelection(position, position);
    };
    $.fn.getSelection = function () {
        if (this.lengh == 0) return -1;
        var s = $(this).getSelectionStart();
        var e = $(this).getSelectionEnd();
        return this[0].value.substring(s, e);
    };
    $.fn.getSelectionStart = function () {
        if (this.lengh == 0) return -1;
        input = this[0];

        var pos = input.value.length;

        if (input.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveEnd('character', input.value.length);
            if (r.text == '')
                pos = input.value.length;
            pos = input.value.lastIndexOf(r.text);
        } else if (typeof(input.selectionStart) != "undefined")
            pos = input.selectionStart;
        return pos;
    };
    $.fn.getSelectionEnd = function () {
        if (this.lengh == 0) return -1;
        input = this[0];

        var pos = input.value.length;

        if (input.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveStart('character', -input.value.length);
            if (r.text == '')
                pos = input.value.length;
            pos = input.value.lastIndexOf(r.text);
        } else if (typeof(input.selectionEnd) != "undefined")
            pos = input.selectionEnd;
        return pos;
    };
    $.fn.setSelection = function (selectionStart, selectionEnd) {
        if (this.lengh == 0) return this;
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
        } else if ($t.selectionStart || $t.selectionStart == '0') {
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
    $("#content").keydown(function (e) {
        var content = $(this);
        //插入粗体快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 66) {//ctrl+b
            e.preventDefault();
            var selectVal = content.getSelection();
            if (selectVal.length > 0) {
                content.insertAtCousor("**" + selectVal + "**");
            } else {
                content.insertAtCousor("****");
                content.setCursorPosition(content.getCursorPosition() - 2);
            }
        }
        //插入斜体快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 73) {//ctrl+i
            e.preventDefault();
            var selectVal = content.getSelection();
            if (selectVal.length > 0) {
                content.insertAtCousor("*" + selectVal + "*");
            } else {
                content.insertAtCousor("**");
                content.setCursorPosition(content.getCursorPosition() - 1);
            }
        }
        //插入链接快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 76) {//ctrl+l
            e.preventDefault();
            if(content.val().length == 0) {
                content.insertAtCousor("[链接内容](链接地址)");
            } else {
                content.insertAtCousor("\r\n[链接内容](链接地址)");
            }
            var currentPosition = content.getCursorPosition();
            content.setSelection(currentPosition - 11, currentPosition - 7);
        }
        //插入图片快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 71) {//ctrl+g
            e.preventDefault();
            if(content.val().length == 0) {
                content.insertAtCousor("![image](图片地址)");
            } else {
                content.insertAtCousor("\r\n![image](图片地址)");
            }
            var currentPosition = content.getCursorPosition();
            content.setSelection(currentPosition - 5, currentPosition - 1);
        }
        //插入代码快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 75) {//ctrl+k
            e.preventDefault();
            if(content.val().length == 0) {
                content.insertAtCousor("```\r\n代码内容\r\n```");
            } else {
                content.insertAtCousor("\r\n```\r\n代码内容\r\n```");
            }
            var currentPosition = content.getCursorPosition();
            content.setSelection(currentPosition - 8, currentPosition - 4);
        }
        //插入hr快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 82) {//ctrl+r
            e.preventDefault();
            if(content.val().length == 0) {
                content.insertAtCousor("----------\r\n");
            } else {
                content.insertAtCousor("\r\n----------\r\n");
            }
        }
        //插入heading快捷键
        if ((e.ctrlKey || e.metaKey) && e.keyCode == 72) {//ctrl+h
            e.preventDefault();
            if(content.val().length == 0) {
                content.insertAtCousor("## 标题内容");
            } else {
                content.insertAtCousor("\r\n## 标题内容");
            }
            var currentPosition = content.getCursorPosition();
            content.setSelection(currentPosition - 4, currentPosition);
        }
        content.scrollTop(content[0].scrollHeight);
    });
    // 打开下面的注释即可粘贴图片上传
    $("#content").on("paste", function(event) {
        var em = $("#error_message");
        var items = (event.clipboardData || event.originalEvent.clipboardData).items;
        for (index in items) {
            var item = items[index];
            var type = item.type;
            if(type && type.split('/')[0] == 'image') {
                em.html("正在上传...");
                var suffix = type.split('/')[1];
                var blob = item.getAsFile();
                var size = blob.size;
                if(size / (1024 * 1024) < 2) {
                    var reader = new FileReader();
                    reader.onload = function (event) {
                        //var base64Str = event.target.result;
                        var form = document.createElement("form").setAttribute("enctype", "multipart/form-data");
                        var formData = new FormData(form);
                        formData.append("image", blob, "image." + suffix);
                        var xhr = new XMLHttpRequest();
                        xhr.open('POST', '/upload', true);
                        xhr.onload = function (event) {
                            var responseText = event.currentTarget.responseText;
                            var json = JSON.parse(responseText);
                            if (json.code == 200) {
                                $.each(json.detail, function(i, v) {
                                    var content = $("#content");
                                    if (content.val().length > 0) {
                                        content.val(content.val() + "\r\n![image](" + v + ")\r\n");
                                    } else {
                                        content.val(content.val() + "![image](" + v + ")\r\n");
                                    }
                                });
                                em.html("");
                            } else {
                                em.html(json.description);
                            }
                        };
                        xhr.send(formData);
                    };
                    reader.readAsDataURL(blob);
                } else {
                    em.html("上传图片大小不能超过2M");
                }
            }
        }
    });
})(jQuery);
