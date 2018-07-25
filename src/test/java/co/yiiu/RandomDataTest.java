package co.yiiu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by tomoya at 2018/7/24
 */
@Slf4j
public class RandomDataTest {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private Connection connection;
  private Statement statement;
  private PreparedStatement preparedStatement;

  //  @Before
  public void before() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection("jdbc:mysql://localhost/pybbs?useSSL=false&characterEncoding=utf8", "root", "");
    statement = connection.createStatement();
  }

  //  @After
  public void after() throws SQLException {
    statement.close();
    connection.close();
//    preparedStatement.close();
  }

  @Test
  public void insertUser() throws SQLException {
    // 100万用户
    String baseSql = "INSERT INTO `user` (`avatar`, `bio`, `block`, `comment_email`, `email`, `in_time`, `mobile`, `password`, `reply_email`, `reputation`, `token`, `url`, `username`) VALUES ";
    String sql = "";
    String values = "";
    String password = new BCryptPasswordEncoder().encode("123123");
    for (int i = 1; i <= 1000000; i++) {
      values += "('http://p7o6459pu.bkt.clouddn.com/FperIGntLCz74nfs2jgme6PFtjbT', NULL, 0, 1, \""+i+"@gmail.com\", NOW(), NULL, '"+password+"', 1, 0, '"+ UUID.randomUUID().toString()+"', NULL, 'user_"+i+"'),";
      if (i % 1000 == 0) {
        sql = baseSql + values;
        sql = sql.substring(0, sql.length() - 1);
        sql += ";";
        int count = statement.executeUpdate(sql);
        System.out.println("插入了: " + count + " 条数据, id: " + i);
        sql = "";
        values = "";
      }
    }
  }

  private String[] tags = {"java", "spring", "hibernate", "mybatis", "python", "nodejs", "javascript", "swift", "mysql", "android", "kotlin", "golang", "spring-boot", "spring-security", "express", "koa"};

  @Test
  public void insertTopic() throws SQLException {
    // 1000万话题
    String baseSql = "INSERT INTO `topic` (`comment_count`, `content`, `down`, `down_ids`, `good`, `in_time`, `last_comment_time`, `modify_time`, `tag`, `title`, `top`, `up`, `up_ids`, `user_id`, `view`, `weight`) VALUES ";
    String sql = "";
    String values = "";
    Random random = new Random();
    for (int i = 1; i <= 10000000; i++) {
      int titleLength = random.nextInt(41) + 6;
      int contentLength = random.nextInt(2000) + 2;
      int userID = random.nextInt(1000001) + 1;
      values += "(0, '"+RandomNumber.getRandomNumberAndString(contentLength)+"', 0, '', 0, NOW(), NULL, NULL, '"+RandomNumber.getRandomNumberAndString(random.nextInt(tags.length))+"', '"+RandomNumber.getRandomNumberAndString(titleLength) + i +"', 0, 0, '', "+userID+", 0, 0),";
      if (i % 1000 == 0) {
        sql = baseSql + values;
        sql = sql.substring(0, sql.length() - 1);
        sql += ";";
        int count = statement.executeUpdate(sql);
        System.out.println("插入了: " + count + " 条数据, id: " + i);
        sql = "";
        values = "";
      }
    }
  }

  @Test
  public void insertComment() {
    String sql = "INSERT INTO `comment` (`comment_id`, `content`, `down`, `down_ids`, `in_time`, `topic_id`, `up`, `up_ids`, `user_id`) VALUES (NULL, ?, 0, '', NOW(), ?, 0, '', ?)";

  }

  @Test
  public void test() {
    long t1 = System.currentTimeMillis();
    String sql = "select t.*, u.* from topic t, user u where t.user_id = u.id limit ?, ?";
    List maps = jdbcTemplate.queryForList(sql, 100, 20);
    String countSql = "select count(*) from topic";
    Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);
    long t2 = System.currentTimeMillis();
    System.out.println(t2-t1);
    log.info("list: {}, count: {}", maps, count);
  }
}
