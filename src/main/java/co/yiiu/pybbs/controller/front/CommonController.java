package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.controller.api.BaseApiController;
import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.FileUtil;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.StringUtil;
import co.yiiu.pybbs.util.captcha.Captcha;
import co.yiiu.pybbs.util.captcha.GifCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseApiController {

  // gif 验证码
  @GetMapping("/captcha")
  public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
    Captcha captcha = new GifCaptcha();
    captcha.out(response.getOutputStream());
    String text = captcha.text();
    session.setAttribute("_captcha", text);
  }

}
