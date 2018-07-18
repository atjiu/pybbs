package co.yiiu.module.es.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/4/24
 */
@Data
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
}
