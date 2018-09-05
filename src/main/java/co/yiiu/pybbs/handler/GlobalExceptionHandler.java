package co.yiiu.pybbs.handler;

import co.yiiu.pybbs.exceptions.ApiException;
import co.yiiu.pybbs.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya at 2018/9/3
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ApiException.class)
  @ResponseBody
  public Result jsonErrorHandler(ApiException e) {
    Result result = new Result();
    result.setCode(e.getCode());
    result.setDescription(e.getMessage());
    return result;
  }

}
