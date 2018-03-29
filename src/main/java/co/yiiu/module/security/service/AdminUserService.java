package co.yiiu.module.security.service;

import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class AdminUserService {

  @Autowired
  private AdminUserRepository adminUserRepository;

  public Page<AdminUser> page(Integer pageNo, Integer pageSize) {
    Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(
        new Sort.Order(Sort.Direction.DESC, "inTime")
    ));
    return adminUserRepository.findAll(pageable);
  }

  public AdminUser findOne(Integer id) {
    return adminUserRepository.findOne(id);
  }

  public AdminUser save(AdminUser adminUser) {
    return adminUserRepository.save(adminUser);
  }

  public AdminUser findByToken(String token) {
    return adminUserRepository.findByToken(token);
  }

  public AdminUser findByUsername(String username) {
    return adminUserRepository.findByUsername(username);
  }
}
