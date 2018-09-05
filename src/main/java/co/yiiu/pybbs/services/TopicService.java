package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by tomoya at 2018/9/3
 */
@Service
public class TopicService {

  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private CommentService commentService;

  public void save(Topic topic) {
    topicRepository.save(topic);
  }

  public Topic findById(String id) {
    return topicRepository.findById(id).orElse(null);
  }

  public Page<Topic> findAll(Integer pageNo, Integer pageSize, String tab) {
    Sort sort = Sort.by(Sort.Direction.DESC, "top", "inTime");
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    if (StringUtils.isEmpty(tab)) {
      return topicRepository.findAll(pageable);
    } else {
      Topic topic = new Topic();
      topic.setTab(tab);
      return topicRepository.findAll(Example.of(topic), pageable);
    }
  }

  public void deleteById(String id) {
    commentService.deleteByTopicId(id);
    topicRepository.deleteById(id);
  }

  public Page<Topic> findByUserId(String userId, Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "inTime");
    Topic topic = new Topic();
    topic.setUserId(userId);
    return topicRepository.findAll(Example.of(topic), pageable);
  }

  public Topic findByTitle(String title) {
    Topic topic = new Topic();
    topic.setTitle(title);
    List<Topic> topics = topicRepository.findAll(Example.of(topic));
    if (topics.size() > 0) return topics.get(0);
    return null;
  }
}
