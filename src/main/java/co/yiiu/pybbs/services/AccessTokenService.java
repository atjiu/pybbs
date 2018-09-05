package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.AccessToken;
import co.yiiu.pybbs.repositories.AccessTokenRepository;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessTokenService {

  @Autowired
  private AccessTokenRepository accessTokenRepository;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  public AccessToken findByToken(String token) {
    AccessToken accessToken = new AccessToken();
    accessToken.setToken(token);
    List<AccessToken> tokens = accessTokenRepository.findAll(Example.of(accessToken));
    if (tokens.size() > 0) {
      if (jwtTokenUtil.validateToken(tokens.get(0).getToken())) {
        return tokens.get(0);
      } else {
        this.deleteById(tokens.get(0).getId());
      }
    }
    return null;
  }

  public void deleteByUserId(String userId) {
    accessTokenRepository.deleteByUserId(userId);
  }

  public void save(AccessToken accessToken) {
    accessTokenRepository.save(accessToken);
  }

  public void deleteById(String id) {
    accessTokenRepository.deleteById(id);
  }

  public AccessToken findByUserId(String userId) {
    AccessToken accessToken = new AccessToken();
    accessToken.setUserId(userId);
    List<AccessToken> tokens = accessTokenRepository.findAll(Example.of(accessToken));
    if (tokens.size() > 0) {
      if (jwtTokenUtil.validateToken(tokens.get(0).getToken())) {
        return tokens.get(0);
      } else {
        this.deleteById(tokens.get(0).getId());
      }
    }
    return null;
  }
}
