package cn.tomoya.module.node.service;

import cn.tomoya.module.node.dao.LabelDao;
import cn.tomoya.module.node.entity.Label;
import cn.tomoya.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by tomoya on 2017/6/16.
 */
@Service
@Transactional
public class LabelService {

  @Autowired
  private LabelDao labelDao;

  public void save(Label label) {
    labelDao.save(label);
  }

  /**
   * query label by name
   * @param name
   * @return
   */
  public Label findByName(String name) {
    return labelDao.findByName(name);
  }

  /**
   * fuzzy query label by name
   * @param text
   * @return
   */
  public List<Label> findByNameLike(String text) {
    return labelDao.findByNameLike("%" + text + "%");
  }

  /**
   * search all nodes order by topicCount desc
   * @param p
   * @param size
   * @return
   */
  public Page findAll(int p, int size) {
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "topicCount")
    );
    Pageable pageable = new PageRequest((p - 1) * size, size, sort);
    return labelDao.findAll(pageable);
  }

  /**
   * deal label from create topic
   * @param labels
   * @return
   */
  public String dealLabels(String labels) {
    // deal label
    if (!StringUtils.isEmpty(labels) && !labels.equals(",")) {
      Date now = new Date();
      String labelId = "";
      String[] labelStr = labels.split(",");
      List<String> newLabelStr = StrUtil.removeDuplicate(labelStr);
      for(String s: newLabelStr) {
        Label label = this.findByName(s);
        if(label == null) { // create label and save
          label = new Label();
          label.setInTime(now);
          label.setName(s);
          label.setTopicCount(1);
        } else {
          label.setTopicCount(label.getTopicCount() + 1);
        }
        save(label);
        labelId += label.getId() + ",";
      }
      if(labelId.length() > 0) labelId = labelId.substring(0, labelId.length() - 1);
      return labelId;
    }
    return null;
  }
}
