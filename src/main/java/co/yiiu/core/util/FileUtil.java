package co.yiiu.core.util;

import co.yiiu.config.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class FileUtil {

  @Autowired
  private SiteConfig siteConfig;

  /**
   * upload file
   *
   * @param file
   * @param fileUploadEnum
   * @return
   * @throws IOException
   */
  public String uploadFile(MultipartFile file, FileUploadEnum fileUploadEnum, String username) throws IOException {
    if (!file.isEmpty()) {
      String type = file.getContentType();
      String suffix = "." + type.split("/")[1];
      String fileName = null;
      BufferedOutputStream stream = null;
      String requestPath = null;

      // upload file
      if (fileUploadEnum == FileUploadEnum.FILE) {
        String today = DateUtil.formatDate(new Date());
        String userUploadPath = username + "/" + today + "/";
        fileName = UUID.randomUUID().toString() + suffix;
        File file_dir = new File(siteConfig.getUploadPath() + userUploadPath);
        if (!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(new FileOutputStream(new File(siteConfig.getUploadPath() + userUploadPath + fileName)));
        requestPath = siteConfig.getStaticUrl() + userUploadPath;
      }

      // upload avatar (image)
      if (fileUploadEnum == FileUploadEnum.AVATAR) {
        String userAvatarPath = username + "/";
        fileName = "avatar" + suffix;
        File file_dir = new File(siteConfig.getUploadPath() + userAvatarPath);
        if (!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(
            new FileOutputStream(new File(siteConfig.getUploadPath() + userAvatarPath + fileName)));
        requestPath = siteConfig.getStaticUrl() + userAvatarPath;
      }

      if (stream != null) {
        stream.write(file.getBytes());
        stream.close();
        return requestPath + fileName;
      }
    }
    return null;
  }

  /**
   * search username upload dir's space size
   *
   * @param file
   * @return
   */
  public long getTotalSizeOfFilesInDir(File file) {
    if (file.isFile())
      return file.length();
    File[] children = file.listFiles();
    long total = 0;
    if (children != null)
      for (File child : children)
        total += getTotalSizeOfFilesInDir(child);
    return total;
  }
}
