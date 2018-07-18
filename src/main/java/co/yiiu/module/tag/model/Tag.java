package co.yiiu.module.tag.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/3/27
 */
@Entity
@Table(name = "yiiu_tag")
@Setter
@Getter
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String name;

  private String intro;

  private Date inTime;

  private String logo;

  private Integer topicCount;

}
