package cn.tomoya.exception;

import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * error page unified processing
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("errorCode", getStatus(request));
        mav.addObject("pageTitle", localeMessageSourceUtil.getMessage("site.page.error"));
        mav.setViewName(settingService.getTheme() + "/error");
        return mav;
    }

    /**
     * interface error unified processing
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public Result jsonErrorHandler(ApiException e) throws Exception {
        Result result = new Result<>();
        result.setCode(e.getCode());
        result.setDescription(e.getMessage());
        return result;
    }
}