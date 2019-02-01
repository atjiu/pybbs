package co.yiiu.pybbs.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collections;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class MyPage<T> extends Page<T> {

  private List<T> records;
  private long total;
  private long size;
  private long current;
  private String[] ascs;
  private String[] descs;
  private boolean optimizeCountSql;
  private boolean isSearchCount;

  public MyPage() {
    this.records = Collections.emptyList();
    this.total = 0L;
    this.size = 10L;
    this.current = 1L;
    this.optimizeCountSql = true;
    this.isSearchCount = true;
  }

  public MyPage(long current, long size) {
    this(current, size, 0L);
  }

  public MyPage(long current, long size, long total) {
    this(current, size, total, true);
  }

  public MyPage(long current, long size, boolean isSearchCount) {
    this(current, size, 0L, isSearchCount);
  }

  public MyPage(long current, long size, long total, boolean isSearchCount) {
    this.records = Collections.emptyList();
    this.total = 0L;
    this.size = 10L;
    this.current = 1L;
    this.optimizeCountSql = true;
    this.isSearchCount = true;
    if (current > 1L) {
      this.current = current;
    }

    this.size = size;
    this.total = total;
    this.isSearchCount = isSearchCount;
  }

  public long getPages() {
    if (this.getSize() == 0L) {
      return 0L;
    } else {
      long pages = this.getTotal() / this.getSize();
      if (this.getTotal() % this.getSize() != 0L) {
        ++pages;
      }

      return pages;
    }
  }

  public boolean hasPrevious() {
    return this.current > 1L;
  }

  public boolean hasNext() {
    return this.current < this.getPages();
  }

  public List<T> getRecords() {
    return this.records;
  }

  public Page<T> setRecords(List<T> records) {
    this.records = records;
    return this;
  }

  public long getTotal() {
    return this.total;
  }

  public Page<T> setTotal(long total) {
    this.total = total;
    return this;
  }

  public long getSize() {
    return this.size;
  }

  public Page<T> setSize(long size) {
    this.size = size;
    return this;
  }

  public long getCurrent() {
    return this.current;
  }

  public Page<T> setCurrent(long current) {
    this.current = current;
    return this;
  }

  public String[] ascs() {
    return this.ascs;
  }

  public Page<T> setAscs(List<String> ascs) {
    if (CollectionUtils.isNotEmpty(ascs)) {
      this.ascs = (String[])ascs.toArray(new String[0]);
    }

    return this;
  }

  public Page<T> setAsc(String... ascs) {
    this.ascs = ascs;
    return this;
  }

  public String[] descs() {
    return this.descs;
  }

  public Page<T> setDescs(List<String> descs) {
    if (CollectionUtils.isNotEmpty(descs)) {
      this.descs = (String[])descs.toArray(new String[0]);
    }

    return this;
  }

  public Page<T> setDesc(String... descs) {
    this.descs = descs;
    return this;
  }

  public boolean optimizeCountSql() {
    return this.optimizeCountSql;
  }

  public boolean isSearchCount() {
    return this.total < 0L ? false : this.isSearchCount;
  }

  public Page<T> setSearchCount(boolean isSearchCount) {
    this.isSearchCount = isSearchCount;
    return this;
  }

  public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
    this.optimizeCountSql = optimizeCountSql;
    return this;
  }
}
