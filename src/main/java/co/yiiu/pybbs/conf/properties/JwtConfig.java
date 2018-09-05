package co.yiiu.pybbs.conf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tomoya at 2018/9/3
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
  private String header;
  private String secret;
  private long expiration;
  private String tokenHead;

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public long getExpiration() {
    return expiration;
  }

  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }

  public String getTokenHead() {
    return tokenHead;
  }

  public void setTokenHead(String tokenHead) {
    this.tokenHead = tokenHead;
  }
}
