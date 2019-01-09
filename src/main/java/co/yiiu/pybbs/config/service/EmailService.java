package co.yiiu.pybbs.config.service;

import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
// 这个工具类来自博客：https://www.cnblogs.com/whgk/p/6506027.html
@Component
@DependsOn("mybatisPlusConfig")
public class EmailService implements BaseService<Session> {

  @Autowired
  private SystemConfigService systemConfigService;

  private Session session;
  private Logger log = LoggerFactory.getLogger(EmailService.class);

  @Override
  public Session instance() {
    // 如果session已经存在了，就不执行了，直接返回对象
    if (session != null) return session;
    // session为空，判断系统是否配置了邮箱相关的参数，配置了继续，没配置白白
    SystemConfig systemConfigHost = systemConfigService.selectByKey("mail_host");
    String host = systemConfigHost.getValue();
    SystemConfig systemConfigUsername = systemConfigService.selectByKey("mail_username");
    String username = systemConfigUsername.getValue();
    SystemConfig systemConfigPassword = systemConfigService.selectByKey("mail_password");
    String password = systemConfigPassword.getValue();
    if (StringUtils.isEmpty(host) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return null;
    Properties properties = new Properties();
    properties.setProperty("mail.host", host);
    //是否进行权限验证。
    properties.setProperty("mail.smtp.auth", "true");
    //0.2确定权限（账号和密码）
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };
    //1 获得连接
    /*
      props：包含配置信息的对象，Properties类型
              配置邮箱服务器地址、配置是否进行权限验证(帐号密码验证)等
      authenticator：确定权限(帐号和密码)
      所以就要在上面构建这两个对象。
     */
    this.session = Session.getDefaultInstance(properties, authenticator);
    return this.session;
  }

  public boolean sendEmail(String email, String title, String content) {
    // 先判断session是否初始化了，没配置直接失败
    if (this.instance() == null) return false;
    try {
      //2 创建消息
      Message message = new MimeMessage(this.session);
      String from = systemConfigService.selectAllConfig().get("mail_username").toString();
      // 2.1 发件人 xxx@163.com 我们自己的邮箱地址，就是名称
      message.setFrom(new InternetAddress(from));
      /*
        2.2 收件人
          第一个参数：
            RecipientType.TO    代表收件人
            RecipientType.CC    抄送
            RecipientType.BCC    暗送
          比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
          将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
          而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
          第二个参数
            收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
            这里我们发送给我们的qq邮箱
       */
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
      // 2.3 主题（标题）
      message.setSubject(title + " - " + systemConfigService.selectAllConfig().get("name").toString());
      // 2.4 正文
      //设置编码，防止发送的内容中文乱码。
      message.setContent(content, "text/html;charset=UTF-8");
      //3发送消息
      Transport.send(message);
      return true;
    } catch (MessagingException e) {
      log.error(e.getMessage());
      return false;
    }
  }
}
