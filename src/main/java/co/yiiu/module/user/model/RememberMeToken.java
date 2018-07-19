package co.yiiu.module.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
public class RememberMeToken implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String series;
  private String tokenValue;
  private Date date;
}
