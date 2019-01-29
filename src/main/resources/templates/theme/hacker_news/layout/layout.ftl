<#macro html page_title>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>${page_title} - ${site.name}</title>
  <link rel="stylesheet" href="https://news.ycombinator.com/news.css?tR69YSVmkcPOlJWQ6QcH">
</head>
<body>
<center>
  <table id="hnmain" border="0" cellpadding="0" cellspacing="0" width="85%" bgcolor="#f6f6ef">
    <tbody>
    <tr>
      <td bgcolor="#ff6600">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding:2px">
          <tbody>
          <tr>
            <td style="line-height:12pt; height:20px;">
              <span class="pagetop">
                <b class="hnname">
                  <a href="/">${site.name}</a>
                </b>
                <a href="/?tab=all">全部</a> | <a href="/?tab=good">精华</a> | <a href="/?tab=hot">最热</a> | <a
                  href="/?tab=newest">最新</a> | <a href="/?tab=noanswer">无人问津</a> | <a href="/topic/create">发布话题</a>
              </span>
            </td>
            <td style="text-align:right;padding-right:4px;">
              <span class="pagetop">
                <a id="me" href="user?id=tomoya">tomoya</a>|
                <a id="logout" href="logout?auth=6c2c4986090f81d4bc9e59fe9ba0ea50c18156b3&amp;goto=news">logout</a>
              </span>
            </td>
          </tr>
          </tbody>
        </table>
      </td>
    </tr>
    <tr id="pagespace" title="" style="height:10px"></tr>
    <tr>
      <td>
      <#nested />
      </td>
    </tr>
    <tr>
      <td><img src="s.gif" height="10" width="0">
        <table width="100%" cellspacing="0" cellpadding="1">
          <tbody>
          <tr>
            <td bgcolor="#ff6600"></td>
          </tr>
          </tbody>
        </table>
        <br>
        <center><a href="https://www.ycombinator.com/apply/">
          Applications are open for YC Summer 2019
        </a></center>
        <br>
        <center><span class="yclinks"><a href="newsguidelines.html">Guidelines</a>
        | <a href="newsfaq.html">FAQ</a>
        | <a href="mailto:hn@ycombinator.com">Support</a>
        | <a href="https://github.com/HackerNews/API">API</a>
        | <a href="security.html">Security</a>
        | <a href="lists">Lists</a>
        | <a href="bookmarklet.html" rel="nofollow">Bookmarklet</a>
        | <a href="http://www.ycombinator.com/legal/">Legal</a>
        | <a href="http://www.ycombinator.com/apply/">Apply to YC</a>
        | <a href="mailto:hn@ycombinator.com">Contact</a></span><br><br>
          <form method="get" action="//hn.algolia.com/">Search:
            <input type="text" name="q" value="" size="17" autocorrect="off" spellcheck="false" autocapitalize="off"
                   autocomplete="false"></form>
        </center>
      </td>
    </tr>
    </tbody>
  </table>
</center>
</body>
</html>
</#macro>
