(function (Editor, markdownit, WebUploader) {
  // Set default options
  var md = new markdownit();

  md.set({
    html: false,        // Enable HTML tags in source
    xhtmlOut: false,        // Use '/' to close single tags (<br />)
    breaks: true,        // Convert '\n' in paragraphs into <br>
    langPrefix: 'language-',  // CSS language prefix for fenced blocks
    linkify: false,        // Autoconvert URL-like text to links
    typographer: false,        // Enable smartypants and other sweet transforms
  });

  window.markdowniter = md;

  var toolbar = Editor.toolbar;

  var replaceTool = function (name, callback) {
    for (var i = 0, len = toolbar.length; i < len; i++) {
      var v = toolbar[i];
      if (typeof(v) !== 'string' && v.name === name) {
        v.action = callback;
        break;
      }
    }
  };

  var $body = $('body');

  //添加连接工具
  var ToolLink = function () {
    var self = this;
    this.$win = $([
      '<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="editorToolImageTitle" aria-hidden="true" style="display: none;">' +
      '<div class="modal-dialog" role="document">' +
      '<div class="modal-content">' +
      '<div class="modal-header">' +
      '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
      '<div id="editorToolImageTitle">添加链接</div></div>' +
      '<div class="modal-body">' +
      '<form>' +
      '<div class="form-group">' +
      '<label>标题</label>' +
      '<input type="text" name="title" placeholder="标题" class="form-control">' +
      '</div>' +
      '<div class="form-group">' +
      '<label>链接</label>' +
      '<input type="text" name="link" value="http://" class="form-control" placeholder="链接">' +
      '</div>' +
      '</form>' +
      '</div>' +
      '<div class="modal-footer">' +
      '<button class="btn btn-primary" role="save">确定</button>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>'
    ].join('')).appendTo($body);

    this.$win.on('click', '[role=save]', function () {
      self.$win.find('form').submit();
    }).on('submit', 'form', function () {
      var $el = $(this);
      var title = $el.find('[name=title]').val();
      var link = $el.find('[name=link]').val();

      self.$win.modal('hide');
      self.editor.push(' [' + title + '](' + link + ')');

      $el.find('[name=title]').val('');
      $el.find('[name=link]').val('http://');

      return false;
    });
  };

  ToolLink.prototype.bind = function (editor) {
    this.editor = editor;
    this.$win.modal('show');
  };

  //图片上传工具
  var ToolImage = function () {
    var self = this;
    this.$win = $([
      '<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="editorToolImageTitle" aria-hidden="true" style="display: none;">' +
      '<div class="modal-dialog" role="document">' +
      '<div class="modal-content">' +
      '<div class="modal-header">' +
      '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
      '<div id="editorToolImageTitle">图片</div></div>' +
      '<div class="modal-body">' +
      '<div class="upload-img">' +
      '<div class="button webuploader-container">' +
      '<a class="btn btn-success" href="javascript:;">上传图片</a>' +
      '<input type="file" name="file" class="webuploader-element-invisible" accept="image/*">' +
      '</div>' +
      '<div id="bfb" style="margin-top: 40px;"></div>' +
      '<div class="progress">' +
      '<div class="progress-bar progress-bar-success progress-bar-striped tip" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">' +
      '<span class="sr-only"></span>' +
      '</div>' +
      '</div>' +
      '<div class="alert alert-danger" role="alert"></div>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>'
    ].join('')).appendTo($body);

    this.$upload = this.$win.find('.upload-img').css({
      height: '150px',
      textAlign: 'center',
      border: '4px dashed #ddd'
    });

    this.$uploadBtn = this.$upload.find('.button').css({
      width: '86px',
      height: '40px',
      margin: '50px auto 0'
    });

    this.$progress = this.$upload.find('.progress').hide();

    this.$uploadTip = this.$upload.find('.tip');
    this.$alert = this.$win.find('.alert-danger').hide();

    this.file = false;

    var _csrf = $("input[name='_csrf']").val();

    this.uploader = WebUploader.create({
      swf: '/static/bootstrap/libs/webuploader/Uploader.swf',
      server: '/upload?_csrf=' + _csrf,
      pick: this.$uploadBtn[0],
      paste: document.body,
      dnd: this.$upload[0],
      auto: true,
      fileSingleSizeLimit: 2 * 1024 * 1024,
      //sendAsBinary: true,
      // 只允许选择图片文件。
      accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
      }
    });

    this.uploader.on('beforeFileQueued', function (file) {
      if (self.file !== false || !self.editor) {
        return false;
      }
      self.showFile(file);
    });

    this.uploader.on('uploadProgress', function (file, percentage) {
      // console.log(percentage);
      self.showProgress(file, (percentage * 100).toFixed(2));
    });

    this.uploader.on('uploadSuccess', function (file, res) {
      if (res.code == 200) {
        $(".close").click();
        $("#error_message").html("");
        var image = '![' + file.name + '](' + res.detail + ')';
        self.editor.push(self.editor.value().length == 0 ? image : '\n' + image);
      } else {
        self.removeFile();
        self.showError(res.description || '服务器走神了，上传失败');
      }
    });

    this.uploader.on('uploadComplete', function (file) {
      self.uploader.removeFile(file);
      self.removeFile();
    });

    this.uploader.on('error', function (type) {
      self.removeFile();
      switch (type) {
        case 'Q_EXCEED_SIZE_LIMIT':
        case 'F_EXCEED_SIZE':
          self.showError('文件太大了, 不能超过2M');
          break;
        case 'Q_TYPE_DENIED':
          self.showError('只能上传图片');
          break;
        default:
          self.showError('发生未知错误');
      }
    });

    this.uploader.on('uploadError', function () {
      self.removeFile();
      self.showError('服务器走神了，上传失败');
    });
  };

  ToolImage.prototype.removeFile = function () {
    //var self = this;
    this.file = false;
    this.$uploadBtn.show();
    this.$progress.hide();
    this.$uploadTip.hide();
    $("#bfb").hide();
  };

  ToolImage.prototype.showFile = function (file) {
    //var self = this;
    this.file = file;
    this.$uploadBtn.hide();
    // this.$uploadTip.html('正在上传: ' + file.name).show();
    this.hideError();
  };

  ToolImage.prototype.showError = function (error) {
    $("#error_message").html(error);
    this.$alert.html(error).show();
  };

  ToolImage.prototype.hideError = function (error) {
    $("#error_message").html("");
    this.$alert.hide();
  };

  ToolImage.prototype.showProgress = function (file, percentage) {
    this.$progress.css({margin: '40px 0;'}).show();
    $("#bfb").html('正在上传: ' + file.name + ' ' + percentage + '%').show();
    $("#error_message").html('正在上传: ' + file.name + ' ' + percentage + '%');
    this.$uploadTip
      .css({width: percentage + '%'})
      .attr("aria-valuenow", percentage);
  };

  ToolImage.prototype.bind = function (editor) {
    this.editor = editor;
    this.$win.modal('show');
  };

  var toolImage = new ToolImage();
  var toolLink = new ToolLink();

  replaceTool('image', function (editor) {
    toolImage.bind(editor);
  });
  replaceTool('link', function (editor) {
    toolLink.bind(editor);
  });

  //当编辑器取得焦点时，绑定 toolImage；
  var createToolbar = Editor.prototype.createToolbar;
  Editor.prototype.createToolbar = function (items) {
    createToolbar.call(this, items);
    var self = this;
    $(self.codemirror.display.input).on('focus', function () {
      toolImage.editor = self;
    });
  };

  //追加内容
  Editor.prototype.push = function (txt) {
    var cm = this.codemirror;
    var line = cm.lastLine();
    cm.setLine(line, cm.getLine(line) + txt);
  };
})(window.Editor, window.markdownit, window.WebUploader);
