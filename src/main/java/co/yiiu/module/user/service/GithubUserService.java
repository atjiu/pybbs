package co.yiiu.module.user.service;

import co.yiiu.module.user.model.GithubUser;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.repository.GithubUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "githubUsers")
public class GithubUserService {

  @Autowired
  private GithubUserRepository githubUserRepository;

  /**
   * 根据github的登录名查询github登录记录
   * @param login
   * @return
   */
  @Cacheable
  public GithubUser findByLogin(String login) {
    return githubUserRepository.findByLogin(login);
  }

  /**
   * 根据本地用户查询github登录记录
   * @param user
   * @return
   */
  @Cacheable
  public GithubUser findByUser(User user) {
    return githubUserRepository.findByUser(user);
  }

  @CacheEvict(allEntries = true)
  public GithubUser save(GithubUser githubUser) {
    return githubUserRepository.save(githubUser);
  }

  public GithubUser convert(Map map, GithubUser githubUser) {
    githubUser.setGithubId(map.get("id").toString());
    githubUser.setLogin(map.get("login").toString());
    githubUser.setAvatar_url(map.get("avatar_url").toString());
    githubUser.setUrl(map.get("url") != null ? map.get("url").toString() : null);
    githubUser.setHtml_url(map.get("html_url") != null ? map.get("html_url").toString() : null);
    githubUser.setFollowers_url(map.get("followers_url") != null ? map.get("followers_url").toString() : null);
    githubUser.setFollowing_url(map.get("following_url") != null ? map.get("following_url").toString() : null);
    githubUser.setGists_url(map.get("gists_url") != null ? map.get("gists_url").toString() : null);
    githubUser.setStarted_url(map.get("started_url") != null ? map.get("started_url").toString() : null);
    githubUser.setSubscriptions_url(map.get("subscriptions_url") != null ? map.get("subscriptions_url").toString() : null);
    githubUser.setOrganizations_url(map.get("organizations_url") != null ? map.get("organizations_url").toString() : null);
    githubUser.setRepos_url(map.get("repos_url") != null ? map.get("repos_url").toString() : null);
    githubUser.setEvents_url(map.get("events_url") != null ? map.get("events_url").toString() : null);
    githubUser.setReceived_events_url(map.get("received_events_url") != null ? map.get("received_events_url").toString() : null);
    githubUser.setType(map.get("type") != null ? map.get("type").toString() : null);
    githubUser.setSite_admin(map.get("site_admin") != null && (boolean) map.get("site_admin"));
    githubUser.setName(map.get("name") != null ? map.get("name").toString() : null);
    githubUser.setCompany(map.get("company") != null ? map.get("company").toString() : null);
    githubUser.setBlog(map.get("blog") != null ? map.get("blog").toString() : null);
    githubUser.setLocation(map.get("location") != null ? map.get("location").toString() : null);
    githubUser.setEmail(map.get("email") != null ? map.get("email").toString() : null);
    githubUser.setHireable(map.get("hireable") != null ? map.get("hireable").toString() : null);
    githubUser.setBio(map.get("bio") != null ? map.get("bio").toString() : null);
    githubUser.setPublic_repos(map.get("public_repos") != null ? (int) map.get("public_repos") : 0);
    githubUser.setPublic_gists(map.get("public_gists") != null ? (int) map.get("public_gists") : 0);
    githubUser.setFollowers(map.get("followers") != null ? (int) map.get("followers") : 0);
    githubUser.setFollowing(map.get("following") != null ? (int) map.get("following") : 0);
    return githubUser;
  }

}
