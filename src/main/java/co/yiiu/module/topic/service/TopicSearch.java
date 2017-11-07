package co.yiiu.module.topic.service;

import co.yiiu.module.topic.model.Topic;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tomoya on 17-6-14.
 */
@Repository
@Transactional
public class TopicSearch {

  private Logger log = LoggerFactory.getLogger(TopicSearch.class);

  @PersistenceContext
  private EntityManager entityManager;

  public Page<Topic> search(int p, int size, String text) {

    // get the full text entity manager
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.
            getFullTextEntityManager(entityManager);

    // create the query using Hibernate Search query DSL
    QueryBuilder queryBuilder =
        fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder().forEntity(Topic.class).get();

    // a very basic query by keywords
    org.apache.lucene.search.Query query =
        queryBuilder
            .keyword()
            .onFields("title", "content")
            .matching(text)
            .createQuery();

    // wrap Lucene query in an Hibernate Query object
    org.hibernate.search.jpa.FullTextQuery jpaQuery =
        fullTextEntityManager.createFullTextQuery(query, Topic.class);

    // execute search and return results (sorted by relevance as default)
    jpaQuery.setFirstResult((p - 1) * size);
    jpaQuery.setMaxResults(size);
    Pageable pageable = new PageRequest(p - 1, size);
    List results = jpaQuery.getResultList();

    results = hightLight(query, results, "title", "content");

    return new PageImpl(results, pageable, jpaQuery.getResultSize());
  }

  public static List<Topic> hightLight(Query query, List<Topic> data, String... fields) {
    List<Topic> result = new ArrayList<>();
    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class='text-danger'>", "</span>");
    QueryScorer queryScorer = new QueryScorer(query);
    Highlighter highlighter = new Highlighter(formatter, queryScorer);
    // 使用IK中文分词
    Analyzer analyzer = new SmartChineseAnalyzer();
    for (Topic _topic : data) {
      Topic newTopic = new Topic();
      BeanUtils.copyProperties(_topic, newTopic);
      // 构建新的对象进行返回，避免页面错乱（我的页面有错乱）
      for (String fieldName : fields) {
        // 获得字段值，并给新的文章对象赋值
        Object fieldValue = ReflectionUtils
            .invokeMethod(BeanUtils.getPropertyDescriptor(Topic.class, fieldName).getReadMethod(), _topic);
        ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Topic.class, fieldName).getWriteMethod(),
            newTopic, fieldValue);
        String hightLightFieldValue;
        try {
          hightLightFieldValue = highlighter.getBestFragment(analyzer, fieldName, String.valueOf(fieldValue));
        } catch (Exception e) {
          throw new RuntimeException("高亮显示关键字失败", e);
        }
        // 如果高亮成功则重新赋值
        if (hightLightFieldValue != null) {
          ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Topic.class, fieldName).getWriteMethod(),
              newTopic, hightLightFieldValue);
        } else {
          String value = null;
          if (fieldName.equals("title")) {
            value = _topic.getTitle();
          } else if (fieldName.equals("content")) {
            value = _topic.getContent();
          }
          if (value != null)
            ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Topic.class, fieldName).getWriteMethod(), newTopic, value);
        }
      }
      result.add(newTopic);
    }
    return result;
  }

  public void indexAll() {
    try {
      FullTextEntityManager fullTextEntityManager =
          Search.getFullTextEntityManager(entityManager);
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException e) {
      log.error(
          "An error occurred trying to build the serach index: " +
              e.toString());
    }
  }


}
