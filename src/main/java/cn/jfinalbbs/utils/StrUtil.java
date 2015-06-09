package cn.jfinalbbs.utils;

import com.jfinal.kit.StrKit;

import java.util.UUID;

/**
 * Created by liuyang on 15/4/2.
 */
public class StrUtil extends StrKit {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getEncryptionToken(String token) {
        for(int i = 0; i < 6; i++) {
            token = EncryptionUtil.encoderBase64(token.getBytes());
        }
        return token;
    }

    public static String getDecryptToken(String encryptionToken) {
        for(int i = 0; i < 6; i++) {
            encryptionToken = EncryptionUtil.decoderBase64(encryptionToken.getBytes());
        }
        return encryptionToken;
    }

}
