package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.Collect;
import co.yiiu.pybbs.repositories.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {

  @Autowired
  private CollectRepository collectRepository;

  public void save(Collect collect) {
    collectRepository.save(collect);
  }

  public void deleteByTopicId(String topicId) {
    collectRepository.deleteByTopicId(topicId);
  }

  public Page<Collect> findByUserId(String userId, Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "inTime");
    Collect collect = new Collect();
    collect.setUserId(userId);
    return collectRepository.findAll(Example.of(collect), pageable);
  }

  public Collect findByUserIdAndTopicId(String userId, String topicId) {
    Collect collect = new Collect();
    collect.setUserId(userId);
    collect.setTopicId(topicId);
    List<Collect> collects = collectRepository.findAll(Example.of(collect));
    if (collects.size() > 0) return collects.get(0);
    return null;
  }

  public void deleteById(String id) {
    collectRepository.deleteById(id);
  }
}
