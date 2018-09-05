package co.yiiu.pybbs.utils;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class StringUtil {

  @Autowired
  private SiteConfig siteConfig;

  public String emailRegex = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
  public String urlRegex = "^((https|http)?:\\/\\/)[^\\s]+";
  public String usernameRegex = "[a-z0-9A-Z]{4,16}";
  public String passwordRegex = "[a-z0-9A-Z]{6,32}";

  public boolean check(String text, String regex) {
    if (StringUtils.isEmpty(text)) {
      return false;
    } else {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      return matcher.matches();
    }
  }

  public List<String> sectionNames() {
    return siteConfig.getSections().stream()
        .map(section -> section.get("name"))
        .collect(Collectors.toList());
  }

  public List<String> sectionValues() {
    return siteConfig.getSections().stream()
        .map(section -> section.get("value"))
        .collect(Collectors.toList());
  }
}
