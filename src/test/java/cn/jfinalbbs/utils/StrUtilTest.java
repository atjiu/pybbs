package cn.jfinalbbs.utils;

import com.jfinal.kit.EncryptionKit;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class StrUtilTest {

    @Test
    public void testGetUuid() throws Exception {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println("551200e6d792542a29789a43".length());
    }

    @Test
    public void testRandom() {
        for(int i = 0; i < 100; i++) {
            Random random = new Random();
            System.out.println(random.nextInt(10));
        }
    }

    @Test
    public void testAppend() {
        StringBuffer sb = new StringBuffer();
        sb.append("<url><loc>");
        sb.append("http://jfinalbbs.liygheart.com/topic/f713bbc7583e43fcb558bdb39ef4949c.html");
        sb.append("</loc><priority>0.5</priority><lastmod>2015-04-09</lastmod><changefreq>always</changefreq></url>");
        System.out.println(sb.toString());
    }

    @Test
    public void testMd5() {
        System.out.println(EncryptionKit.md5Encrypt("asd"));
    }
}