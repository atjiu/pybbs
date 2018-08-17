package co.yiiu.module.log.service;

import co.yiiu.config.properties.LogEventConfig;
import co.yiiu.core.bean.Page;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.log.mapper.LogMapper;
import co.yiiu.module.log.pojo.LogEventEnum;
import co.yiiu.module.log.pojo.LogWithBLOBs;
import co.yiiu.module.topic.pojo.Topic;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Service
public class LogService {

  @Autowired
  private FreemarkerUtil freemarkerUtil;
  @Autowired
  private LogMapper logMapper;
  @Autowired
  private LogEventConfig logEventConfig;

  public void save(LogWithBLOBs log) {
    logMapper.insertSelective(log);
  }

  public Page<LogWithBLOBs> findByUserId(Integer pageNo, Integer pageSize, Integer userId) {
    List<LogWithBLOBs> list = logMapper.findByUserId(userId, (pageNo - 1) * pageSize, pageSize, "id desc");
    int count = logMapper.countByUserId(userId);
    return new Page<>(pageNo, pageSize, count, list);
  }

  public void deleteByUserId(Integer userId) {
    logMapper.deleteByUserId(userId);
  }

  public Page<Map> findAllForAdmin(Integer pageNo, Integer pageSize) {
    List<Map> list = logMapper.findAllForAdmin((pageNo - 1) * pageSize, pageSize, "l.id desc");
    int count = logMapper.countAllForAdmin();
    return new Page<>(pageNo, pageSize, count, list);
  }

  public LogWithBLOBs save(LogEventEnum event, Integer userId, String target, Integer targetId, String before, String after, Topic topic) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("topic", topic);
    String desc = freemarkerUtil.format(logEventConfig.getTemplate().get(event.getName()), params);
    LogWithBLOBs log = new LogWithBLOBs();
    log.setEvent(event.getEvent());
    log.setEventDescription(desc);
    log.setUserId(userId);
    log.setTarget(target);
    log.setTargetId(targetId);
    log.setBeforeContent(before);
    log.setAfterContent(after);
    log.setInTime(new Date());
    save(log);
    return log;
  }

}
