package co.yiiu.pybbs.util;

import co.yiiu.pybbs.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class FileUtil {

  private Logger log = LoggerFactory.getLogger(FileUtil.class);

  @Autowired
  private SystemConfigService systemConfigService;

  /**
   * 上传文件
   * @param file 要上传的文件对象
   * @param fileName 文件名，可以为空，为空的话，就生成一串uuid代替文件名
   * @param customPath 自定义存放路径，这个地址是跟在数据库里配置的路径后面的，
   *                   格式类似 avatar/admin 前后没有 / 前面表示头像，后面是用户的昵称，举例，如果将用户头像全都放在一个文件夹里，这里可以直接传个 avatar
   * @return
   */
  public String upload(MultipartFile file, String fileName, String customPath) {
    try {
      if (file == null || file.isEmpty()) return null;

      if (StringUtils.isEmpty(fileName)) fileName = StringUtil.uuid();
      String suffix = "." + file.getContentType().split("/")[1];
      // 如果存放目录不存在，则创建
      File savePath = new File(systemConfigService.selectAllConfig().get("upload_path").toString() + customPath);
      if (!savePath.exists()) savePath.mkdirs();

      // 给上传的路径拼上文件名与后缀
      String localPath = systemConfigService.selectAllConfig().get("upload_path").toString() + customPath + "/" + fileName + suffix;

      // 上传文件
      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(localPath)));
      stream.write(file.getBytes());
      stream.close();

      // 上传成功后返回访问路径
      return systemConfigService.selectAllConfig().get("static_url").toString() + customPath + "/" + fileName + suffix + "?v=" + StringUtil.randomNumber(1);
    } catch (IOException e) {
      log.error(e.getMessage());
      return null;
    }
  }
}
