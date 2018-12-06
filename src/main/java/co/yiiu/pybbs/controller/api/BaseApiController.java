package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.controller.front.BaseController;
import co.yiiu.pybbs.util.Result;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseApiController extends BaseController {

  protected Result success() {
    return success(null);
  }

  protected Result success(Object detail) {
    Result result = new Result();
    result.setCode(200);
    result.setDescription("SUCCESS");
    result.setDetail(detail);
    return result;
  }

  protected Result error(String description) {
    Result result = new Result();
    result.setCode(201);
    result.setDescription(description);
    return result;
  }
}
