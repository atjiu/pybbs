package co.yiiu.module.user.service;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.module.user.mapper.RememberMeTokenMapper;
import co.yiiu.module.user.pojo.RememberMeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PersistentTokenService implements PersistentTokenRepository {

  @Autowired
  private RememberMeTokenMapper rememberMeTokenMapper;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public void createNewToken(PersistentRememberMeToken token) {

    List<RememberMeToken> tokens = rememberMeTokenMapper.findAllByUsername(token.getUsername());

    if (tokens != null && tokens.size() >= siteConfig.getLoginPoints()) {
      int end = tokens.size() - siteConfig.getLoginPoints() + 1;
      for (int i = 0; i < end; i++) {
        rememberMeTokenMapper.deleteByPrimaryKey(tokens.get(i).getId());
      }
    }

    RememberMeToken rememberMeToken = new RememberMeToken();
    BeanUtils.copyProperties(token, rememberMeToken);
    rememberMeTokenMapper.insertSelective(rememberMeToken);
  }

  @Override
  public void updateToken(String series, String tokenValue, Date lastUsed) {
    RememberMeToken rememberMeToken = rememberMeTokenMapper.findBySeries(series);
    if (rememberMeToken != null) {
      rememberMeToken.setTokenValue(tokenValue);
      rememberMeToken.setDate(lastUsed);
    }
  }

  @Override
  public PersistentRememberMeToken getTokenForSeries(String seriesId) {
    RememberMeToken rememberMeToken = rememberMeTokenMapper.findBySeries(seriesId);
    if (rememberMeToken != null) {
      return new PersistentRememberMeToken(rememberMeToken.getUsername(),
          rememberMeToken.getSeries(), rememberMeToken.getTokenValue(), rememberMeToken.getDate());
    }
    return null;
  }

  @Override
  public void removeUserTokens(String username) {
    rememberMeTokenMapper.deleteByUsername(username);
  }
}
