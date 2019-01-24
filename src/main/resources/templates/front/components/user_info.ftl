<div class="panel panel-info">
  <div class="panel-body">
    <div class="media">
      <div class="media-left">
        <a href="/user/${_user.username!}">
          <img src="${_user.avatar!}" title="${_user.username!}" class="avatar"/>
        </a>
      </div>
      <div class="media-body">
        <div class="media-heading">
          <a href="/user/${_user.username!}">${_user.username!}</a>
          <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
            <i>${(_user.bio!"这家伙很懒，什么都没有留下")?html}</i>
          </div>
        </div>
      </div>
      <div style="margin-top: 15px;">
        <a href="/topic/create" style="text-decoration: underline"><span class="glyphicon glyphicon-pencil"></span><i>发布话题</i></a>
      </div>
    </div>
  </div>
  <div class="panel-footer" style="background-color: white">
    <div class="row">
      <span class="col-md-6"><a href="/notifications"><span id="n_count">0</span> 条未读消息</a></span>
      <span class="col-md-6 text-right">积分：<a href="/top100">${_user.score!0}</a></span>
    </div>
  </div>
</div>
