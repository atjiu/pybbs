package co.yiiu.module.node.repository;

import co.yiiu.module.node.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface NodeRepository extends JpaRepository<Node, Integer> {

  List<Node> findAll();

  List<Node> findByPid(Integer pid);

  List<Node> findByPidNot(Integer pid);

  Node findByValue(String value);

  void deleteByPid(Integer pid);
}