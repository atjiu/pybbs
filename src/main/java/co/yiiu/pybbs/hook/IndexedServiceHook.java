package co.yiiu.pybbs.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class IndexedServiceHook {

    @Pointcut("execution(public * co.yiiu.pybbs.service.IIndexedService.indexAllTopic(..))")
    public void indexAllTopic() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IIndexedService.indexTopic(..))")
    public void indexTopic() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IIndexedService.deleteTopicIndex(..))")
    public void deleteTopicIndex() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IIndexedService.batchDeleteIndex(..))")
    public void batchDeleteIndex() {
    }

}
