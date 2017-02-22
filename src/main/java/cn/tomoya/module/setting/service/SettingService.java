package cn.tomoya.module.setting.service;

import cn.tomoya.module.setting.entity.Setting;
import cn.tomoya.module.setting.dao.SettingDao;
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
public class SettingService {

    @Autowired
    private SettingDao settingDao;

    public List<Setting> findAll() {
        return settingDao.findAll();
    }

    public Setting findOne(int id) {
        return settingDao.findOne(id);
    }

    public Setting findByName(String name) {
        return settingDao.findByName(name);
    }

    public void save(Setting setting) {
        settingDao.save(setting);
    }

    public String getBaseUrl() {
        return findByName("base_url").getValue();
    }

    public String getTheme() {
        return findByName("theme").getValue();
    }

    public String getEditor() {
        return findByName("editor").getValue();
    }

    public String getSearch() {
        return findByName("search").getValue();
    }

    public int getPageSize() {
        return Integer.parseInt(findByName("page_size").getValue());
    }

    public String getAvatarPath() {
        return findByName("avatar_path").getValue();
    }

    public String getUploadPath() {
        return findByName("upload_path").getValue();
    }

    @CacheEvict("setting")
    public void clearCache() {

    }
}
