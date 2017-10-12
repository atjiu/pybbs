package cn.tomoya.module.section.service;

import cn.tomoya.module.section.dao.SectionDao;
import cn.tomoya.module.section.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class SectionService {

  @Autowired
  private SectionDao sectionDao;

  public List<Section> findAll() {
    return sectionDao.findAll();
  }

  public void save(Section section) {
    sectionDao.save(section);
  }

  public void delete(int id) {
    sectionDao.delete(findById(id));
  }

  public Section findById(int id) {
    return sectionDao.findById(id);
  }

  public Section findByName(String name) {
    return sectionDao.findByName(name);
  }

  @CacheEvict("sections")
  public void clearCache() {
  }
}