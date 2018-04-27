package co.yiiu.module.es.repository;

import co.yiiu.module.es.model.TagIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya at 2018/4/24
 */
@Repository
public interface TagIndexRepository extends ElasticsearchRepository<TagIndex, Integer> {
}
