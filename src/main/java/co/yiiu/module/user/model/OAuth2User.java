package co.yiiu.module.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/3/19
 */
@Entity
@Table(name = "yiiu_oauth2_user")
@Getter
@Setter
public class OAuth2User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String avatar;
  private String nickName;
  private Date inTime;
  // 关联本地用户ID
  private Integer userId;
  // 授权帐户的ID
  private String oauthUserId;
  private String accessToken;
  // 授权帐户类型
  private String type;

  public enum Type {
    GITHUB
  }
}
