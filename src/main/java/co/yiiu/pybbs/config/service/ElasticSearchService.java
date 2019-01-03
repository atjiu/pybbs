package co.yiiu.pybbs.config.service;

import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.SystemConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@DependsOn("mybatisPlusConfig")
public class ElasticSearchService implements BaseService<RestHighLevelClient> {

  @Autowired
  private SystemConfigService systemConfigService;
  private Logger log = LoggerFactory.getLogger(ElasticSearchService.class);
  private RestHighLevelClient client;
  // 索引名
  private String name;

  public static XContentBuilder topicMappingBuilder;

  static {
    try {
      topicMappingBuilder = JsonXContent.contentBuilder()
          .startObject()
            .startObject("properties")
//              .startObject("id")
//                .field("type", "integer")
//              .endObject()
              .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("index", "true")
              .endObject()
              .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("index", "true")
              .endObject()
            .endObject()
          .endObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public RestHighLevelClient instance() {
    if (this.client != null) return client;
    try {
      SystemConfig systemConfigHost = systemConfigService.selectByKey("elasticsearch_host");
      String host = systemConfigHost.getValue();
      SystemConfig systemConfigPort = systemConfigService.selectByKey("elasticsearch_port");
      String port = systemConfigPort.getValue();
      SystemConfig systemConfigName = systemConfigService.selectByKey("elasticsearch_index");
      name = systemConfigName.getValue();

      if (StringUtils.isEmpty(host) || StringUtils.isEmpty(port)) return null;
      client = new RestHighLevelClient(
          RestClient.builder(
              new HttpHost(host, Integer.parseInt(port), "http")));
      // 判断索引是否存在，不存在创建
      if (!this.existIndex()) this.createIndex("topic", topicMappingBuilder);
      return client;
    } catch (NumberFormatException e) {
      log.error(e.getMessage());
      return null;
    }
  }

  // 创建索引
  public boolean createIndex(String type, XContentBuilder mappingBuilder) {
    try {
      if (this.instance() == null) return false;
      CreateIndexRequest request = new CreateIndexRequest(name);
      request.settings(Settings.builder()
          .put("index.number_of_shards", 1)
          .put("index.number_of_shards", 5));
      if (mappingBuilder != null) request.mapping(type, mappingBuilder);
      CreateIndexResponse response = this.client.indices().create(request, RequestOptions.DEFAULT);
      return response.isAcknowledged();
    } catch (IOException e) {
      log.error(e.getMessage());
      return false;
    }
  }

  // 检查索引是否存在
  public boolean existIndex() {
    try {
      if (this.instance() == null) return false;
      GetIndexRequest request = new GetIndexRequest();
      request.indices(name);
      request.local(false);
      request.humanReadable(true);
      return client.indices().exists(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
      return false;
    }
  }

  // 删除索引
  public boolean deleteIndex() {
    try {
      if (this.instance() == null) return false;
      DeleteIndexRequest request = new DeleteIndexRequest(name);
      request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
      AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
      return response.isAcknowledged();
    } catch (IOException e) {
      log.error(e.getMessage());
      return false;
    }
  }

  // 创建文档
  public void createDocument(String type, String id, Map<String, Object> source) {
    try {
      if (this.instance() == null) return;
      IndexRequest request = new IndexRequest(name, type, id);
      request.source(source);
      client.index(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  // 更新文档
  public void updateDocument(String type, String id, Map<String, Object> source) {
    try {
      if (this.instance() == null) return;
      UpdateRequest request = new UpdateRequest(name, type, id);
      request.doc(source);
      client.update(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  // 删除文档
  public void deleteDocument(String type, String id) {
    try {
      if (this.instance() == null) return;
      DeleteRequest request = new DeleteRequest(name, type, id);
      client.delete(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  // 批量创建文档
  public void bulkDocument(String type, Map<String, Map<String, Object>> sources) {
    try {
      if (this.instance() == null) return;
      BulkRequest requests = new BulkRequest();
      Iterator<String> it = sources.keySet().iterator();
      int count = 0;
      while(it.hasNext()) {
        count++;
        String next = it.next();
        IndexRequest request = new IndexRequest(name, type, next);
        request.source(sources.get(next));
        requests.add(request);
        if (count % 1000 == 0) {
          client.bulk(requests, RequestOptions.DEFAULT);
          requests.requests().clear();
          count = 0;
        }
      }
      if (requests.numberOfActions() > 0) client.bulk(requests, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  // 批量删除文档
  public void bulkDeleteDocument(String type, List<Integer> ids) {
    try {
      if (this.instance() == null) return;
      BulkRequest requests = new BulkRequest();
      int count = 0;
      for (Integer id: ids) {
        count++;
        DeleteRequest request = new DeleteRequest(name, type, String.valueOf(id));
        requests.add(request);
        if (count % 1000 == 0) {
          client.bulk(requests, RequestOptions.DEFAULT);
          requests.requests().clear();
          count = 0;
        }
      }
      if (requests.numberOfActions() > 0) client.bulk(requests, RequestOptions.DEFAULT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * 查询
   *
   * @param pageNo
   * @param pageSize
   * @param keyword  要查询的内容
   * @param fields   要查询的字段，可以为多个
   * @return 分页对象 {@link Page}
   */
  public Page<Map<String, Object>> searchDocument(Integer pageNo, Integer pageSize, String keyword, String... fields) {
    try {
      if (this.instance() == null) return new Page<>();
      SearchRequest request = new SearchRequest(name);
      SearchSourceBuilder builder = new SearchSourceBuilder();
      builder.query(QueryBuilders.multiMatchQuery(keyword, fields));
      builder.from((pageNo - 1) * pageSize).size(pageSize);
      request.source(builder);
      SearchResponse response = client.search(request, RequestOptions.DEFAULT);
      // 总条数
      long totalCount = response.getHits().getTotalHits();
      // 结果集
      List<Map<String, Object>> records = Arrays.stream(response.getHits().getHits())
          .map(hit -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", hit.getId());
            map.putAll(hit.getSourceAsMap());
            return map;
          }).collect(Collectors.toList());
      Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
      page.setTotal(totalCount);
      page.setRecords(records);
      return page;
    } catch (IOException e) {
      log.error(e.getMessage());
      return new Page<>();
    }
  }
}
