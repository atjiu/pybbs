package co.yiiu.core.util.identicon;

import co.yiiu.core.util.StrUtil;
import co.yiiu.core.util.identicon.generator.IBaseGenartor;
import co.yiiu.core.util.identicon.generator.impl.MyGenerator;
import co.yiiu.core.util.security.MD5Helper;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
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

  private IBaseGenartor genartor;

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
    String md5 = MD5Helper.getMD5String(StrUtil.randomString(6));
    BufferedImage image = identicon.create(md5, 300);
    return "data:image/png;base64," + imgToBase64String(image, "png");
  }

  public static void main(String[] args) {
    System.out.println(new Identicon().generator());
  }
}
