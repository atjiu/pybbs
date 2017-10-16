package co.yiiu.module.code.repository;

import co.yiiu.module.code.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya on 17-6-6.
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {

  List<Code> findByCodeAndType(String code, String type);

}
