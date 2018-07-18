package co.yiiu.module.user.model;

/**
 * Created by tomoya at 2018/3/28
 */
public enum UserReputation {

  UP_COMMENT("评论被点赞", 10),
  UP_TOPIC("话题被点赞", 5),

  DOWN_TOPIC("点踩话题", -2),
  DOWN_COMMENT("点踩评论", -2);

  private String name;
  private Integer reputation;

  UserReputation(String name, Integer reputation) {
    this.name = name;
    this.reputation = reputation;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getReputation() {
    return reputation;
  }

  public void setReputation(Integer reputation) {
    this.reputation = reputation;
  }
}
