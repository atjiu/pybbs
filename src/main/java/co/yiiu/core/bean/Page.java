package co.yiiu.core.bean;

import java.util.List;

/**
 * Created by tomoya at 2018/7/23
 */
public class Page<T> {

  private Integer number;
  private Integer size;
  private Integer totalPages;
  private Integer totalCount;
  private List<T> content;
  private Boolean first;
  private Boolean last;

  public Page(Integer number, Integer size, Integer totalCount, List<T> content) {
    this.number = number;
    this.size = size;
    this.totalCount = totalCount;
    this.content = content;
    this.totalPages = totalCount % size == 0 ? totalCount / size : (totalCount / size) + 1;
    this.first = number == 1;
    this.last = number.equals(this.totalPages);
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public Boolean getFirst() {
    return first;
  }

  public void setFirst(Boolean first) {
    this.first = first;
  }

  public Boolean getLast() {
    return last;
  }

  public void setLast(Boolean last) {
    this.last = last;
  }
}
