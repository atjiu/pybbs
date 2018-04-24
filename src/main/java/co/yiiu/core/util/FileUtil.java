package co.yiiu.core.util;

import co.yiiu.config.SiteConfig;
import co.yiiu.module.attachment.model.Attachment;
import co.yiiu.module.attachment.service.AttachmentService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
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
   * 上传文件到本地
   *
   * @param file
   * @param fileType
   * @return
   * @throws IOException
   */
  public Attachment uploadFile(MultipartFile file, FileType fileType, String username) throws IOException {
    Map<String, Object> map = getFileSize(file);
    String suffix = (String) map.get("suffix");
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
    return attachmentService.createAttachment(localPath, fileName, requestUrl, fileType.name(),
        (Integer) map.get("width"), (Integer) map.get("height"), (String) map.get("size"), suffix, md5);
  }

  /**
   * 上传文件到七牛
   *
   * @param file
   * @param fileType
   * @return
   * @throws IOException
   */
  public Attachment uploadFileToQiniu(MultipartFile file, FileType fileType) throws IOException {
    Map<String, Object> map = getFileSize(file);
    String suffix = (String) map.get("suffix");
    String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
    //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone2());
    //...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);
    Auth auth = Auth.create(siteConfig.getUpload().getQiniu().getAccessKey(), siteConfig.getUpload().getQiniu().getSecretKey());
    String uploadToken = auth.uploadToken(siteConfig.getUpload().getQiniu().getBucket());
    Response response = uploadManager.put(file.getInputStream(), null, uploadToken, null, null);
    DefaultPutRet defaultPutRet = JsonUtil.jsonToObject(response.bodyString(), DefaultPutRet.class);
    String requestUrl = siteConfig.getUpload().getQiniu().getDomain() + defaultPutRet.key;
    // 将图片信息保存在数据库
    return attachmentService.createAttachment(null, defaultPutRet.key, requestUrl, fileType.name(),
        (Integer) map.get("width"), (Integer) map.get("height"), (String) map.get("size"), suffix, md5);
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
