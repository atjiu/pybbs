package co.yiiu.pybbs.conf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya at 2018/9/3
 */
@Configuration
@ConfigurationProperties(prefix = "site")
public class SiteConfig {

  private List<String> corsDomain;
  private Integer pageSize;
  private List<Map<String, String>> sections;
  private List<String> admin;
  private List<String> ban;
  private Integer createTopicScore;
  private Integer createCommentScore;
  private Integer goodTopicScore;

  public Integer getGoodTopicScore() {
    return goodTopicScore;
  }

  public void setGoodTopicScore(Integer goodTopicScore) {
    this.goodTopicScore = goodTopicScore;
  }

  public Integer getCreateTopicScore() {
    return createTopicScore;
  }

  public void setCreateTopicScore(Integer createTopicScore) {
    this.createTopicScore = createTopicScore;
  }

  public Integer getCreateCommentScore() {
    return createCommentScore;
  }

  public void setCreateCommentScore(Integer createCommentScore) {
    this.createCommentScore = createCommentScore;
  }

  public List<String> getCorsDomain() {
    return corsDomain;
  }

  public void setCorsDomain(List<String> corsDomain) {
    this.corsDomain = corsDomain;
  }

  public List<String> getAdmin() {
    return admin;
  }

  public void setAdmin(List<String> admin) {
    this.admin = admin;
  }

  public List<String> getBan() {
    return ban;
  }

  public void setBan(List<String> ban) {
    this.ban = ban;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public List<Map<String, String>> getSections() {
    return sections;
  }

  public void setSections(List<Map<String, String>> sections) {
    this.sections = sections;
  }
}
