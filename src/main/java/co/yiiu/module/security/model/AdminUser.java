package co.yiiu.module.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Entity
@Table(name = "yiiu_admin_user")
@Setter
@Getter
public class AdminUser implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  private String password;

  private Date inTime;

  private String token;

  private Integer roleId;

  @Transient
  private Role role;
  @Transient
  private List<Permission> permissions;
}
