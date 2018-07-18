package co.yiiu.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by tomoya at 2018/3/29
 */
@Component
@Slf4j
public class EmailUtil {

  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private Environment env;

  public void sendEmail(String email, String subject, String content) {
    new Thread(() -> {
      try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setTo(email);

        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(mimeMessage);
      } catch (MessagingException e) {
        log.error(e.getLocalizedMessage());
      }
    }).start();
  }
}
