package co.yiiu.core.util;

import co.yiiu.config.SiteConfig;
import co.yiiu.module.attachment.model.Attachment;
import co.yiiu.module.attachment.service.AttachmentService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Slf4j
public class FileUtil {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private AttachmentService attachmentService;

  /**
   * upload file
   *
   * @param file
   * @param fileType
   * @return
   * @throws IOException
   */
  public Attachment uploadFile(MultipartFile file, FileType fileType, String username) throws IOException {
    String suffix = "." + file.getContentType().split("/")[1];
    Integer width = null, height = null;
    // 如果是图片，计算图片长宽
    if (fileType.equals(FileType.PICTURE)) {
      BufferedImage image = ImageIO.read(file.getInputStream());
      width = image.getWidth();
      height = image.getHeight();
    }
    String size = getFileSize(file.getSize());
    String fileName = UUID.randomUUID().toString() + suffix;
    String today = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE);
    // 判断要上传的文件夹是否存在，不存在则创建
    File localPathDir = new File(siteConfig.getUploadPath() + username + File.separator + today);
    if (!localPathDir.exists()) localPathDir.mkdirs();

    String localPath = siteConfig.getUploadPath() + username + File.separator + today + File.separator + fileName;
    String requestUrl = siteConfig.getStaticUrl() + username + File.separator + today + File.separator + fileName;
    String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());

    // 上传文件
    @Cleanup BufferedOutputStream stream = new BufferedOutputStream(
        new FileOutputStream(new File(localPath))
    );
    stream.write(file.getBytes());

    // 构建Attachment对象
    return attachmentService.createAttachment(localPath, fileName, requestUrl, fileType.name(), width, height, size, suffix, md5);
  }

  public Map<String, Object> getFileSize(MultipartFile file) {
    Map<String, Object> map = new HashMap<>();
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      String suffix = "." + file.getContentType().split("/")[1];
      int width = image.getWidth();
      int height = image.getHeight();
      map.put("suffix", suffix);
      map.put("width", width);
      map.put("height", height);
      map.put("size", getFileSize(file.getSize()));
      return map;
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
    }
    return null;
  }

  // 格式化文件大小
  public String getFileSize(long longSize) {
    String fileSize;
    long SIZE_BT = 1024L;
    long SIZE_KB = SIZE_BT * 1024;
    long SIZE_MB = SIZE_KB * 1024;
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
    if (longSize == 0) {
      fileSize = "0";
    } else if (longSize < SIZE_BT && longSize > 0) {// <1K
      fileSize = longSize + "B";
    } else if (longSize >= SIZE_BT && longSize < SIZE_KB) {// >1K and <1M
      fileSize = df.format(((double) longSize / (double) SIZE_BT)) + "K";
    } else if (longSize >= SIZE_KB && longSize < SIZE_MB) {// >1M and
      // <1G
      fileSize = df.format(((double) longSize / (double) SIZE_KB)) + "M";
    } else {
      fileSize = "1G";
    }
    return fileSize;
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
