package co.yiiu.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Cookie工具类
 * <p>
 * 注意：在cookie的名或值中不能使用分号（;）、逗号（,）、等号（=）以及空格
 * </p>
 *
 * @author hubin
 * @since 2014-5-8
 */
public class CookieHelper {

  private static final Logger logger = Logger.getLogger("CookieHelper");

  /* 浏览器关闭时自动删除 */
  public final static int CLEAR_BROWSER_IS_CLOSED = -1;

  /* 立即删除 */
  public final static int CLEAR_IMMEDIATELY_REMOVE = 0;

  /**
   * <p>
   * 防止伪造SESSIONID攻击. 用户登录校验成功销毁当前JSESSIONID. 创建可信的JSESSIONID
   * </p>
   *
   * @param request 当前HTTP请求
   * @param value   用户ID等唯一信息
   */
  public static void authJSESSIONID(HttpServletRequest request, String value) {
    request.getSession().invalidate();
    request.getSession().setAttribute("KISSO-" + value, true);
  }

  /**
   * <p>
   * 根据cookieName获取Cookie
   * </p>
   *
   * @param request
   * @param cookieName Cookie name
   * @return Cookie
   */
  public static Cookie findCookieByName(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null)
      return null;
    for (int i = 0; i < cookies.length; i++) {
      if (cookies[i].getName().equals(cookieName)) {
        return cookies[i];
      }
    }
    return null;
  }

  public static String getValue(HttpServletRequest request, String cookieName) {
    Cookie cookie = findCookieByName(request, cookieName);
    if(cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  /**
   * <p>
   * 根据 cookieName 清空 Cookie【默认域下】
   * </p>
   *
   * @param response
   * @param cookieName
   */
  public static void clearCookieByName(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, "");
    cookie.setMaxAge(CLEAR_IMMEDIATELY_REMOVE);
    response.addCookie(cookie);
  }

  /**
   * <p>
   * 清除指定doamin的所有Cookie
   * </p>
   *
   * @param request
   * @param response
   * @param domain   Cookie所在的域
   * @param path     Cookie 路径
   * @return
   */
  public static void clearAllCookie(HttpServletRequest request, HttpServletResponse response, String domain,
                                    String path) {
    Cookie[] cookies = request.getCookies();
    for (int i = 0; i < cookies.length; i++) {
      clearCookie(response, cookies[i].getName(), domain, path);
    }
    logger.info("clearAllCookie in  domain " + domain);
  }

  /**
   * <p>
   * 根据cookieName清除指定Cookie
   * </p>
   *
   * @param request
   * @param response
   * @param cookieName cookie name
   * @param domain     Cookie所在的域
   * @param path       Cookie 路径
   * @return boolean
   */
  public static boolean clearCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                          String domain, String path) {
    boolean result = false;
    Cookie ck = findCookieByName(request, cookieName);
    if (ck != null) {
      result = clearCookie(response, cookieName, domain, path);
    }
    return result;
  }

  /**
   * <p>
   * 清除指定Cookie 等同于 clearCookieByName(...)
   * </p>
   * <p>
   * <p>
   * 该方法不判断Cookie是否存在,因此不对外暴露防止Cookie不存在异常.
   * </p>
   *
   * @param response
   * @param cookieName cookie name
   * @param domain     Cookie所在的域
   * @param path       Cookie 路径
   * @return boolean
   */
  private static boolean clearCookie(HttpServletResponse response, String cookieName, String domain, String path) {
    boolean result = false;
    try {
      Cookie cookie = new Cookie(cookieName, "");
      cookie.setMaxAge(CLEAR_IMMEDIATELY_REMOVE);
      cookie.setDomain(domain);
      cookie.setPath(path);
      response.addCookie(cookie);
      logger.fine("clear cookie " + cookieName);
      result = true;
    } catch (Exception e) {
      logger.severe("clear cookie " + cookieName + " is exception!\n" + e.toString());
    }
    return result;
  }

  /**
   * <p>
   * 添加 Cookie
   * </p>
   *
   * @param response
   * @param domain   所在域
   * @param path     域名路径
   * @param name     名称
   * @param value    内容
   * @param maxAge   生命周期参数
   * @param httpOnly 只读
   * @param secured  Https协议下安全传输
   */
  public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value,
                               int maxAge, boolean httpOnly, boolean secured) {
    Cookie cookie = new Cookie(name, value);
    /**
     * 不设置该参数默认 当前所在域
     */
    if (domain != null && !"".equals(domain)) {
      cookie.setDomain(domain);
    }
    cookie.setPath(path);
    cookie.setMaxAge(maxAge);

    /** Cookie 只在Https协议下传输设置 */
    if (secured) {
      cookie.setSecure(secured);
    }

    /** Cookie 只读设置 */
    if (httpOnly) {
      addHttpOnlyCookie(response, cookie);
    } else {
      /*
       * //servlet 3.0 support
       * cookie.setHttpOnly(httpOnly);
       */
      response.addCookie(cookie);
    }
  }

  /**
   * <p>
   * 解决 servlet 3.0 以下版本不支持 HttpOnly
   * </p>
   *
   * @param response HttpServletResponse类型的响应
   * @param cookie   要设置httpOnly的cookie对象
   */
  public static void addHttpOnlyCookie(HttpServletResponse response, Cookie cookie) {
    if (cookie == null) {
      return;
    }
    /**
     * 依次取得cookie中的名称、值、 最大生存时间、路径、域和是否为安全协议信息
     */
    String cookieName = cookie.getName();
    String cookieValue = cookie.getValue();
    int maxAge = cookie.getMaxAge();
    String path = cookie.getPath();
    String domain = cookie.getDomain();
    boolean isSecure = cookie.getSecure();
    StringBuffer sf = new StringBuffer();
    sf.append(cookieName + "=" + cookieValue + ";");
    if (maxAge >= 0) {
      sf.append("Max-Age=" + cookie.getMaxAge() + ";");
    }
    if (domain != null) {
      sf.append("domain=" + domain + ";");
    }
    if (path != null) {
      sf.append("path=" + path + ";");
    }
    if (isSecure) {
      sf.append("secure;HTTPOnly;");
    } else {
      sf.append("HTTPOnly;");
    }
    response.addHeader("Set-Cookie", sf.toString());
  }

}