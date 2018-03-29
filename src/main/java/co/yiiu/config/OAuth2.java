package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OAuth2 {
  private String clientId;
  private String clientSecret;
  private String callbackUrl;
}
