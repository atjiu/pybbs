package cn.tomoya;

import cn.tomoya.config.base.BaseEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class TestDemo {

  Logger log = LoggerFactory.getLogger(TestDemo.class);

  @Test
  public void test1() {
    String dh = ",";
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < 10000000; i++) {
      sb.append(i).append(dh);
    }
    String str = sb.toString();
    long start = System.currentTimeMillis();
    String[] strs = str.split(dh);
    List list = Arrays.asList(strs);
    boolean b = list.contains("57192");
    System.out.println(System.currentTimeMillis() - start);
    start = System.currentTimeMillis();
    str = str.replace("3123,", "") + "123123123,";
    System.out.println(System.currentTimeMillis() - start);
  }

  @Test
  public void test2() {
    Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    log.info(md5PasswordEncoder.encodePassword("123123", "tomoya"));
  }

  @Test
  public void test3() {
    log.info(UUID.randomUUID().toString());
    log.info(new BCryptPasswordEncoder().encode("123123"));
  }

  @Test
  public void test4() {
    String test = "@dd 1 @bb 2";
    List<String> list = BaseEntity.fetchUsers(null, test);
    for (String s : list) {
      System.out.println(s);
    }
  }

  @Test
  public void test5() {
    Map map = new HashMap();
    map.put("tab", "123");
    String tab = map.get("tab").toString();
//    String tab1 = map.get("tab1").toString();//java.lang.NullPointerException
    log.info("tab: {}, test: {}", tab, map.get("tab2"));
  }

}
