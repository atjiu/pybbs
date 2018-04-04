package co.yiiu.module.attachment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomoya at 2018/1/24
 */
@Entity
@Table(name = "yiiu_attachment")
@Getter
@Setter
public class Attachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  // 文件存储的本地路径
  private String localPath;
  // 文件名字
  private String fileName;
  private Date inTime;
  // 文件访问地址
  private String requestUrl;
  private String type;
  private Integer width;
  private Integer height;
  // 文件大小
  private String size;
  // 文件后缀
  private String suffix;
  // 文件的MD5值，用于防止文件重复
  @Column(unique = true)
  private String md5;
}
