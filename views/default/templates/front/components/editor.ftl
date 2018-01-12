<#macro editor row=15 type="topic" content="">
<div class="form-group">
  <#if type=="topic">
    <label for="content">内容</label>
  </#if>
  <span <#if type=="topic">class="pull-right"</#if> id="menu">
    <i class="fa fa-bold" aria-hidden="true"></i>
    <i class="fa fa-italic" aria-hidden="true"></i>
    <i class="fa fa-quote-left" aria-hidden="true"></i>
    <i class="fa fa-list" aria-hidden="true"></i>
    <i class="fa fa-list-ol" aria-hidden="true"></i>
    <i class="fa fa-code" aria-hidden="true"></i>
    <i class="fa fa-link" aria-hidden="true"></i>
    <i class="fa fa-picture-o" aria-hidden="true" data-toggle="modal" data-target="#myModal"></i>
    <i class="fa fa-eye" aria-hidden="true"></i>
  </span>
  <textarea name="content" id="content" rows="${row}" class="form-control" placeholder="请输入内容~">${content!}</textarea>
  <div id="pre_div" class="hidden"></div>

  <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">上传图片</h4>
        </div>
        <div class="modal-body">
          <p class="upload-area">
            <button type="button" class="btn btn-success" id="selectPicBtn">选择图片</button>
            <input type="file" class="hidden" name="file" accept="image/png,image/gif,image/jpg,image/jpeg"
                   data-url="/common/upload" id="selectFileInput"/>
          </p>
          <div class="progress hidden">
            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar"
                 aria-valuenow="0"
                 aria-valuemin="0" aria-valuemax="100"></div>
            <div class="percentage"></div>
          </div>
        </div>
        <div class="modal-footer">
          <span class="pull-left" style="line-height: 30px;">
            点击上传按钮选择图片上传，或者拖动图片到这里直接上传
          </span>
          <button type="button" class="btn btn-primary btn-sm" id="upload-modal-btn" data-dismiss="modal">取消
          </button>
        </div>
      </div>
    </div>
  </div>
</div>
<link href="/static/css/jquery.atwho.min.css" rel="stylesheet">

<script src="/static/js/jquery.ui.widget.js"></script>
<script src="/static/js/jquery.fileupload.js"></script>
<script src="/static/js/marked.min.js"></script>
<script src="/static/js/jquery.caret.min.js"></script>
<script src="/static/js/jquery.atwho.min.js"></script>
<script src="/static/js/editor.js"></script>
</#macro>