<#macro tagsinput tags="">
<link rel="stylesheet" href="/static/bootstrap-tagsinput/bootstrap-tagsinput.css">

<style>
  .tag-logo {
    width: 16px;
    height: 16px;
    border-radius: 2px;
  }

  .tag-intro {
    font-size: 10px;
    color: gray;
    word-wrap: break-word;
    word-break: break-all;
    text-overflow: ellipsis;
    overflow: hidden;
    width: 300px;
  }
</style>

<input type="text" id="tag" data-role="tagsinput" value="${tags}" class="form-control" size="5"
       placeholder="输入标签，最多5个"/>

<script src="/static/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="/static/typeahead/typeahead.bundle.min.js"></script>
<script src="/static/typeahead/handlebars.min.js"></script>

<script type="text/x-handlebars-template" id="autocompleteTpl">
  <div>
    <div>
      {{#if logo}}<img class="tag-logo" src="{{logo}}">{{/if}}
      {{name}} <span style="color: gray;">x {{topicCount}}</span>
    </div>
    <div class="tag-intro">{{intro}}</div>
  </div>
</script>

<script type="text/javascript">
  var tagAutoComplete = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    prefetch: '${site.baseUrl!}api/tag/autocomplete',
    remote: {
      url: '${site.baseUrl!}api/tag/autocomplete?keyword=%QUERY',
      wildcard: '%QUERY',
      filter: function (data) {
        return $.map(data.detail, function (v) {
          return v;
        })
      }
    }
  });
  $('#tag').tagsinput({
    maxTags: 5,
    trimValue: true,
    typeaheadjs: [{
      minLength: 2
    }, {
      name: 'tag_auto_complete',
      displayKey: 'name',
      valueKey: 'name',
      source: tagAutoComplete,
      templates: {
        // empty: [
        //   '<div>',
        //   '没有找到标签!',
        //   '</div>'
        // ].join('\n'),
        suggestion: Handlebars.compile($("#autocompleteTpl").html())
      }
    }]
  });
</script>
</#macro>