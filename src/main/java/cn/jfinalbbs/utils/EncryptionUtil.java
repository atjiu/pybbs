package cn.jfinalbbs.utils;

import com.jfinal.kit.EncryptionKit;

/**
 * Created by liuyang on 15/4/21.
 */
public class EncryptionUtil extends EncryptionKit {

    /**
     * 码表;
     */
    public static char[] encodeTable = {'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    /**
     * Base64的编码;
     *
     * @param value
     * @return
     */
    public static String encoderBase64(byte[] value) {
        StringBuilder sb = new StringBuilder();
        //获取编码字节是3的倍数;
        int len = value.length;
        int len3 = len / 3;
        //先处理没有加换行符;
        for (int i = 0; i < len3; i++) {

            //得到第一个字符;
            int b1 = (value[i * 3] >> 2) & 0x3F;
            char c1 = encodeTable[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = ((value[i * 3] << 4 & 0x3F) + (value[i * 3 + 1] >> 4)) & 0x3F;
            char c2 = encodeTable[b2];
            sb.append(c2);

            //得到第三个字符;
            int b3 = ((value[i * 3 + 1] << 2 & 0x3C) + (value[i * 3 + 2] >> 6)) & 0x3F;
            char c3 = encodeTable[b3];
            sb.append(c3);

            //得到第四个字符;
            int b4 = value[i * 3 + 2] & 0x3F;
            char c4 = encodeTable[b4];
            sb.append(c4);

        }

        //如果有剩余的字符就补0;
        //剩余的个数;
        int less = len % 3;
        if (less == 1) {//剩余一个字符--补充两个等号;;

            //得到第一个字符;
            int b1 = value[len3 * 3] >> 2 & 0x3F;
            char c1 = encodeTable[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = (value[len3 * 3] << 4 & 0x30) & 0x3F;
            char c2 = encodeTable[b2];
            sb.append(c2);
            sb.append("==");

        } else if (less == 2) {//剩余两个字符--补充一个等号;

            //得到第一个字符;
            int b1 = value[len3 * 3] >> 2 & 0x3F;
            char c1 = encodeTable[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = ((value[len3 * 3] << 4 & 0x30) + (value[len3 * 3 + 1] >> 4)) & 0x3F;
            char c2 = encodeTable[b2];
            sb.append(c2);

            //得到第三个字符;
            int b3 = (value[len3 * 3 + 1] << 2 & 0x3C) & 0x3F;
            char c3 = encodeTable[b3];
            sb.append(c3);
            sb.append("=");

        }

        return sb.toString();
    }

    /**
     * Base64的解码;
     *
     * @param value
     * @return
     */
    public static String decoderBase64(byte[] value) {

        //每四个一组进行解码;
        int len = value.length;
        int len4 = len / 4;
        StringBuilder sb = new StringBuilder();
        //除去末尾的四个可能特殊的字符;
        int i = 0;
        for (i = 0; i < len4 - 1; i++) {

            //第一个字符;
            byte b1 = (byte) ((char2Index((char) value[i * 4]) << 2) + (char2Index((char) value[i * 4 + 1]) >> 4));
            sb.append((char) b1);
            //第二个字符;
            byte b2 = (byte) ((char2Index((char) value[i * 4 + 1]) << 4)
                    + (char2Index((char) value[i * 4 + 2]) >> 2));
            sb.append((char) b2);
            //第三个字符;
            byte b3 = (byte) ((char2Index((char) value[i * 4 + 2]) << 6) + (char2Index((char) value[i * 4 + 3])));
            sb.append((char) b3);

        }

        //处理最后的四个字符串;
        for (int j = 0; j < 3; j++) {
            int index = i * 4 + j;
            if ((char) value[index + 1] != '=') {

                if (j == 0) {
                    byte b = (byte) ((char2Index((char) value[index]) << 2)
                            + (char2Index((char) value[index + 1]) >> 4));
                    sb.append((char) b);
                } else if (j == 1) {
                    byte b = (byte) ((char2Index((char) value[index]) << 4)
                            + (char2Index((char) value[index + 1]) >> 2));
                    sb.append((char) b);
                } else if (j == 2) {
                    byte b = (byte) ((char2Index((char) value[index]) << 6)
                            + (char2Index((char) value[index + 1])));
                    sb.append((char) b);
                }

            } else {
                break;
            }
        }

        return sb.toString();
    }

    /**
     * 将码表中的字符映射到索引值;
     *
     * @param ch
     * @return
     */
    public static int char2Index(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A';
        } else if (ch >= 'a' && ch <= 'z') {
            return 26 + ch - 'a';
        } else if (ch >= '0' && ch <= '9') {
            return 52 + ch - '0';
        } else if (ch == '+') {
            return 62;
        } else if (ch == '/') {
            return 63;
        }
        return 0;
    }
}
