package co.yiiu.module.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 4/27/18
 */
@Document(indexName = "pybbs", type = "tag", refreshInterval = "-1")
public class TagIndex implements Serializable {

  @Id
  private Integer id;
  private String logo;
  private String name;
  private String intro;
  private Date inTime;
  private Integer topicCount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Integer getTopicCount() {
    return topicCount;
  }

  public void setTopicCount(Integer topicCount) {
    this.topicCount = topicCount;
  }
}
