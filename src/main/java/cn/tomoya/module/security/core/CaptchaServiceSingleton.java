package cn.tomoya.module.security.core;

import org.apache.log4j.Logger;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

public class CaptchaServiceSingleton {

  private Logger log = Logger.getLogger(CaptchaServiceSingleton.class);

  private static GenericManageableCaptchaService instance;

  public void setInstance(GenericManageableCaptchaService instance) {
    CaptchaServiceSingleton.instance = instance;
    log.info("CaptchaServiceSingleton.instance...");
  }

  public static ImageCaptchaService getInstance() {
    return instance;
  }
}