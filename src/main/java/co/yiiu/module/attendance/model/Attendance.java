package co.yiiu.module.attendance.model;

import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya on 17-6-15.
 */
@Entity
@Table(name = "yiiu_attendance")
@Getter
@Setter
public class Attendance implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  private int score;

  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonIgnore
  private User user;

  @Column(name = "in_time")
  private Date inTime;

}
