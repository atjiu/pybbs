<#include "/page/front/common/_layout.ftl"/>
<@html title="${topic.title!} - ${siteTitle!}" description="${topic.title!}" page_tab="topic" sidebar_topic_user="show"
    sidebar_other_topic="show" sidebar_xgtopic="show">
<link rel="stylesheet" href="${baseUrl!}/static/wangEditor/css/wangEditor.css">
<link rel="stylesheet" href="${baseUrl!}/static/css/jquery.atwho.min.css">
<script src="${baseUrl!}/static/js/lodash.min.js"></script>
<script src="${baseUrl!}/static/wangEditor/js/wangEditor.js"></script>
<script src="${baseUrl!}/static/js/jquery.atwho.min.js"></script>

<div class="panel panel-default">
    <div class="panel-body">
        <#if topic.top == 1>
            <span class="label label-success">顶</span>
        <#elseif topic.good == 1>
            <span class="label label-success">精</span>
        </#if>
        <span id="topic_title">
            ${topic.title!}
        </span>
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            <span><a href="${baseUrl!}/user/${topic.author_id!}">${topic.nickname!}</a></span>
            <span>
                &nbsp;•&nbsp;${topic.formatDate(topic.in_time)!}
            </span>
            <span>&nbsp;•&nbsp;${topic.view!} 次浏览</span>
            <#if topic.modify_time??>
                <span>&nbsp;•&nbsp;更新
                    ${topic.formatDate(topic.modify_time)!}
                </span>
            </#if>
            <span>
                &nbsp;•&nbsp;<a href="${baseUrl!}/?tab=${topic.tab}">${topic.sectionName!}</a>
            </span>
            &nbsp;
            <#if session.user?? && topic.author_id == session.user.id>
                <span>
                    <a href="${baseUrl!}/topic/edit/${topic.id!}"><span class="glyphicon glyphicon-edit"></span></a>&nbsp;
                    <a href="javascript:if(confirm('确定要删除此话题吗？'))location='${baseUrl!}/topic/delete/${topic.id!}'"><span
                            class="glyphicon glyphicon-trash" style="cursor:pointer;"></span></a>
                </span>
            </#if>
        </div>
    </div>
    <div class="panel-body" style="border-top: 1px #E5E5E5 solid; padding-top: 10px">
        <div id="topic_content">
            ${topic.content!}
        </div>
        <div class="pull-left topic-label">
            <#list labels as label>
                <a href="${baseUrl!}/?l=${label.id!}" style="text-decoration: none;">
                    <span class="label label-success label-item">${label.name}</span>
                </a>
            </#list>
        </div>
    </div>
    <#if session.user??>
        <div class="panel-footer" id="topic_footer">
            <#if collect??>
                <input type="button" id="collect" onclick="collect(2, '${topic.id!}')"
                       class="btn btn-xs btn-default" value="取消收藏"/>
            <#else>
                <input type="button" id="collect" onclick="collect(1, '${topic.id!}')"
                       class="btn btn-xs btn-default" value="加入收藏"/>
            </#if>
            <input type="button" class="btn btn-xs btn-default" value="微博分享"
                onclick="window.open('http://service.weibo.com/share/share.php?url=${baseUrl!}/topic/${topic.id!}.html?r=${session.user.nickname!}&title=JFinalbbs%20-%20${topic.title!}', '_blank', 'width=550,height=370'); recordOutboundLink(this, '微博分享', 'weibo.com');"/>
            <#if collectCount &gt; 0><div class="pull-right collect-count">${collectCount!}人收藏</div></#if>
        </div>
    </#if>
</div>
<div class="panel panel-default">
<#if topic.reply_count == 0>
    <div class="panel-body text-center">
        目前尚无回复
    </div>
<#else>
    <div class="panel-heading">${topic.reply_count!"0"}个回复</div>
    <div class="panel-body">
        <#list replies as reply>
            <div class="media" id="${reply.id!}">
                <div class="media-left">
                    <a href="${baseUrl!}/user/${reply.author_id}">
                        <img src="${reply.avatar!}" alt="avatar" class="media-object avatar">
                    </a>
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <p class="small-fade">
                            <a href="${baseUrl!}/user/${reply.author_id!}" data_class="atwho" data_id="${reply.author_id!}">${reply.nickname!}</a>
                            <span>${reply.formatDate(reply.in_time)!}</span>
                            <span class="pull-right">${reply_index + 1}楼</span>
                        </p>
                    </div>
                    <div class="media-body reply_content">
                    ${reply.content!}
                    </div>
                </div>
            </div>
            <#if reply_has_next>
                <div class="divide"></div>
            </#if>
        </#list>
    </div>
</#if>
</div>
<#if session.user??>
    <div class="panel panel-default" id="reply_input">
        <div class="panel-heading">
            添加回复 <b style="color:red;">注:回复会奖励1点积分，但被管理员删除回复，将扣除作者2积分</b>
        </div>
        <div class="panel-body">
            <form action="${baseUrl!}/reply/${topic.id!}" method="post" id="reply_form">
                <div id="reply_content"><textarea id="content" name="content" class="form-control inputor" style="height: 200px; margin-bottom: 5px;"></textarea></div>
                <input type="submit" class="btn btn-raised btn-default " value="回复">
                <div id="preview_content" class="hidden"></div>
            </form>
        </div>
    </div>
</#if>
<script type="text/javascript">
    $(function () {
        $("#reply_form").submit(function () {
            if ($.trim($("#content").val()) == "") {
                alert("内容不能为空");
                return false;
            }
            return true;
        });

        <#if session.user??>
        //==========wangEditor Start============

        var editor = new wangEditor("content");
        // 自定义菜单
        editor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'forecolor',
            'bgcolor',
            'quote',
            'fontfamily',
            'fontsize',
            'head',
            'unorderlist',
            'orderlist',
            'link',
            'table',
            'img',
            'insertcode',
            '|',
            'fullscreen'
        ];
        editor.config.uploadImgUrl = '${baseUrl!}/upload';
        editor.create();

        //==========wangEditor End============

        // At.js start
        var nicknames = [];
        nicknames.push({author_id:'${topic.author_id!}', name: '${topic.nickname!}'});
        $("a[data_class='atwho']").each(function (i, item) {
            console.log(nicknames);
            nicknames.push({author_id:$(item).attr('data_id') ,name:$(item).text()});
        });
        editor.$txt.atwho({
            at: '@',
            data: _.uniq(nicknames, 'author_id')
        });
        // At.js end

        </#if>
    });

    var _type = 0;

    function collect(type, tid) {
        var url = "${baseUrl!}/collect/" + tid;
        if (_type == 0) _type = type;
        if (_type == 2) url = "${baseUrl!}/collect/delete/" + tid;
        $.ajax({
            url: url,
            async: false,
            cache: false,
            type: 'post',
            dataType: "json",
            data: {},
            success: function (data) {
                if (data.code == '200') {
                    if (_type == 1) {
                        _type = 2;
                        $("#collect").removeClass("btn-default");
                        $("#collect").addClass("btn-default");
                        $("#collect").val("取消收藏");
                    } else {
                        _type = 1;
                        $("#collect").removeClass("btn-default");
                        $("#collect").addClass("btn-default");
                        $("#collect").val("加入收藏");
                    }
                } else {
                    alert(data.description);
                }
            }
        });
    }

</script>
</@html>