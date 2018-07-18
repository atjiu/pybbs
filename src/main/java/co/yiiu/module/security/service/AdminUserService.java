package co.yiiu.module.security.service;

import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class AdminUserService {

  @Autowired
  private AdminUserRepository adminUserRepository;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public Page<AdminUser> page(Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "inTime"));
    return adminUserRepository.findAll(pageable);
  }

  public AdminUser findOne(Integer id) {
    return adminUserRepository.findById(id).get();
  }

  public AdminUser save(AdminUser adminUser) {
    adminUser =  adminUserRepository.save(adminUser);
    // 删除redis里的用户缓存
    this.deleteAllRedisAdminUser();
    return adminUser;
  }

  public AdminUser findByToken(String token) {
    return adminUserRepository.findByToken(token);
  }

  public AdminUser findByUsername(String username) {
    return adminUserRepository.findByUsername(username);
  }

  public void deleteById(Integer id) {
    AdminUser adminUser = this.findOne(id);
    // 删除缓存数据
    stringRedisTemplate.delete("admin_" + adminUser.getToken());
    adminUserRepository.delete(adminUser);
  }

  // 删除所有后台用户存在redis里的数据
  public void deleteAllRedisAdminUser() {
    List<AdminUser> adminUsers = adminUserRepository.findAll();
    adminUsers.forEach(adminUser -> stringRedisTemplate.delete("admin_" + adminUser.getToken()));
  }
}
