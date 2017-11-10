package co.yiiu.module.user.repository;

import co.yiiu.module.user.model.GithubUser;
import co.yiiu.module.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findById(int id);

  Page<User> findByBlock(boolean block, Pageable pageable);

  User findByUsername(String username);

  void deleteById(int id);

  User findByToken(String token);

  User findByEmail(String email);

  User findByGithubUser(GithubUser githubUser);

}
