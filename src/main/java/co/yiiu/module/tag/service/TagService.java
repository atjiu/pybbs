package co.yiiu.module.tag.service;

import co.yiiu.core.bean.Page;
import co.yiiu.module.tag.mapper.TagMapper;
import co.yiiu.module.tag.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
  private TagMapper tagMapper;

  public Tag findById(Integer id) {
    return tagMapper.selectByPrimaryKey(id);
  }

  public void save(Tag tag) {
    tagMapper.insertSelective(tag);
  }

  public void update(Tag tag) {
    tagMapper.updateByPrimaryKeySelective(tag);
  }

  public void save(List<Tag> tags) {
    for (Tag tag : tags) {
      this.save(tag);
    }
  }

  public Tag findByName(String name) {
    return tagMapper.findByName(name);
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
        this.save(tag);
      } else {
        tag.setTopicCount(tag.getTopicCount() + 1);
        this.update(tag);
      }
      tagList.add(tag);
    }
    return tagList;
  }

  // 查询话题的标签
  public List<Tag> findByTopicId(Integer topicId) {
    return tagMapper.findByTopicId(topicId);
  }

  public Page<Tag> page(Integer pageNo, Integer pageSize) {
    List<Tag> list = tagMapper.findAll((pageNo - 1) * pageSize, pageSize, "topic_count desc");
    int count = tagMapper.count();
    return new Page<>(pageNo, pageSize, count, list);
  }

  public void deleteById(Integer id) {
    tagMapper.deleteByPrimaryKey(id);
  }
}
