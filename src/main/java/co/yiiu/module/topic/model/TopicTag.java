package co.yiiu.module.topic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by tomoya at 2018/3/27
 */
@Entity
@Table(name = "yiiu_topic_tag")
@Setter
@Getter
public class TopicTag implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private Integer topicId;

  private Integer tagId;
}
