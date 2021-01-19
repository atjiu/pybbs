package co.yiiu.pybbs.hook;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class UserServiceHook {

    @Pointcut("execution(public * co.yiiu.pybbs.service.IUserService.selectByUsername(..))")
    public void selectByUsername() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IUserService.selectByToken(..))")
    public void selectByToken() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IUserService.selectById(..))")
    public void selectById() {
    }

    @Pointcut("execution(public * co.yiiu.pybbs.service.IUserService.delRedisUser(..))")
    public void delRedisUser() {
    }

}
