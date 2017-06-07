package cn.tomoya.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityTag {

  /**
   * Get authenticated status
   * @return
   */
  public static boolean isAuthenticated() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (obj instanceof UserDetails) {
      return true;
    }
    return false;
  }

  /**
   * Gets the user name of the user from the Authentication object
   *
   * @return the user name as string
   */
  public static String getPrincipal() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof UserDetails) {
      return ((UserDetails) obj).getUsername();
    } else {
      return "Guest";
    }
  }

  /**
   * Is the user granted all of the grantedAuthorities passed in
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if user has all of the listed authorities/roles, otherwise
   * false
   */
  public static boolean allGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        continue;
      return false;
    }
    return true;
  }

  public static boolean allGranted(String checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    if (userAuths.contains(checkForAuths))
      return true;
    return false;
  }

  /**
   * Is the user granted any of the grantedAuthorities passed into
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if user has any of the listed authorities/roles, otherwise
   * false
   */
  public static boolean anyGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return true;
    }
    return false;
  }

  /**
   * is the user granted none of the supplied roles
   *
   * @param checkForAuths a string array of roles
   * @return true only if none of listed roles are granted
   */
  public static boolean noneGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return false;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  private static Set<String> getUserAuthorities() {
    try {
      Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Set<String> roles = new HashSet<String>();
      if (obj instanceof UserDetails) {
        Collection<GrantedAuthority> gas = (Collection<GrantedAuthority>) ((UserDetails) obj).getAuthorities();
        for (GrantedAuthority ga : gas) {
          roles.add(ga.getAuthority());
        }
      }
      return roles;
    } catch (Exception e) {
      return new HashSet<String>();
    }
  }

}
