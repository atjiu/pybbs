<#macro create_topic_guide>
<div class="panel panel-default">
  <div class="panel-heading">
    <b>话题发布指南</b>
  </div>
  <div class="panel-body">
    <p>• 首先，请为你的主题选择一个节点。恰当的归类会让你发布的信息更加有用。</p>
    <p>• 在标题中描述内容要点。如果一件事情在标题的长度内就已经可以说清楚，那就没有必要写正文了。</p>
    <p>• 保持对陌生人的友善。用知识去帮助别人。</p>
    <p>• <b>如果是转载的文章，请务必只填上原文的URL，内容就不用复制过来了。</b></p>
    <p>• 每天每个用户最多只能发${site.maxCreateTopic} 个话题。</p>
    <p>• 发布话题会<#if site.createTopicScore gte 0>增加<#else>扣除</#if>用户${site.createTopicScore?abs}积分</p>
    <p>• 发布话题5分钟后不能再编辑</p>
  </div>
</div>
</#macro>