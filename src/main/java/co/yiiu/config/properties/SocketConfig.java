package co.yiiu.config.properties;

/**
 * Created by tomoya at 2018/8/17
 */
public class SocketConfig {

  private String hostname;
  private Integer port;
  private String url;

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
