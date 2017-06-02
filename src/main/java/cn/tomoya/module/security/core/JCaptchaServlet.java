package cn.tomoya.module.security.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@WebServlet("/captcha.jpg")
public class JCaptchaServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
      throws ServletException, IOException {
    byte[] captchaChallengeAsJpeg = null;
    // 输出jpg的字节流
    ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
    try {
      // get the session id that will identify the generated captcha.
      // the same id must be used to validate the response, the session id is a
      // good candidate!
      String captchaId = httpServletRequest.getSession().getId();
      // call the ImageCaptchaService getChallenge method
      BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,
          httpServletRequest.getLocale());

      // a jpeg encoder
      JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
      jpegEncoder.encode(challenge);

    } catch (IllegalArgumentException e) {
      httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    } catch (CaptchaServiceException e) {
      httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
    // flush it in the response
    httpServletResponse.setHeader("Cache-Control", "no-store");
    httpServletResponse.setHeader("Pragma", "no-cache");
    httpServletResponse.setDateHeader("Expires", 0);
    httpServletResponse.setContentType("image/jpeg");
    ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
    responseOutputStream.write(captchaChallengeAsJpeg);
    responseOutputStream.flush();
    responseOutputStream.close();
  }
}
