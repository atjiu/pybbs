package co.yiiu.module.user.repository;

import co.yiiu.module.user.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Integer> {

  @Cacheable
  User findById(int id);

  @Cacheable
  User findByUsername(String username);

  @CacheEvict
  void deleteById(int id);

  @Cacheable
  User findByToken(String token);

  @Cacheable
  User findByEmail(String email);

}
