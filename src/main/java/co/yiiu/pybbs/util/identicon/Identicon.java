package co.yiiu.pybbs.util.identicon;

import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.util.HashUtil;
import co.yiiu.pybbs.util.MD5Util;
import co.yiiu.pybbs.util.StringUtil;
import co.yiiu.pybbs.util.identicon.generator.IBaseGenartor;
import co.yiiu.pybbs.util.identicon.generator.impl.MyGenerator;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

/**
 * Author: Bryant Hang
 * Date: 15/1/10
 * Time: 下午2:42
 */
@Component
public class Identicon {

  private Logger log = LoggerFactory.getLogger(Identicon.class);

  private IBaseGenartor genartor;
  @Autowired
  private SystemConfigService systemConfigService;

  public Identicon() {
    this.genartor = new MyGenerator();
  }

  public BufferedImage create(String hash, int size) {
    Preconditions.checkArgument(size > 0 && !StringUtils.isEmpty(hash));

    boolean[][] array = genartor.getBooleanValueArray(hash);

//        int ratio = DoubleMath.roundToInt(size / 5.0, RoundingMode.HALF_UP);
    int ratio = size / 6;

    BufferedImage identicon = new BufferedImage(ratio * 6, ratio * 6, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = identicon.getGraphics();

    graphics.setColor(genartor.getBackgroundColor()); // 背景色
    graphics.fillRect(0, 0, identicon.getWidth(), identicon.getHeight());

    graphics.setColor(genartor.getForegroundColor()); // 图案前景色
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (array[i][j]) {
          graphics.fillRect(j * ratio + 35, i * ratio + 35, ratio, ratio);
        }
      }
    }

    return identicon;
  }

  public static String imgToBase64String(RenderedImage img, String formatName) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      ImageIO.write(img, formatName, os);
      return Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (final IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }

  public String generator() {
    Identicon identicon = new Identicon();
    String md5 = MD5Util.getMD5String(StringUtil.randomString(6));
    BufferedImage image = identicon.create(md5, 300);
    return "data:image/png;base64," + imgToBase64String(image, "png");
  }

  public String generator(String username) {
    Identicon identicon = new Identicon();
    String md5 = HashUtil.md5(StringUtil.randomString(6));
    BufferedImage image = identicon.create(md5, 420);
    return saveFile(username, image);
  }

  public String saveFile(String username, BufferedImage image) {
    String fileName = "avatar.png";
    String userAvatarPath = "avatar/" + username + "/";
    try {
      File file = new File(systemConfigService.selectAllConfig().get("upload_path").toString() + userAvatarPath);
      if (!file.exists()) file.mkdirs();
      File file1 = new File(systemConfigService.selectAllConfig().get("upload_path").toString() + userAvatarPath + fileName);
      if (!file1.exists()) file1.createNewFile();
      ImageIO.write(image, "PNG", file1);
      return systemConfigService.selectAllConfig().get("static_url").toString() + userAvatarPath + fileName;
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
    }
    return null;
  }

//  public String saveFileToQiniu(byte[] data) {
//    try {
//      //构造一个带指定Zone对象的配置类
//      Configuration cfg = new Configuration(Zone.zone2());
//      //...其他参数参考类注释
//      UploadManager uploadManager = new UploadManager(cfg);
//      Auth auth = Auth.create(siteConfig.getUpload().getQiniu().getAccessKey(), siteConfig.getUpload().getQiniu().getSecretKey());
//      String uploadToken = auth.uploadToken(siteConfig.getUpload().getQiniu().getBucket());
//      Response response = uploadManager.put(data, null, uploadToken, null, null, true);
//      DefaultPutRet defaultPutRet = JsonUtil.jsonToObject(response.bodyString(), DefaultPutRet.class);
//      return siteConfig.getUpload().getQiniu().getDomain() + defaultPutRet.key;
//    } catch (QiniuException e) {
//      log.error(e.getLocalizedMessage());
//    }
//    return "";
//  }

//  public static void main(String[] args) {
//    System.out.println(new Identicon().generator());
//  }
}
