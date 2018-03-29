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
@Table(name = "yiiu_role")
@Setter
@Getter
public class Role implements Serializable {
  @Id
  @GeneratedValue
  private Integer id;

  private String name;

}
