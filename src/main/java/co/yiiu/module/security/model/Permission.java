package co.yiiu.module.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya at 2018/3/19
 */
@Entity
@Table(name = "yiiu_permission")
@Setter
@Getter
public class Permission implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String url;
  private String value;
  private Integer pid;
}
