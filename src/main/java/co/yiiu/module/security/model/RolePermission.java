package co.yiiu.module.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by tomoya at 2018/3/19
 */
@Entity
@Table(name = "yiiu_role_permission")
@Setter
@Getter
public class RolePermission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer roleId;
  private Integer permissionId;
}
