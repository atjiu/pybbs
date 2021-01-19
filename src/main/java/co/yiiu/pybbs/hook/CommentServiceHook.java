package co.yiiu.pybbs.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class CommentServiceHook {

    @Pointcut("execution(public * co.yiiu.pybbs.service.ICommentService.selectByTopicId(..))")
    public void selectByTopicId() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ICommentService.insert(..))")
    public void insert() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ICommentService.update(..))")
    public void update() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ICommentService.vote(..))")
    public void vote() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.ICommentService.delete(..))")
    public void delete() {
    }

}
