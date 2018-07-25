package co.yiiu.module.security.service;

import co.yiiu.core.bean.Page;
import co.yiiu.module.security.mapper.AdminUserMapper;
import co.yiiu.module.security.pojo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
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
  private AdminUserMapper adminUserMapper;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public Page<AdminUser> page(Integer pageNo, Integer pageSize) {
    List<AdminUser> list = adminUserMapper.findAll(pageNo, pageSize, "inTime desc");
    int count = adminUserMapper.count();
    return new Page<>(pageNo, pageSize, count, list);
  }

  public AdminUser findOne(Integer id) {
    return adminUserMapper.selectByPrimaryKey(id);
  }

  public AdminUser save(AdminUser adminUser) {
    adminUserMapper.insert(adminUser);
    // 删除redis里的用户缓存
    this.deleteAllRedisAdminUser();
    return adminUser;
  }

  public AdminUser findByUsername(String username) {
    return adminUserMapper.findAdminUser(null, username);
  }

  public void deleteById(Integer id) {
    AdminUser adminUser = this.findOne(id);
    // 删除缓存数据
    stringRedisTemplate.delete("admin_" + adminUser.getToken());
    adminUserMapper.deleteByPrimaryKey(id);
  }

  // 删除所有后台用户存在redis里的数据
  public void deleteAllRedisAdminUser() {
    List<AdminUser> adminUsers = adminUserMapper.findAll(null, null, null);
    adminUsers.forEach(adminUser -> stringRedisTemplate.delete("admin_" + adminUser.getToken()));
  }
}
