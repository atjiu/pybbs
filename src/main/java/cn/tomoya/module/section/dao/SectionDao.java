package cn.tomoya.module.section.dao;

import cn.tomoya.module.section.entity.Section;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Repository
@CacheConfig(cacheNames = "sections")
public interface SectionDao extends JpaRepository<Section, Integer> {

    @Cacheable
    List<Section> findAll();

    @Cacheable
    Section findOne(int id);

    @Cacheable
    Section findByName(String name);

}
