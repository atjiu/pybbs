<#include "../layout/layout.ftl"/>
<@html page_title="编辑话题" page_tab="">
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-header">编辑话题</div>
                <div class="card-body">
                    <form action="" onsubmit="return;" id="form">
                        <div class="form-group">
                            <label for="title">标题</label>
                            <input type="text" name="title" id="title" value="${topic.title}" class="form-control"
                                   placeholder="标题"/>
                        </div>
                        <div class="form-group">
                            <label for="content">内容</label>
                            <#if site?? && site.content_style?? && site.content_style == "MD">
                                <span class="pull-right">
                                    <a href="javascript:uploadFile('topic')">上传图片</a>&nbsp;
                                    <a href="javascript:uploadFile('video')">上传视频</a>
                                </span>
                            </#if>
                            <#include "../components/editor.ftl"/>
                            <@editor _type="topic" _content="${topic.content!}" style="${topic.style!'MD'}"/>
                        </div>
                        <#--<div class="form-group">
                          <label for="tags">标签</label>
                          <input type="text" name="tags" id="tags" value="${tags}" class="form-control" placeholder="标签, 多个标签以 英文逗号 隔开"/>
                        </div>-->
                        <div class="form-group">
                            <button type="button" id="btn" class="btn btn-info">更新话题</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-3 hidden-xs">
            <#include "../components/create_topic_guide.ftl"/>
        </div>
    </div>
    <script>
        $(function () {
            $("#btn").click(function () {
                var title = $("#title").val();
                var content = window.editor ? window.editor.getDoc().getValue() : window._E.txt.html();
                // var tags = $("#tags").val();
                if (!title || title.length > 120) {
                    err("请输入标题，且最大长度在120个字符以内");
                    return;
                }
                // if (!tags || tags.split(",").length > 5) {
                //   err("请输入标签，且最多只能填5个");
                //   return;
                // }
                var _this = this;
                $(_this).button("loading");
                req("put", "/api/topic/${topic.id}", {
                    title: title,
                    content: content
                }, "${_user.token!}", function (data) {
                    if (data.code === 200) {
                        suc("更新成功");
                        setTimeout(function () {
                            window.location.href = "/topic/" + data.detail.id
                        }, 700);
                    } else {
                        err(data.description);
                        $(_this).button("reset");
                    }
                });
            });
        })
    </script>
    <#include "../components/upload.ftl"/>
</@html>
