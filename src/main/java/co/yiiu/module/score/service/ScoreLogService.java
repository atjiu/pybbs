package co.yiiu.module.score.service;

import co.yiiu.config.FreemarkerConfig;
import co.yiiu.config.ScoreEventConfig;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.collect.model.Collect;
import co.yiiu.module.score.model.ScoreEventEnum;
import co.yiiu.module.score.model.ScoreLog;
import co.yiiu.module.score.repository.ScoreLogRepository;
import co.yiiu.module.user.model.User;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Service
@CacheConfig(cacheNames = "scoreLogs")
public class ScoreLogService {

    @Autowired
    FreemarkerUtil freemarkerUtil;

    @Autowired
    ScoreLogRepository scoreLogRepository;
    @Autowired
    ScoreEventConfig scoreEventConfig;

    @CacheEvict(allEntries = true)
    public void save(ScoreLog scoreLog) {
        scoreLogRepository.save(scoreLog);
    }

    @Cacheable
    public Page<ScoreLog> findScoreByUser(Integer p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p == null ? 0 : p-1, size, sort);
        return scoreLogRepository.findByUser(user, pageable);
    }

}
