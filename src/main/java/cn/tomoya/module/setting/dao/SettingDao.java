package cn.tomoya.module.setting.dao;

import cn.tomoya.module.setting.entity.Setting;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "setting")
public interface SettingDao extends JpaRepository<Setting, Integer> {

    @Cacheable
    Setting findByName(String name);

    @Cacheable
    Setting findOne(int id);

}
