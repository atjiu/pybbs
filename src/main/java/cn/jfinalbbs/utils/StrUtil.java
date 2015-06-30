package cn.jfinalbbs.utils;

import com.jfinal.kit.StrKit;

import java.util.Random;
import java.util.UUID;

/**
 * Created by liuyang on 15/4/2.
 */
public class StrUtil extends StrKit {

    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static final Random rand = new Random();

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String randomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(hexDigits[rand.nextInt(hexDigits.length)]);
        }
        return sb.toString();
    }

    public static String randomNumber(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(digits[rand.nextInt(digits.length)]);
        }
        return sb.toString();
    }

    public static String getEncryptionToken(String token) {
        for (int i = 0; i < 6; i++) {
            token = EncryptionUtil.encoderBase64(token.getBytes());
        }
        return token;
    }

    public static String getDecryptToken(String encryptionToken) {
        for (int i = 0; i < 6; i++) {
            encryptionToken = EncryptionUtil.decoderBase64(encryptionToken.getBytes());
        }
        return encryptionToken;
    }

    public static void main(String[] args) {
        System.out.println(StrUtil.getUUID());
    }

}
