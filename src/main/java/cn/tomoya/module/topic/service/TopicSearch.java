package cn.tomoya.module.topic.service;

import cn.tomoya.module.topic.entity.Topic;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by tomoya on 17-6-14.
 */
@Repository
@Transactional
public class TopicSearch {

  @PersistenceContext
  private EntityManager entityManager;

  public Page search(int p, int size, String text) {

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
    Pageable pageable = new PageRequest((p - 1) * size, size);
    List results = jpaQuery.getResultList();
    return new PageImpl(results, pageable, jpaQuery.getResultSize());
  }

}
