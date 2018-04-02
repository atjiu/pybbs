package co.yiiu.module.security.service;

import co.yiiu.core.util.JsonUtil;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(
        new Sort.Order(Sort.Direction.DESC, "inTime")
    ));
    return adminUserRepository.findAll(pageable);
  }

  public AdminUser findOne(Integer id) {
    return adminUserRepository.findOne(id);
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

  // 删除所有后台用户存在redis里的数据
  public void deleteAllRedisAdminUser() {
    List<AdminUser> adminUsers = adminUserRepository.findAll();
    adminUsers.forEach(adminUser -> stringRedisTemplate.delete("admin_" + adminUser.getToken()));
  }
}
