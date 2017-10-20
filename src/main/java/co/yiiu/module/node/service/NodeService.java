package co.yiiu.module.node.service;

import co.yiiu.module.node.model.Node;
import co.yiiu.module.node.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "nodes")
public class NodeService {

  @Autowired
  private NodeRepository nodeRepository;

  @Cacheable
  public List<Map<String, Object>> findAll() {
    List<Map<String, Object>> nodes = new ArrayList<>();
    List<Node> pNodes = this.findByPid(0);
    for(Node pn: pNodes) {
      Map<String, Object> map = new HashMap<>();
      List<Node> cNodes = this.findByPid(pn.getId());
      map.put("name", pn.getName());
      map.put("list", cNodes);
      nodes.add(map);
    }
    return nodes;
  }

  /**
   * 查询所有子节点
   * @return
   */
  @Cacheable
  public List<Node> findAllChild() {
    return nodeRepository.findByPidNot(0);
  }

  @Cacheable
  public List<Node> findByPid(int pid) {
    return nodeRepository.findByPid(pid);
  }

  @CacheEvict(allEntries = true)
  public void save(Node node) {
    nodeRepository.save(node);
  }

  @CacheEvict(allEntries = true)
  public void deleteById(int id) {
    nodeRepository.deleteById(id);
  }

  @Cacheable
  public Node findById(int id) {
    return nodeRepository.findById(id);
  }

  @Cacheable
  public Node findByValue(String value) {
    return nodeRepository.findByValue(value);
  }

  public void dealNodeTopicCount(Node oldNode, Node newNode) {
    if(oldNode.getId() != newNode.getId()) {
      //原来的节点话题数-1
      oldNode.setTopicCount(oldNode.getTopicCount() - 1);
      this.save(oldNode);
      //原来的节点话题数+1
      newNode.setTopicCount(newNode.getTopicCount() + 1);
      this.save(newNode);
    }
  }
}