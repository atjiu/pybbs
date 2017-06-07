package cn.tomoya.util;

import cn.tomoya.config.yml.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Component
public class FileUtil {

  @Autowired
  private SiteConfig siteConfig;

  /**
   * 上传文件
   *
   * @param file
   * @param fileUploadEnum
   * @return
   * @throws IOException
   */
  public String uploadFile(MultipartFile file, FileUploadEnum fileUploadEnum) throws IOException {
    if (!file.isEmpty()) {
      String type = file.getContentType();
      String suffix = "." + type.split("/")[1];
      String fileName = UUID.randomUUID().toString() + suffix;
      BufferedOutputStream stream = null;
      String requestPath = null;
      if (fileUploadEnum == FileUploadEnum.FILE) {
        File file_dir = new File(siteConfig.getUploadPath());
        if(!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(new FileOutputStream(new File(siteConfig.getUploadPath() + fileName)));
        requestPath = siteConfig.getStaticUrl();
      } else if (fileUploadEnum == FileUploadEnum.AVATAR) {
        File file_dir = new File(siteConfig.getUploadPath() + "avatar/");
        if(!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(
            new FileOutputStream(new File(siteConfig.getUploadPath() + "avatar/" + fileName)));
        requestPath = siteConfig.getStaticUrl() + "avatar/";
      }
      if (stream != null) {
        stream.write(file.getBytes());
        stream.close();
        return requestPath + fileName;
      }
    }
    return null;
  }
}
