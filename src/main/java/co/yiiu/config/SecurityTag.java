package co.yiiu.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityTag {

  /**
   * Get admin_user block status
   *
   * @return
   */
  public boolean isLock() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return !((UserDetails) obj).isAccountNonLocked();
    } else {
      return true;
    }
  }

  /**
   * Get authenticated status
   *
   * @return
   */
  public boolean isAuthenticated() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return true;
    }
    return false;
  }

  /**
   * Gets the admin_user name of the admin_user from the Authentication object
   *
   * @return the admin_user name as string
   */
  public String getPrincipal() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return ((UserDetails) obj).getUsername();
    } else {
      return null;
    }
  }

  /**
   * Is the admin_user granted all of the grantedAuthorities passed in
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if admin_user has all of the listed authorities/roles, otherwise
   * false
   */
  public boolean allGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        continue;
      return false;
    }
    return true;
  }

  public boolean allGranted(String checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    if (userAuths.contains(checkForAuths))
      return true;
    return false;
  }

  /**
   * Is the admin_user granted any of the grantedAuthorities passed into
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if admin_user has any of the listed authorities/roles, otherwise
   * false
   */
  public boolean anyGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return true;
    }
    return false;
  }

  /**
   * is the admin_user granted none of the supplied roles
   *
   * @param checkForAuths a string array of roles
   * @return true only if none of listed roles are granted
   */
  public boolean noneGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return false;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  private Set<String> getUserAuthorities() {
    try {
      Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Set<String> roles = new HashSet<>();
      if (obj instanceof org.springframework.security.core.userdetails.User) {
        Collection<GrantedAuthority> gas = (Collection<GrantedAuthority>) ((UserDetails) obj).getAuthorities();
        for (GrantedAuthority ga : gas) {
          roles.add(ga.getAuthority());
        }
      }
      return roles;
    } catch (Exception e) {
      return new HashSet<>();
    }
  }

}
