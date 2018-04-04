package co.yiiu.module.topic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer topicId;

  private Integer tagId;
}
