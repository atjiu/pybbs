package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

  @Query(value = "select p from Permission p, RolePermission rp, Role r, AdminUser au where " +
      "p.id = rp.permissionId and rp.roleId = r.id and r.id = au.roleId and au.id = ?1")
  List<Permission> findByUserId(Integer userId);

  List<Permission> findByPid(Integer pid);
}
