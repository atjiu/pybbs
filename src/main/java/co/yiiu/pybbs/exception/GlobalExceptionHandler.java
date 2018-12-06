package co.yiiu.pybbs.exception;

import co.yiiu.pybbs.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@ControllerAdvice
public class GlobalExceptionHandler {

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
  public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
    e.printStackTrace();
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("errorCode", getStatus(request));
    mav.setViewName("front/error");
    return mav;
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
  public Result jsonErrorHandler(ApiException e) throws Exception {
    Result result = new Result();
    result.setCode(e.getCode());
    result.setDescription(e.getMessage());
    return result;
  }
}
