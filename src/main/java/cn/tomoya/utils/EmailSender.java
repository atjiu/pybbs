package cn.tomoya.utils;

import com.jfinal.kit.PropKit;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class EmailSender {

    public static final String EMAIL_BODY_HEADER = "";
    // 邮箱服务器
    private String host;
    private String MAIL_SUBJECT = "测试邮件";
    private String sender;
    private String username;
    private String password;
    private String mail_from;
    private String charset = "utf-8";

    private static EmailSender emailSender;
    static{
        PropKit.use("config.properties");
    }
    public static EmailSender getInstance() {
        if (emailSender == null) {
            emailSender = new EmailSender();
        }
        return emailSender;
    }

    public EmailSender() {
        this.host = PropKit.get("email.smtp");
        this.username = PropKit.get("email.username");
        this.password = PropKit.get("email.password");
        this.mail_from = username;
        this.sender = PropKit.get("email.sender");
    }

    /**
     * 此段代码用来发送普通电子邮件
     */
    public void send(String subject, String[] mailTo, String mailBody) throws Exception {
        try {
            Properties props = new Properties(); // 获取系统环境
            Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            // notification.setContent("foobar, "application/x-foobar"); // 设置邮件格式
            message.setSubject(subject == null ? MAIL_SUBJECT : subject, charset); // 设置邮件主题
            message.setText("<html><head><meta charset='utf-8'></head><body>" + mailBody + "</body></html>", charset, "html"); // 设置邮件正文
//          notification.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(mail_from, sender);
            message.setFrom(address); // 设置邮件发送者的地址
            Address toAddress = null;
            for (int i = 0; i < mailTo.length; i++) {
                toAddress = new InternetAddress(mailTo[i]); // 设置邮件接收方的地址
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            toAddress = null;
            Transport.send(message); // 发送邮件
            System.out.println("send ok!");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 用来进行服务器对用户的认证
     */
    public class Email_Autherticator extends Authenticator {
        public Email_Autherticator() {
            super();
        }

        public Email_Autherticator(String user, String pwd) {
            super();
            username = user;
            password = pwd;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    public static void sendMail(String title, String[] mailTo, String content) {
        String mailBody = EMAIL_BODY_HEADER + content;
        try {
            EmailSender.getInstance().send(title, mailTo, mailBody);
        } catch (Exception e) {
            System.out.println("email send error:" + mailBody);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMail(null, new String[]{""}, "测试邮件内容");
    }

}
