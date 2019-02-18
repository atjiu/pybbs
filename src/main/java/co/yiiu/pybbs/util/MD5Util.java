package co.yiiu.pybbs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class MD5Util {

  private static Logger log = LoggerFactory.getLogger(MD5Util.class);

  private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
      'c', 'd', 'e', 'f'};
  private static MessageDigest messagedigest = null;

  static {
    try {
      messagedigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      log.error("{}初始化失败，MessageDigest不支持MD5Util, errorMessage: {}", MD5Util.class.getName(), e.getMessage());
    }
  }

  private MD5Util() {}

  /**
   * 功能：加盐版的MD5.返回格式为MD5(密码+{盐值})
   *
   * @param password 密码
   * @param salt     盐值
   * @return String
   * @author 郑晓鹏
   * @date 2014年06月24日
   */
  public static String getMD5StringWithSalt(String password, String salt) {
    if (password == null) {
      throw new IllegalArgumentException("password不能为null");
    }
    if (salt.equals("") || salt.length() == 0) {
      throw new IllegalArgumentException("salt不能为空");
    }
    if ((salt.lastIndexOf('{') != -1) || (salt.lastIndexOf('}') != -1)) {
      throw new IllegalArgumentException("salt中不能包含 { 或者 }");
    }
    return getMD5String(password + '{' + salt + '}');
  }

  /**
   * 功能：得到文件的md5值。
   *
   * @param file 文件。
   * @return String
   * @throws IOException 读取文件IO异常时。
   * @author 郑晓鹏
   * @date 2014年06月24日
   */
  public static String getFileMD5String(File file) throws IOException {
    FileInputStream in = new FileInputStream(file);
    FileChannel ch = in.getChannel();
    MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
    messagedigest.update(byteBuffer);
    return bufferToHex(messagedigest.digest());
  }

  /**
   * 功能：得到一个字符串的MD5值。
   *
   * @param str 字符串
   * @return String
   * @author 郑晓鹏
   * @date 2014年06月24日
   */
  public static String getMD5String(String str) {
    return getMD5String(str.getBytes());
  }

  private static String getMD5String(byte[] bytes) {
    messagedigest.update(bytes);
    return bufferToHex(messagedigest.digest());
  }

  private static String bufferToHex(byte[] bytes) {
    return bufferToHex(bytes, 0, bytes.length);
  }

  private static String bufferToHex(byte[] bytes, int m, int n) {
    StringBuffer stringbuffer = new StringBuffer(2 * n);
    int k = m + n;
    for (int l = m; l < k; l++) {
      appendHexPair(bytes[l], stringbuffer);
    }
    return stringbuffer.toString();
  }

  private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
    char c0 = hexDigits[(bt & 0xf0) >> 4];
    char c1 = hexDigits[bt & 0xf];
    stringbuffer.append(c0);
    stringbuffer.append(c1);
  }

  /*
   * 哈希摘摘要
   */
  public static String hexdigest(byte[] paramArrayOfByte) {
    try {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      char[] arrayOfChar = new char[32];
      int i = 0;
      int j = 0;
      for (; ; ) {
        if (i >= 16) {
          return new String(arrayOfChar);
        }
        int k = arrayOfByte[i];
        int m = j + 1;
        arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
        j = m + 1;
        arrayOfChar[m] = hexDigits[(k & 0xF)];
        i++;
      }

    } catch (Exception localException) {
      return null;
    }
  }
}
