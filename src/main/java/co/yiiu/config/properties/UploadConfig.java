package co.yiiu.config.properties;

import co.yiiu.config.properties.QiniuConfig;

/**
 * Created by tomoya at 2018/4/24
 */
public class UploadConfig {

  private QiniuConfig qiniu;

  public QiniuConfig getQiniu() {
    return qiniu;
  }

  public void setQiniu(QiniuConfig qiniu) {
    this.qiniu = qiniu;
  }
}
