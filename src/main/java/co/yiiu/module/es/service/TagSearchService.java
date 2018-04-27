package co.yiiu.module.es.service;

import co.yiiu.module.es.model.TagIndex;
import co.yiiu.module.es.model.TopicIndex;
import co.yiiu.module.es.repository.TagIndexRepository;
import co.yiiu.module.tag.model.Tag;
import co.yiiu.module.tag.repository.TagRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya at 2018/4/24
 */
@Service
public class TagSearchService {

  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private TagIndexRepository tagIndexRepository;

  /**
   * 索引全部标签
   */
  public void indexedAll() {
    List<TagIndex> tagIndices = new ArrayList<>();
    List<Tag> tags = tagRepository.findAll();
    tags.forEach(tag -> {
      TagIndex tagIndex = new TagIndex();
      BeanUtils.copyProperties(tag, tagIndex);
      tagIndices.add(tagIndex);
    });
    // 保存前先删除索引
    this.clearAll();
    tagIndexRepository.saveAll(tagIndices);
  }

  /**
   * 索引标签
   * @param tag
   */
  public void indexed(Tag tag) {
    TagIndex tagIndex = new TagIndex();
    BeanUtils.copyProperties(tag, tagIndex);
    tagIndexRepository.save(tagIndex);
  }

  /**
   * 根据id删除索引
   * @param id
   */
  public void deleteById(Integer id) {
    tagIndexRepository.deleteById(id);
  }

  /**
   * 查询索引
   * @param keyword
   * @param limit
   * @return
   */
  public List<TagIndex> query(String keyword, Integer limit) {
    Pageable pageable = PageRequest.of(0, limit);
    QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "name");
    SearchQuery query = new NativeSearchQueryBuilder()
        .withPageable(pageable)
        .withQuery(queryBuilder)
        .build();return tagIndexRepository.search(query).getContent();
  }

  /**
   * 清除所有的索引
   */
  public void clearAll() {
    tagIndexRepository.deleteAll();
  }
}
