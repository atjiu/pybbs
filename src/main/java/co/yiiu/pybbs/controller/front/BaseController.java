package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseController {

  protected String redirect(String path) {
    return "redirect:" + path;
  }

  protected User getUser() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    HttpSession session = request.getSession();
    return (User) session.getAttribute("_user");
  }

}
