package co.yiiu.module.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
  @GeneratedValue
  private Integer id;
  private String name;
  private String url;
  private String value;
  private Integer pid;
}
