package co.yiiu.module.es.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 4/27/18
 */
@Data
@Document(indexName = "topic_index", type = "topic", refreshInterval = "-1")
public class TagIndex implements Serializable {

  @Id
  private Integer id;
  private String logo;
  private String name;
  private String intro;
  private Date inTime;
  private Integer topicCount;

}
