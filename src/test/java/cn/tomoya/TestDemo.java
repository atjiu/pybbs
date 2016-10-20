package cn.tomoya;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Log4j
public class TestDemo {

    @Test
    public void test1() {
        String dh = ",";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 10000000; i++) {
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
        log.info(new BCryptPasswordEncoder().encode("123123"));
    }
}
