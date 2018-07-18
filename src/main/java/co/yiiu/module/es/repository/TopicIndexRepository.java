package co.yiiu.module.es.repository;

import co.yiiu.module.es.model.TopicIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya at 2018/4/24
 */
@Repository
public interface TopicIndexRepository extends ElasticsearchRepository<TopicIndex, Integer> {
}
