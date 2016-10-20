package cn.tomoya.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Setter
@Getter
public class Result<T> {

    private int code;
    private String description;
    private T detail;

}
