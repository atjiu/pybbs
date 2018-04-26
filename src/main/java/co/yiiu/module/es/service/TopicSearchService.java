package co.yiiu.module.es.service;

import co.yiiu.module.es.model.TopicIndex;
import co.yiiu.module.es.repository.TopicIndexRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.repository.TopicRepository;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class TopicSearchService {

  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private TopicIndexRepository topicIndexRepository;

  public void indexed() {
    List<TopicIndex> topicIndices = new ArrayList<>();
    List<Topic> topics = topicRepository.findAll();
    topics.forEach(topic -> {
      TopicIndex topicIndex = new TopicIndex();
      topicIndex.setId(topic.getId());
      topicIndex.setTitle(topic.getTitle());
      topicIndex.setTag(topic.getTag());
      topicIndex.setContent(topic.getContent());
      topicIndex.setInTime(topic.getInTime());

      User user = userService.findById(topic.getUserId());
      topicIndex.setUsername(user.getUsername());
      topicIndices.add(topicIndex);
    });
    topicIndexRepository.saveAll(topicIndices);
  }

  public Page<TopicIndex> query(String keyword, Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
    SearchQuery query = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build();
    return topicIndexRepository.search(query);
  }
}
