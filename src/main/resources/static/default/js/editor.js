(function () {

  var contentE = $("#content");

  $("#menu .fa").click(function () {
    var position = contentE.caret("position");
    if($(this).hasClass("fa-bold")) {
      var p1 = position.left;
      var tmp = contentE.val();
      newText = tmp.substr(0, p1) + "****" + tmp.substr(p1, tmp.length);
      contentE.val(newText);
      contentE.caret('position', -2);
      contentE.focus();
    }
  });
})();