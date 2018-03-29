package co.yiiu.module.tag.service;

import co.yiiu.module.tag.model.Tag;
import co.yiiu.module.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya at 2018/3/27
 */
@Service
@Transactional
public class TagService {

  @Autowired
  private TagRepository tagRepository;

  public Tag findById(Integer id) {
    return tagRepository.findOne(id);
  }

  public Tag save(Tag tag) {
    return tagRepository.save(tag);
  }

  public List<Tag> save(List<Tag> tags) {
    return tagRepository.save(tags);
  }

  public Tag findByName(String name) {
    return tagRepository.findByName(name);
  }

  public List<Tag> findByNameLike(String name) {
    return tagRepository.findTop7ByNameLike("%" + name + "%");
  }

  public List<Tag> save(String[] tags) {
    List<Tag> tagList = new ArrayList<>();
    for(String t: tags) {
      Tag tag = this.findByName(t);
      if(tag == null) {
        tag = new Tag();
        tag.setInTime(new Date());
        tag.setName(t);
        tag.setTopicCount(1);
      } else {
        tag.setTopicCount(tag.getTopicCount() + 1);
      }
      tagList.add(tag);
    }
    return tagRepository.save(tagList);
  }

  // 查询话题的标签
  public List<Tag> findByTopicId(Integer topicId) {
    return tagRepository.findByTopicId(topicId);
  }

  public Page<Tag> page(Integer pageNo, Integer pageSize) {
    Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(
        new Sort.Order(Sort.Direction.DESC, "topicCount"),
        new Sort.Order(Sort.Direction.DESC, "inTime")
    ));
    return tagRepository.findAll(pageable);
  }
}
