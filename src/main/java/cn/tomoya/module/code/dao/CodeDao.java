package cn.tomoya.module.code.dao;

import cn.tomoya.module.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya on 17-6-6.
 */
@Repository
public interface CodeDao extends JpaRepository<Code, Integer> {

  List<Code> findByCodeAndType(String code, String type);

}
