package co.yiiu.pybbs.exception;

import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.HttpUtil;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private ISystemConfigService systemConfigService;

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 错误页面统一处理
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        // 当报错了，又不知道啥错的时候，把下面这行代码打开，就可以看到报错的堆信息了
        e.printStackTrace();
//        log.error(e.getMessage());
        if (!HttpUtil.isApiRequest(request)) {
            response.setCharacterEncoding("utf-8");
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", e);
            mav.addObject("errorCode", getStatus(request));
            mav.setViewName("theme/" + systemConfigService.selectAllConfig().get("theme").toString() + "/error");
            return mav;
        } else /*if (accept.contains("application/json"))*/ {
            Result result = new Result();
            result.setCode(201);
            result.setDescription("服务器出错啦~");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(Optional.ofNullable(JsonUtil.objectToJson(result)).orElse(""));
        }
        return null;
    }

    /**
     * 接口错误统一处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public Result jsonErrorHandler(ApiException e) {
        log.info("api exception: {}", e.getMessage());
        Result result = new Result();
        result.setCode(e.getCode());
        result.setDescription(e.getMessage());
        return result;
    }

    /**
     * 没有权限的请求异常处理
     *
     * @param e
     * @return
     * @throws UnauthenticatedException
     */
    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public ResponseEntity<String> jsonErrorHandler(HttpServletRequest request, HttpServletResponse response, UnauthenticatedException e) throws IOException {
        log.error(e.getMessage());
        if (HttpUtil.isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            response.sendRedirect("/adminlogin");
            return null;
        }
    }

    /**
     * 没有权限的请求异常处理
     *
     * @param e
     * @return
     * @throws AuthorizationException
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result jsonErrorHandler(HttpServletRequest request, HttpServletResponse response, AuthorizationException e) throws IOException {
        log.error(e.getMessage());
        if (HttpUtil.isApiRequest(request)) {
            Result result = new Result();
            result.setCode(201);
            result.setDescription("没有权限进行这次操作");
            return result;
        } else {
            response.sendRedirect("/adminlogin");
            return null;
        }
    }

}
