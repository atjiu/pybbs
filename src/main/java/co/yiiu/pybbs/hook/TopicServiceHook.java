package co.yiiu.pybbs.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class TopicServiceHook {

    @Pointcut("execution(public * co.yiiu.pybbs.service.ITopicService.search(..))")
    public void search() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ITopicService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ITopicService.update(..))")
    public void update() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ITopicService.vote(..))")
    public void vote() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ITopicService.updateViewCount(..))")
    public void updateViewCount() {
    }

}
