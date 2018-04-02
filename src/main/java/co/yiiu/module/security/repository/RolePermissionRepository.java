package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

  void deleteByRoleId(Integer roleId);

  void deleteByPermissionId(Integer permissionId);

  List<RolePermission> findByRoleId(Integer roleId);

}
