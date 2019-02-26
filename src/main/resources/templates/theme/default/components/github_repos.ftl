<div class="panel panel-info">
  <div class="panel-heading">${user.username}'s repos on GitHub</div>
  <ul id="repos"></ul>
</div>
<style>
  #repos {
    margin-top: 15px;
    padding-left: 0;
    list-style: none;
  }

  #repos li {
    padding: 0 15px;
    margin-top: 10px;
    border-bottom: 1px solid #ccc;
  }

  #repos li:last-child {
    border: 0;
  }
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script>
  $.get("https://api.github.com/users/${githubLogin}/repos?sort=updated", function (data) {
    $.each(data, function (i, v) {
      $("#repos").append(
        "<li>" +
        "  <p><a href=\"" + v.html_url + "\"><b>" + v.name + "</b></a></p>" +
        "  <p style='word-break: break-word;'>" + v.description + "</p>" +
        "  <p>" + v.language + " • <i class=\"fa fa-star\"></i>" + v.stargazers_count + " • Updated " + moment(v.updated_at).fromNow() + "</p>" +
        "</li>"
      );
    })
  })
</script>
