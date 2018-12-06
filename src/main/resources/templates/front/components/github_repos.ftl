<div class="panel panel-default">
  <div class="panel-heading">${user.username}'s repos on GitHub</div>
  <table class="table" id="repos">
  </table>
</div>
<script>
  $.get("https://api.github.com/users/${user.githubName}/repos", function (data) {
    $.each(data, function (i, v) {
      $("#repos").append("<tr>\n" +
          "      <td>\n" +
          "        <div><a href=\""+v.html_url+"\"><b>"+v.name+"</b></a> <span class=\"pull-right\">"+v.language+" • "+v.stargazers_count+" 人关注</span></div>\n" +
          v.description +
          "      </td>\n" +
          "    </tr>");
    })
  })
</script>
