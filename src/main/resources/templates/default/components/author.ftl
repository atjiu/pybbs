<div class="panel panel-info">
  <div class="panel-heading">
    作者
  </div>
  <div class="panel-body">
    <div class="media">
      <div class="media-left">
        <a href="/user/${topicUser.username}">
          <img src="${topicUser.avatar!}" title="${topicUser.username}" class="avatar"/>
        </a>
      </div>
      <div class="media-body">
        <div class="media-heading">
          <a href="/user/${topicUser.username!}">${topicUser.username}</a>
        </div>
        <p>积分：${topicUser.score}</p>
      </div>
      <div style="color: #7A7A7A; font-size: 12px;margin-top:5px;">
        <i>${(topicUser.bio!"这家伙很懒，什么都没有留下")?html}</i>
      </div>
    </div>
  </div>
</div>
