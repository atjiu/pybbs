package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya at 2018/3/19
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
