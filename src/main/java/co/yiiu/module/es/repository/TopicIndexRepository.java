package co.yiiu.module.es.repository;

import co.yiiu.module.es.pojo.TopicIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by tomoya at 2018/4/24
 */
public interface TopicIndexRepository extends ElasticsearchRepository<TopicIndex, Integer> {
}
