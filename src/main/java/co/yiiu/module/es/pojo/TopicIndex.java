package co.yiiu.module.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/4/24
 */
@Document(indexName = "topic_index", type = "topic", refreshInterval = "-1")
public class TopicIndex implements Serializable {

  @Id
  private Integer id;
  @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String title;
  @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String content;
  private String username;
  private String tag;
  private Date inTime;
  private Double weight;

  public TopicIndex() {
  }

  public TopicIndex(Integer id, String title, String content, String username, String tag, Date inTime, Double weight) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.username = username;
    this.tag = tag;
    this.inTime = inTime;
    this.weight = weight;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }
}
