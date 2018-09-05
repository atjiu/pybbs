package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tomoya at 2018/9/3
 */
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByUsername(String username) {
    User user = new User();
    user.setUsername(username);
    List<User> users = userRepository.findAll(Example.of(user));
    if (users.size() > 0) return users.get(0);
    return null;
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public User findById(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public User findByEmail(String email) {
    User user = new User();
    user.setEmail(email);
    List<User> users = userRepository.findAll(Example.of(user));
    if (users.size() > 0) return users.get(0);
    return null;
  }

  public List<User> findTop100(Integer size) {
    Sort sort = Sort.by(Sort.Direction.DESC, "score");
    Pageable pageable = PageRequest.of(0, size, sort);
    return userRepository.findAll(pageable).getContent();
  }
}
