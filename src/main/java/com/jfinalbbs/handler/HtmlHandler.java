package com.jfinalbbs.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class HtmlHandler extends Handler {
    @Override
    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean[] booleans) {
        if(s.lastIndexOf(".html") != -1) {
            s = s.substring(0, s.indexOf(".html"));
        }
        nextHandler.handle(s, httpServletRequest, httpServletResponse, booleans);
    }
}
