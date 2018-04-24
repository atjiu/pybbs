package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomoya at 2018/4/24
 */
@Setter
@Getter
public class QiniuConfig {

  private String accessKey;
  private String secretKey;
  private String bucket;
  private String domain;

}
