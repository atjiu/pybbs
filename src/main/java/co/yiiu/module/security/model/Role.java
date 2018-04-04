package co.yiiu.module.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

}
