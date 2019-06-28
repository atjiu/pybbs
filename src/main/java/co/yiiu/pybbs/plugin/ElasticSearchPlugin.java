package co.yiiu.pybbs.plugin;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Aspect
public class ElasticSearchPlugin {

  @Autowired
  private ElasticSearchService elasticSearchService;

  @Around("co.yiiu.pybbs.hook.TopicServiceHook.search()")
  public Object search(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object[] args = proceedingJoinPoint.getArgs();
    if (elasticSearchService.instance() == null) return proceedingJoinPoint.proceed(args);
    return elasticSearchService.searchDocument((Integer) args[0], (Integer) args[1], (String) args[2], "title", "content");
  }
}
