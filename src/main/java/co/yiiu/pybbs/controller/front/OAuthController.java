package co.yiiu.pybbs.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController extends BaseController {

  // 使用github联合登录
  @GetMapping("/github")
  public String github() {
    // TODO
    return null;
  }

  // github联合登录后的回调
  @GetMapping("/github/callback")
  public String callback() {
    // TODO
    return null;
  }
}
