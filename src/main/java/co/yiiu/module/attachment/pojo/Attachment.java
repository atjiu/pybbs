package co.yiiu.module.attachment.pojo;

import java.util.Date;

public class Attachment {
  private Integer id;

  private String fileName;

  private Integer height;

  private Date inTime;

  private String localPath;

  private String md5;

  private String requestUrl;

  private String size;

  private String suffix;

  private String type;

  private Integer width;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName == null ? null : fileName.trim();
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public String getLocalPath() {
    return localPath;
  }

  public void setLocalPath(String localPath) {
    this.localPath = localPath == null ? null : localPath.trim();
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5 == null ? null : md5.trim();
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl == null ? null : requestUrl.trim();
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size == null ? null : size.trim();
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix == null ? null : suffix.trim();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type == null ? null : type.trim();
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }
}