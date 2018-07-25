package co.yiiu.module.es.repository;

import co.yiiu.module.es.pojo.TagIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by tomoya at 2018/4/24
 */
public interface TagIndexRepository extends ElasticsearchRepository<TagIndex, Integer> {
}
