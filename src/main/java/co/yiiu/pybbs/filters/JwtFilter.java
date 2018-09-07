package co.yiiu.pybbs.filters;

import co.yiiu.pybbs.conf.properties.JwtConfig;
import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import co.yiiu.pybbs.utils.Result;
import co.yiiu.pybbs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya at 2018/9/3
 */
@Component
public class JwtFilter implements HandlerInterceptor {

  @Autowired
  private JwtConfig jwtConfig;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private StringUtil stringUtil;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    if (request.getMethod().equals("OPTIONS")) {
      // 在WebMvcConfig里配置的Cors在这好像不起作用，这里还要再配置一遍
      response.setHeader("Access-Control-Allow-Methods", "*");
      response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
      response.setHeader("Access-Control-Allow-Origin", stringUtil.listToString(siteConfig.getCorsDomain(), ","));
      response.setStatus(HttpServletResponse.SC_NO_CONTENT);
      return false;
    }
    String authHeader = request.getHeader(jwtConfig.getHeader());
    if (authHeader == null || !authHeader.startsWith(jwtConfig.getTokenHead())) {
      Result.error(response, 202, "无效Token");
      return false;
    }
    String authToken = authHeader.substring(jwtConfig.getTokenHead().length()); // The part after "Bearer "
    if(!jwtTokenUtil.validateToken(authToken)) {
      Result.error(response, 202, "Token不合法或已过期");
      return false;
    }
    request.setAttribute("authToken", authHeader);
    return true;
  }
}
