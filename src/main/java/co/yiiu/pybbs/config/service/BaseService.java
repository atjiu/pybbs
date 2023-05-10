package co.yiiu.pybbs.config.service;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://atjiu.github.io
 */
public interface BaseService<T> {

    // 外接服务初始化实例方法
    T instance();
}
