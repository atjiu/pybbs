package cn.tomoya.module.label.dao;

import cn.tomoya.module.label.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya on 2017/6/16.
 */
@Repository
public interface LabelDao extends JpaRepository<Label, Integer> {

  Label findByName(String name);

  List<Label> findByNameLike(String name);

}
